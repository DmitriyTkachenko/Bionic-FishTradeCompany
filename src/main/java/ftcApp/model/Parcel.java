package ftcApp.model;

import ftcApp.model.enums.ParcelStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Parcel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private ParcelStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date purchased;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parcel")
    private List<Item> items = new ArrayList<>();

    private Double deliveryCost;

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

        if (deliveryCost != null ? !deliveryCost.equals(parcel.deliveryCost) : parcel.deliveryCost != null)
            return false;
        if (items != null ? !items.equals(parcel.items) : parcel.items != null) return false;
        if (!purchased.equals(parcel.purchased)) return false;
        if (status != parcel.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + purchased.hashCode();
        result = 31 * result + (deliveryCost != null ? deliveryCost.hashCode() : 0);
        return result;
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
}
