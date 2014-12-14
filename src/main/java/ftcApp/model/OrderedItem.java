package ftcApp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
public class OrderedItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    private double weight;

    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    public OrderedItem() {
    }

    public OrderedItem(Item item, double weight, double price, Order Order) {
        this.item = item;
        this.weight = weight;
        this.price = price;
        this.order = Order;
        if (!order.getOrderedItems().contains(this)) {
            order.getOrderedItems().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedItem that = (OrderedItem) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (Double.compare(that.weight, weight) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(weight);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getNetIncome() {
        double expense = this.getDeliveryCost() + this.getStorageCost() + this.getPurchaseCost();
        double income = this.getIncome();
        return income - expense;
    }

    public double getIncome() {
        return weight * price;
    }

    public double getStorageCost() {
        return this.getNumberOfDaysInColdStore() * weight * Parcel.getStorageCostPerDayPerTonne();
    }

    public double getDeliveryCost() {
        Parcel parcel = item.getParcel();
        return (parcel.getDeliveryCost() / parcel.getTotalWeight()) * weight;
    }

    public long getNumberOfDaysInColdStore() {
        Date beginning = item.getParcel().getArrived();
        Date end = order.getShipped();
        Instant beginningInstant = Instant.ofEpochMilli(beginning.getTime());
        Instant endInstant = Instant.ofEpochMilli(end.getTime());
        LocalDateTime beginningLdt = LocalDateTime.ofInstant(beginningInstant, ZoneId.systemDefault());
        LocalDateTime endLdt = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault());
        long days = ChronoUnit.DAYS.between(beginningLdt, endLdt);
        return days;
    }

    public double getPurchaseCost() {
        return item.getBuyingPrice() * weight;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getOrderedItems().contains(this)) {
            order.getOrderedItems().add(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
