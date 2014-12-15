package ftcApp.ui;

import ftcApp.model.Item;
import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.model.enums.WriteOffStatus;
import ftcApp.service.ItemService;
import ftcApp.service.ParcelService;
import ftcApp.service.TestService;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ItemsBean implements Serializable {
    @Inject
    private transient ItemService itemService;

    @Inject
    private transient ParcelService parcelService;

    @Inject
    private transient TestService testService;

    private List<ItemQuantity> itemQuantities;

    private List<Item> items;
    private List<Item> itemsForWritingOff;

    private boolean editable = false;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

//        testService.addItemsData();
        fetchItems();
        fetchItemsForWritingOff();
    }

    @PreDestroy
    public void destroy() {
        testService.removeItemsData();
    }

    public void loadItemsForParcel(Parcel parcel) {
        items = parcel.getItems();
        editable = (parcel.getStatus() == ParcelStatus.REGISTERED_BY_GM);
    }

    public void fetchItems() {
        Iterable<Item> items = itemService.findItemsOnSaleAndRefresh();
        this.itemQuantities = new ArrayList<>();
        items.forEach((Item item) -> this.itemQuantities.add(new ItemQuantity(item, 0.0)));
    }

    public void writeOffItem(Item item) {
        item.setWriteOffStatus(WriteOffStatus.WRITTEN_OFF);
        itemService.update(item);
        itemsForWritingOff.remove(item);
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/itemsChanged", true);
    }

    public void fetchItemsForWritingOff() {
        itemsForWritingOff = (List<Item>) itemService.findItemsDesignatedForWriteOff();
    }

    public void loadItemsForParcelForEditingByCsm(Parcel parcel) {
        loadItemsForParcel(parcel);
    }

    public void saveChangesColdStore() {
        parcelService.updateParcelColdStore(items.get(0).getParcel());
    }

    public int getNumberOfItemsForWritingOff() {
        return ((List<Item>) itemsForWritingOff).size();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<ItemQuantity> getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(List<ItemQuantity> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Iterable<Item> getItemsForWritingOff() {
        return itemsForWritingOff;
    }

    public void setItemsForWritingOff(List<Item> itemsForWritingOff) {
        this.itemsForWritingOff = itemsForWritingOff;
    }
}
