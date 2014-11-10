package ftcApp.ui;

import ftcApp.model.Order;
import ftcApp.model.OrderedItem;
import ftcApp.service.OrderService;
import ftcApp.service.TestService;
import ftcApp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class CartBean implements Serializable {
    @Inject
    private transient TestService testService;

    @Inject
    private transient OrderService orderService;

    @Inject
    private transient UserService userService;

    private Order order;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        testService.addEmployees();
        testService.addCustomer();
    }

    @PreDestroy
    public void destroy() {
        testService.removeEmployees();
        testService.removeCustomer();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void addItemToOrder(ItemQuantity itemQuantity) {
        if (order == null) {
            order = new Order();
        }
        OrderedItem orderedItem = new OrderedItem(itemQuantity.getItem(), itemQuantity.getWeight(), itemQuantity.getItem().getSellingPrice(), order);
    }

    public void saveOrder() {
        String login = userService.getLoginOfCurrentlyLoggedUser();
        orderService.saveOrderForCustomerWithLogin(order, login);
    }
}