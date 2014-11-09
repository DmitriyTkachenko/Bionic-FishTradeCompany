package ftcApp.ui;

import ftcApp.model.Order;
import ftcApp.model.enums.OrderStatus;
import ftcApp.service.OrderService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class OrdersBean implements Serializable {
    @Inject
    private transient OrderService orderService;

    private Iterable<Order> ordersPendingShipment;
    private Iterable<Order> ordersPendingPayments;
    private double sum;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        ordersPendingShipment = orderService.findOrdersPendingShipment();
        ordersPendingPayments = orderService.findOrdersNotPaidInFull();
    }

    public void markOrderAsShipped(Order order) {
        orderService.markOrderAsShipped(order);
        ordersPendingShipment = orderService.findOrdersPendingShipment();
    }

    public void markOrderAsReadyForShipment(Order order) {
        orderService.updateOrderStatus(order, OrderStatus.PENDING_SHIPMENT);
    }

    public void markOrderAsCompleted(Order order) {
        orderService.updateOrderStatus(order, OrderStatus.COMPLETED);
        ordersPendingPayments = orderService.findOrdersNotPaidInFull();
    }

    public void addPaymentToOrder(Order order) {
        orderService.addPaymentToOrder(order, sum);
        sum = 0.0;
        ordersPendingPayments = orderService.findOrdersNotPaidInFull();
    }

    public Iterable<Order> getOrdersPendingShipment() {
        return ordersPendingShipment;
    }

    public void setOrdersPendingShipment(Iterable<Order> ordersPendingShipment) {
        this.ordersPendingShipment = ordersPendingShipment;
    }

    public Iterable<Order> getOrdersPendingPayments() {
        return ordersPendingPayments;
    }

    public void setOrdersPendingPayments(Iterable<Order> ordersPendingPayments) {
        this.ordersPendingPayments = ordersPendingPayments;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
