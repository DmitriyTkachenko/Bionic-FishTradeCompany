package ftcApp.model;

import ftcApp.model.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "F_ORDER")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private OrderStatus status;

    private Double deliveryPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderedItem> orderedItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Payment> payments;

    public Order() {
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Order(OrderStatus status, Double deliveryPrice, List<OrderedItem> orderedItems, List<Payment> payments) {
        this.status = status;
        this.deliveryPrice = deliveryPrice;
        this.orderedItems = orderedItems;
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.deliveryPrice, deliveryPrice) != 0) return false;
        if (status != order.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = status.hashCode();
        temp = Double.doubleToLongBits(deliveryPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getTotalWeight() {
        double totalWeight = 0.0;
        for (OrderedItem orderedItem : orderedItems) {
            totalWeight += orderedItem.getWeight();
        }
        return totalWeight;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (OrderedItem orderedItem : orderedItems) {
            totalPrice += orderedItem.getItem().getSellingPrice();
        }
        return totalPrice;
    }

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
