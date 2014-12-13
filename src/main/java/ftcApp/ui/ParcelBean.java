package ftcApp.ui;

import ftcApp.model.FishName;
import ftcApp.model.Item;
import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.service.FishNameService;
import ftcApp.service.ParcelService;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class ParcelBean implements Serializable {
    private Parcel parcel;
    private Item item;
    private Iterable<FishName> fishNames;

    @Inject
    private transient ParcelService parcelService;

    @Inject
    private transient FishNameService fishNameService;

    @Inject
    private transient TestService testService;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        initParcel();
        initItem();
        testService.addFishNames();
        fishNames = fishNameService.findAll();
    }

    @PreDestroy
    public void destroy() {
        testService.removeFishNames();
    }

    public void addItemToParcel() {
        item.setParcel(parcel);
        if (Double.compare(item.getSellingPrice(), 0.0) == 0) {
            item.setSellingPrice(null);
        }
        initItem();
    }

    public List<String> completeFishName(String query) {
        List<String> results = new ArrayList<String>();
        query = query.toLowerCase();

        for (FishName fishName : fishNames) {
            String name = fishName.getName();
            if (name.toLowerCase().startsWith(query)) {
                results.add(name);
            }
        }

        return results;
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
