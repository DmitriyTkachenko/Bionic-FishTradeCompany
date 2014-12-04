package ftcApp.model;

import ftcApp.model.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "F_ORDER")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    private Double deliveryPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderedItem> orderedItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Payment> payments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shipped;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completed;

    public Order() { }

    public Order(OrderStatus status, Customer customer, Double deliveryPrice, List<OrderedItem> orderedItems, List<Payment> payments, Date created) {
        this.status = status;
        this.customer = customer;
        if (!customer.getOrders().contains(this)) {
            customer.getOrders().add(this);
        }
        this.deliveryPrice = deliveryPrice;
        this.orderedItems = orderedItems;
        this.payments = payments;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (completed != null ? !completed.equals(order.completed) : order.completed != null) return false;
        if (created != null ? !created.equals(order.created) : order.created != null) return false;
        if (customer != null ? !customer.equals(order.customer) : order.customer != null) return false;
        if (deliveryPrice != null ? !deliveryPrice.equals(order.deliveryPrice) : order.deliveryPrice != null)
            return false;
        if (orderedItems != null ? !orderedItems.equals(order.orderedItems) : order.orderedItems != null) return false;
        if (payments != null ? !payments.equals(order.payments) : order.payments != null) return false;
        if (shipped != null ? !shipped.equals(order.shipped) : order.shipped != null) return false;
        if (status != order.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (deliveryPrice != null ? deliveryPrice.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (shipped != null ? shipped.hashCode() : 0);
        result = 31 * result + (completed != null ? completed.hashCode() : 0);
        return result;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (!customer.getOrders().contains(this)) {
            customer.getOrders().add(this);
        }
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
            totalPrice += (orderedItem.getPrice() * orderedItem.getWeight());
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

    public boolean isPaidInFull() {
        return Double.compare(this.getPercentagePaid(), 100.0) >= 0;
    }

    public boolean isPrepaymentPaid() {
        return Double.compare(this.getTotalPaymentSum(), this.getRequiredPrepaymentSum()) >= 0;
    }

    public Date getShipped() {
        return shipped;
    }

    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    public double getRequiredPrepaymentSum() {
        return customer.getPrepaymentShareRequired() * getTotalPrice();
    }

    public void removeOrderedItem(OrderedItem orderedItem) {
        orderedItems.remove(orderedItem);
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }
}
