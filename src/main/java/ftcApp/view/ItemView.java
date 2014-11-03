package ftcApp.view;

import ftcApp.model.Item;
import ftcApp.model.Order;
import ftcApp.model.OrderedItem;
import ftcApp.model.enums.OrderStatus;
import ftcApp.service.ItemService;
import ftcApp.service.OrderService;
import ftcApp.service.OrderedItemService;
import ftcApp.service.TestService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class ItemView implements Serializable {
    @Inject
    private transient ItemService itemService;

    @Inject
    private transient TestService testService;

    @Inject
    private transient OrderedItemService orderedItemService;

    @Inject
    private transient OrderService orderService;

    private Iterable<Item> items;
    private Order order;
    private Item selectedItem;
    private double selectedWeight;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        testService.addItemsData();
        items = itemService.findAll();
    }

    @PreDestroy
    public void destroy() {
        testService.removeItemsData();
    }

    public void addItemToOrder() {
        if (order == null) {
            order = new Order(OrderStatus.PENDING_PREPAYMENT, null, new ArrayList<>(), new ArrayList<>());
        }
        OrderedItem orderedItem = new OrderedItem(selectedItem, selectedWeight, order);
    }

    public Iterable<Item> getItems() {
        return items;
    }

    public void setItems(Iterable<Item> items) {
        this.items = items;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public double getSelectedWeight() {
        return selectedWeight;
    }

    public void setSelectedWeight(double selectedWeight) {
        this.selectedWeight = selectedWeight;
    }
}
