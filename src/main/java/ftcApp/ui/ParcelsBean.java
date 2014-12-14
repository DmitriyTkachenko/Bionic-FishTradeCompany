package ftcApp.ui;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.service.ParcelService;
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
public class ParcelsBean implements Serializable {
    @Inject
    private transient ParcelService parcelService;

    private Iterable<Parcel> parcels;
    private Iterable<Parcel> parcelsForCsm;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        this.fetchParcels();
        this.fetchParcelsForCsm();
    }

    public void fetchParcels() {
        parcels = parcelService.findAll();
    }

    public void fetchParcelsForCsm() { parcelsForCsm = parcelService.findParcelsNotRegisteredInColdStore(); }

    public ParcelStatus[] getParcelStatuses() {
        return ParcelStatus.values();
    }

    public void saveChanges() {
        parcelService.updateAll(parcels);
    }

    public Iterable<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(Iterable<Parcel> parcels) {
        this.parcels = parcels;
    }

    public Iterable<Parcel> getParcelsForCsm() {
        return parcelsForCsm;
    }

    public void setParcelsForCsm(Iterable<Parcel> parcelsForCsm) {
        this.parcelsForCsm = parcelsForCsm;
    }
}
