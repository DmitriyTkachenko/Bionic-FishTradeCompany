package ftcApp.model;

import ftcApp.model.enums.UserRole;

import javax.persistence.Entity;

@Entity
public class Employee extends User {
    private UserRole role;

    public Employee() {
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
