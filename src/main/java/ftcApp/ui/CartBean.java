package ftcApp.ui;

import ftcApp.model.Order;
import ftcApp.model.OrderedItem;
import ftcApp.service.OrderService;
import ftcApp.service.TestService;
import ftcApp.service.UserService;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
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
    private Integer orderId;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void addItemToOrder(ItemQuantity itemQuantity) {
        if (Double.compare(itemQuantity.getWeight(), 0.0) == 0) {
            return;
        }
        if (order == null) {
            order = new Order();
        }
        OrderedItem orderedItem = new OrderedItem(itemQuantity.getItem(), itemQuantity.getWeight(), itemQuantity.getItem().getSellingPrice(), order);
    }

    public void saveOrder() {
        String login = userService.getLoginOfCurrentlyLoggedUser();
        RequestContext context = RequestContext.getCurrentInstance();
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        try {
            orderService.saveOrderForCustomerWithLogin(order, login);
            orderId = order.getId();
            context.execute("PF('orderSubmittedDialog').show();");
            order = null;
        } catch (Exception e) {
            e.printStackTrace();
            context.execute("PF('orderSubmitFailedDialog').show();");
        } finally {
            eventBus.publish("/itemsChanged", true);
        }
    }

    public String getNumberOfItems() {
        if (order == null) {
            return "";
        } else {
            int numberOfOrderedItems = order.getNumberOfOrderedItems();
            if (numberOfOrderedItems != 0) {
                return " (" + numberOfOrderedItems + ")";
            } else {
                return "";
            }
        }
    }
}