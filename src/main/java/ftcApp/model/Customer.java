package ftcApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Customer extends User implements Serializable {
    @Column(nullable = false)
    private double prepaymentShareRequired;

    private String shippingAddress;

    private String contactInfo;

    public Customer() {
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public double getPrepaymentShareRequired() {
        return prepaymentShareRequired;
    }

    public void setPrepaymentShareRequired(double prepaymentShareRequired) {
        this.prepaymentShareRequired = prepaymentShareRequired;
    }
}
