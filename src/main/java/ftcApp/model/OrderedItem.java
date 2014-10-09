package ftcApp.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderedItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    private double weight;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    public OrderedItem() {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
