package ftcApp.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @Column(name = "paymentSum")
    private double sum;

    public Payment() {
    }

    public Payment(Order order, double sum) {
        this.order = order;
        if (!order.getPayments().contains(this)) {
            order.getPayments().add(this);
        }
        this.sum = sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getPayments().contains(this)) {
            order.getPayments().add(this);
        }
    }
}
