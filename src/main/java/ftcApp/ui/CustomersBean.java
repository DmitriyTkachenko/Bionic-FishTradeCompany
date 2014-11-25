package ftcApp.ui;

import ftcApp.model.Customer;
import ftcApp.service.CustomerService;
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
public class CustomersBean implements Serializable {
    @Inject
    private transient CustomerService customerService;

    private Iterable<Customer> customers;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        customers = customerService.findAll();
    }

    public void saveChanges() {
        customerService.updateAll(customers);
    }

    public Iterable<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Iterable<Customer> customers) {
        this.customers = customers;
    }
}
