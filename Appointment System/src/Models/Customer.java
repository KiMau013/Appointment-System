package Models;

/**
 * Model for Customers
 */
public class Customer {

    private Integer customerId;
    private String customerName, address, postalCode, phone, division, country;

    public Customer(){}

    public Customer(Integer customerId, String customerName, String phone, String address, String postalCode, String division, String country){
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.country = country;
    }

    //For Dropdowns
    public Customer(Integer customerId) {
        this.customerId = customerId;
    }

    //getters
    public Integer getCustomerId(){
        return customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getDivision(){ return division; }
    public String getCountry(){ return country; }

    //setters
    public void setCustomerId(Integer customerId){
        this.customerId = customerId;
    }
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }
    public void setDivision(String division) { this.division = division; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {return customerId.toString();}
}
