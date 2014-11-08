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

@ManagedBean
@ViewScoped
public class ParcelsBean {
    @Inject
    private ParcelService parcelService;

    private Iterable<Parcel> parcels;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        parcels = parcelService.findAll();
    }

    public ParcelStatus[] getParcelStatuses() {
        return ParcelStatus.values();
    }

    public void saveChanges() {
        parcelService.updateParcels(parcels);
    }

    public Iterable<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(Iterable<Parcel> parcels) {
        this.parcels = parcels;
    }
}
