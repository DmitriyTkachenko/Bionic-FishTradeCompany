package ftcApp.model;

import ftcApp.exception.OrderSaveFailedException;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nameBought;

    private String nameColdStore;

    @Column(nullable = false)
    private double weightBought;

    private Double weightColdStore;

    private String descriptionBought;

    private String descriptionColdStore;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parcelId", nullable = false)
    private Parcel parcel;

    @Column(nullable = false)
    private Double buyingPrice;

    private Double sellingPrice;

    @Column(nullable = false)
    boolean writtenOff = false;

    public Item() { }

    public Item(String nameBought, double weightBought, String descriptionBought, Parcel parcel, Double buyingPrice, Double sellingPrice) {
        this.nameBought = nameBought;
        this.weightBought = weightBought;
        this.descriptionBought = descriptionBought;
        this.parcel = parcel;
        if (!parcel.getItems().contains(this)) {
            parcel.getItems().add(this);
        }
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.weightBought, weightBought) != 0) return false;
        if (writtenOff != item.writtenOff) return false;
        if (buyingPrice != null ? !buyingPrice.equals(item.buyingPrice) : item.buyingPrice != null) return false;
        if (descriptionBought != null ? !descriptionBought.equals(item.descriptionBought) : item.descriptionBought != null)
            return false;
        if (descriptionColdStore != null ? !descriptionColdStore.equals(item.descriptionColdStore) : item.descriptionColdStore != null)
            return false;
        if (nameBought != null ? !nameBought.equals(item.nameBought) : item.nameBought != null) return false;
        if (nameColdStore != null ? !nameColdStore.equals(item.nameColdStore) : item.nameColdStore != null)
            return false;
        if (sellingPrice != null ? !sellingPrice.equals(item.sellingPrice) : item.sellingPrice != null) return false;
        if (weightColdStore != null ? !weightColdStore.equals(item.weightColdStore) : item.weightColdStore != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nameBought != null ? nameBought.hashCode() : 0;
        result = 31 * result + (nameColdStore != null ? nameColdStore.hashCode() : 0);
        temp = Double.doubleToLongBits(weightBought);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (weightColdStore != null ? weightColdStore.hashCode() : 0);
        result = 31 * result + (descriptionBought != null ? descriptionBought.hashCode() : 0);
        result = 31 * result + (descriptionColdStore != null ? descriptionColdStore.hashCode() : 0);
        result = 31 * result + (buyingPrice != null ? buyingPrice.hashCode() : 0);
        result = 31 * result + (sellingPrice != null ? sellingPrice.hashCode() : 0);
        result = 31 * result + (writtenOff ? 1 : 0);
        return result;
    }

    public void duplicateBoughtAndColdStoreProperties() {
        if (nameColdStore == null) {
            nameColdStore = nameBought;
        }
        if (weightColdStore == null) {
            weightColdStore = weightBought;
        }
        if (descriptionColdStore == null) {
            descriptionColdStore = descriptionBought;
        }
    }

    public boolean reduceWeightInColdStoreBy(double weight) throws OrderSaveFailedException {
        if (Double.compare(weightColdStore, weight) >= 0) {
            weightColdStore -= weight;
            return true;
        } else {
            throw new OrderSaveFailedException("Requested quantity is not available.");
        }
    }

    public boolean weightsMatch() {
        return Double.compare(weightBought, weightColdStore) == 0;
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

    public String getNameColdStore() {
        return nameColdStore;
    }

    public void setNameColdStore(String nameColdStore) {
        this.nameColdStore = nameColdStore;
    }

    public Double getWeightColdStore() {
        return weightColdStore;
    }

    public void setWeightColdStore(Double weightColdStore) {
        this.weightColdStore = weightColdStore;
    }

    public String getDescriptionColdStore() {
        return descriptionColdStore;
    }

    public void setDescriptionColdStore(String descriptionColdStore) {
        this.descriptionColdStore = descriptionColdStore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameBought() {
        return nameBought;
    }

    public void setNameBought(String name) {
        this.nameBought = name;
    }

    public double getWeightBought() {
        return weightBought;
    }

    public void setWeightBought(double weight) {
        this.weightBought = weight;
    }

    public String getDescriptionBought() {
        return descriptionBought;
    }

    public void setDescriptionBought(String description) {
        this.descriptionBought = description;
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
