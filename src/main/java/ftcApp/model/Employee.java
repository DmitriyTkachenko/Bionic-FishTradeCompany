package ftcApp.model;

import ftcApp.model.enums.EmployeeRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Employee extends User implements Serializable {
    @Column(nullable = false)
    private EmployeeRole role;

    public Employee() {
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }
}
