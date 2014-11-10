package ftcApp.ui;

import ftcApp.model.Employee;
import ftcApp.model.enums.UserRole;
import ftcApp.service.EmployeeService;
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
public class EmployeesBean implements Serializable {
    @Inject
    private transient EmployeeService employeeService;

    private Iterable<Employee> employees;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        employees = employeeService.findAll();
    }

    public UserRole[] getUserRoles() {
        return UserRole.values();
    }

    public void saveChanges() {
        employeeService.updateAll(employees);
    }

    public Iterable<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Iterable<Employee> employees) {
        this.employees = employees;
    }
}
