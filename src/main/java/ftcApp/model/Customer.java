package ftcApp.model;

import ftcApp.model.enums.UserRole;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends User {
    @Column(nullable = false)
    private double prepaymentShareRequired = 1.0;

    private String shippingAddress;

    private String contactInfo;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer() {
    }

    public Customer(String login, String password, UserRole userRole) {
        setLogin(login);
        setPassword(password);
        setUserRole(userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        if (Double.compare(customer.prepaymentShareRequired, prepaymentShareRequired) != 0) return false;
        if (contactInfo != null ? !contactInfo.equals(customer.contactInfo) : customer.contactInfo != null)
            return false;
        if (orders != null ? !orders.equals(customer.orders) : customer.orders != null) return false;
        if (shippingAddress != null ? !shippingAddress.equals(customer.shippingAddress) : customer.shippingAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(prepaymentShareRequired);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (shippingAddress != null ? shippingAddress.hashCode() : 0);
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        return result;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
