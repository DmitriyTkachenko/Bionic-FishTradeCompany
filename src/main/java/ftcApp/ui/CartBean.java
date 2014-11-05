package ftcApp.ui;

import ftcApp.model.Order;
import ftcApp.model.OrderedItem;
import ftcApp.model.enums.OrderStatus;
import ftcApp.service.TestService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class CartBean {
    @Inject
    private transient TestService testService;

    private Order order;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        testService.addUser();
    }

    @PreDestroy
    public void destroy() {
        testService.removeUser();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void addItemToOrder(ItemQuantity itemQuantity) {
        if (order == null) {
            order = new Order(OrderStatus.PENDING_PREPAYMENT, null, new ArrayList<>(), new ArrayList<>());
        }
        OrderedItem orderedItem = new OrderedItem(itemQuantity.getItem(), itemQuantity.getWeight(), itemQuantity.getItem().getSellingPrice(), order);
    }
}