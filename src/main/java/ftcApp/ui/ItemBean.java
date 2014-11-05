package ftcApp.ui;

import ftcApp.model.Item;
import ftcApp.service.ItemService;
import ftcApp.service.TestService;
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

@ManagedBean
@ViewScoped
public class ItemBean implements Serializable {
    @Inject
    private transient ItemService itemService;

    @Inject
    private transient TestService testService;

    private ArrayList<ItemQuantity> items;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        testService.addItemsData();
        Iterable<Item> items = itemService.findAll();

        this.items = new ArrayList<>();
        items.forEach((Item item) -> this.items.add(new ItemQuantity(item, 0.0)));
    }

    @PreDestroy
    public void destroy() {
        testService.removeItemsData();
    }

    public ArrayList<ItemQuantity> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemQuantity> items) {
        this.items = items;
    }
}
