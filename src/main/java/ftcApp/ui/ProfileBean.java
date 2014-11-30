package ftcApp.ui;

import ftcApp.model.Order;
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
public class ProfileBean implements Serializable {
    @Inject
    private transient OrderService orderService;

    private Iterable<Order> orders;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        orders = orderService.findOrdersByLoggedCustomer();
    }

    public Iterable<Order> getOrders() {
        return orders;
    }

    public void setOrders(Iterable<Order> orders) {
        this.orders = orders;
    }
}
