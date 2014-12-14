package ftcApp.model;

import ftcApp.model.enums.ParcelStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Parcel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private ParcelStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date purchased;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrived;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parcel")
    private List<Item> items = new ArrayList<>();

    private Double deliveryCost;

    private static final double STORAGE_COST_PER_DAY_PER_TONNE = 20.0;

    public Parcel(ParcelStatus status, Date purchased, List<Item> items, Double deliveryCost) {
        this.status = status;
        this.purchased = purchased;
        this.items = items;
        this.deliveryCost = deliveryCost;
    }

    public Parcel() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parcel parcel = (Parcel) o;

        if (arrived != null ? !arrived.equals(parcel.arrived) : parcel.arrived != null) return false;
        if (deliveryCost != null ? !deliveryCost.equals(parcel.deliveryCost) : parcel.deliveryCost != null)
            return false;
        if (purchased != null ? !purchased.equals(parcel.purchased) : parcel.purchased != null) return false;
        if (status != parcel.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (purchased != null ? purchased.hashCode() : 0);
        result = 31 * result + (arrived != null ? arrived.hashCode() : 0);
        result = 31 * result + (deliveryCost != null ? deliveryCost.hashCode() : 0);
        return result;
    }

    public long getNumberOfDaysInColdStore() {
        if (arrived == null) {
            return 0;
        }
        Instant instant = Instant.ofEpochMilli(arrived.getTime());
        LocalDateTime arrivedLdt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        long days = ChronoUnit.DAYS.between(arrivedLdt, LocalDateTime.now());
        return days;
    }

    public double getTotalWeight() {
        double totalWeight = 0.0;
        for (Item item : items) {
            totalWeight += item.getWeightBought();
        }
        return totalWeight;
    }

    public Date getArrived() {
        return arrived;
    }

    public void setArrived(Date arrived) {
        this.arrived = arrived;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public Date getPurchased() {
        return purchased;
    }

    public void setPurchased(Date registered) {
        this.purchased = registered;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryPrice) {
        this.deliveryCost = deliveryPrice;
    }

    public static double getStorageCostPerDayPerTonne() {
        return STORAGE_COST_PER_DAY_PER_TONNE;
    }
}
