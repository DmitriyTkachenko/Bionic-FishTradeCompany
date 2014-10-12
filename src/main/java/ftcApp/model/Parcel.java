package ftcApp.model;

import ftcApp.model.enums.ParcelStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Parcel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer purchaseNumber;

    @Column(nullable = false)
    private ParcelStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date purchased;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parcel")
    private List<Item> items;

    private Double deliveryPrice;

    public Parcel(Integer purchaseNumber, ParcelStatus status, Date purchased, List<Item> items, Double deliveryPrice) {
        this.purchaseNumber = purchaseNumber;
        this.status = status;
        this.purchased = purchased;
        this.items = items;
        this.deliveryPrice = deliveryPrice;
    }

    public Parcel() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parcel parcel = (Parcel) o;

        if (deliveryPrice != null ? !deliveryPrice.equals(parcel.deliveryPrice) : parcel.deliveryPrice != null)
            return false;
        if (!purchaseNumber.equals(parcel.purchaseNumber)) return false;
        if (!purchased.equals(parcel.purchased)) return false;
        if (status != parcel.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = purchaseNumber.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + purchased.hashCode();
        result = 31 * result + (deliveryPrice != null ? deliveryPrice.hashCode() : 0);
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

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
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

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
