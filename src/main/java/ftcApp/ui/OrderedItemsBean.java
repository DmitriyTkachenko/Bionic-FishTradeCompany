package ftcApp.ui;

import ftcApp.model.Order;
import ftcApp.model.OrderedItem;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class OrderedItemsBean implements Serializable {
    private Iterable<OrderedItem> orderedItems;

    public void loadOrderedItemsForOrder(Order order) {
        orderedItems = order.getOrderedItems();
    }

    public Iterable<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Iterable<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
