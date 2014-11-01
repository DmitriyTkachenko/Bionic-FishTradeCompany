package ftcApp.model;

import ftcApp.model.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "F_ORDER")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

        if (deliveryPrice != null ? !deliveryPrice.equals(order.deliveryPrice) : order.deliveryPrice != null)
            return false;
        if (status != order.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (deliveryPrice != null ? deliveryPrice.hashCode() : 0);
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
            totalPrice += orderedItem.getItem().getSellingPrice() * orderedItem.getWeight();
        }
        return totalPrice;
    }

    public double getTotalPaymentSum() {
        double totalPaymentSum = 0.0;
        for (Payment payment : payments) {
            totalPaymentSum += payment.getSum();
        }
        return totalPaymentSum;
    }

    public double getPercentagePaid() {
        double totalPrice = this.getTotalPrice();
        double totalPaymentSum = this.getTotalPaymentSum();
        return (totalPaymentSum / totalPrice) * 100;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
