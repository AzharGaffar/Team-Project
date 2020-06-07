package sample;

public class CustomerDataModel {

    //data model is used for displaying customer information in the customer table.

    //initialize
    int CustomerID;
    String firstName, lastName, alias, email, telephone, status, FixedDiscountRate;

    //constructor
    public CustomerDataModel(int customerID, String firstName, String lastName, String alias, String email, String telephone, String status, String FixedDiscountRate) {
        CustomerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.email = email;
        this.telephone = telephone;
        this.status = status;
        this.FixedDiscountRate= FixedDiscountRate;
    }

    //getters and setters.
    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return status;
    }

    public void setIsValued(String status) {
        this.status = status;
    }

    public String getFixedDiscountRate() {
        return FixedDiscountRate;
    }

    public void setFixedDiscountRate(String FixedDiscountRate) {
        this.FixedDiscountRate=FixedDiscountRate;
    }
}
