package ftcApp.ui;

import ftcApp.model.Item;
import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.service.ParcelService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
public class ParcelBean implements Serializable {
    private Parcel parcel;
    private Item item;

    @Inject
    private transient ParcelService parcelService;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        initParcel();
        initItem();
    }

    public void addItemToParcel() {
        item.setParcel(parcel);
        if (Double.compare(item.getSellingPrice(), 0.0) == 0) {
            item.setSellingPrice(null);
        }
        initItem();
    }

    public void saveParcel() {
        parcel.setStatus(ParcelStatus.REGISTERED_BY_GM);
        parcel.setPurchased(new Date());
        parcelService.save(parcel);
        initParcel();
        initItem();
    }

    public void initItem() {
        item = new Item();
        item.setBuyingPrice(0.0);
        item.setSellingPrice(0.0);
        item.setWeightBought(0.0);
    }

    public void initParcel() {
        parcel = new Parcel();
        parcel.setDeliveryCost(0.0);
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
