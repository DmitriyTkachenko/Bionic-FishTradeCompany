package ftcApp.model;

import ftcApp.model.enums.OrderStatus;
import ftcApp.model.enums.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends User {
    @Column(nullable = false)
    private double prepaymentShareRequired = 0.5;

    @Column(nullable = false, length = 1000)
    private String shippingAddress;

    @Column(nullable = false)
    private String email;

    private String contactInfo;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer() {
    }

    public Customer(String login, String password, String name, UserRole userRole) {
        setLogin(login);
        setPassword(password);
        setUserRole(userRole);
        setName(name);
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
        if (email != null ? !email.equals(customer.email) : customer.email != null) return false;
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
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (contactInfo != null ? contactInfo.hashCode() : 0);
        return result;
    }

    public int getNumberOfOrders() {
        int numberOfOrders = 0;
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                ++numberOfOrders;
            }
        }
        return numberOfOrders;
    }

    public double getTotalMoneySpent() {
        double totalMoneySpent = 0.0;
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                totalMoneySpent += order.getTotalPrice();
            }
        }
        return totalMoneySpent;
    }

    public double getTotalWeightOrdered() {
        double totalWeightOrdered = 0.0;
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                totalWeightOrdered += order.getTotalWeight();
            }
        }
        return totalWeightOrdered;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public double getPrepaymentShareRequiredPercent() { return prepaymentShareRequired * 100.0; }

    public void setPrepaymentShareRequired(double prepaymentShareRequired) {
        this.prepaymentShareRequired = prepaymentShareRequired;
    }

    public void setPrepaymentShareRequiredPercent(double prepaymentShareRequired) {
        this.prepaymentShareRequired = (prepaymentShareRequired / 100.0);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
