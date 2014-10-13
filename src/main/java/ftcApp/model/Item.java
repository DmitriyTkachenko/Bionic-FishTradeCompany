package ftcApp.model;

import ftcApp.model.Parcel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double weight;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parcelId", nullable = false)
    private Parcel parcel;

    private Double buyingPrice;

    private Double sellingPrice;

    @Column(nullable = false)
    boolean writtenOff = false;

    public Item() {
    }

    public Item(String name, double weight, String description, Parcel parcel, Double buyingPrice, Double sellingPrice) {
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.parcel = parcel;
        if (!parcel.getItems().contains(this)) {
            parcel.getItems().add(this);
        }
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
        if (!parcel.getItems().contains(this)) {
            parcel.getItems().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.weight, weight) != 0) return false;
        if (writtenOff != item.writtenOff) return false;
        if (buyingPrice != null ? !buyingPrice.equals(item.buyingPrice) : item.buyingPrice != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (!name.equals(item.name)) return false;
        if (sellingPrice != null ? !sellingPrice.equals(item.sellingPrice) : item.sellingPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (buyingPrice != null ? buyingPrice.hashCode() : 0);
        result = 31 * result + (sellingPrice != null ? sellingPrice.hashCode() : 0);
        result = 31 * result + (writtenOff ? 1 : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public boolean isWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(boolean writtenOff) {
        this.writtenOff = writtenOff;
    }
}
