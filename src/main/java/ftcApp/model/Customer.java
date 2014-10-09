package ftcApp.model;

import javax.persistence.Entity;

@Entity
public class Customer extends User {
    private double prepaymentShareRequired;

    public Customer() {
    }

    public double getPrepaymentShareRequired() {
        return prepaymentShareRequired;
    }

    public void setPrepaymentShareRequired(double prepaymentShareRequired) {
        this.prepaymentShareRequired = prepaymentShareRequired;
    }
}
