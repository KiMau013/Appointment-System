package DAOImplementations;

import Models.Customer;
import Models.First_Level_Division;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 * Class for Customer Implementations
 */
public class CustomersImplementation {

    private static Customer newCustomer;

    public static Customer getNewCustomer() {
        return newCustomer;
    }

    /**
     *SQL for adding Customer
     * @param customerID
     * @param name
     * @param address
     * @param firstLevel
     * @param postal
     * @param phone
     */
    public static void addCustomer(Integer customerID, String name, String address, String firstLevel, String postal, String phone){
        int currentDivision = First_Level_DivisionsImplementation.convertToDivision(firstLevel);
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO customers" + " (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)" + " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    customerID, name, address, postal, phone, LocalDateTime.now(), User.getUserName(), LocalDateTime.now(), User.getUserName(), currentDivision);
            Query.makeQuery(sqlQuery);
            statement.close();
            }
        catch(SQLException e){
        System.out.println("Error adding customer: " + e.getMessage());
    }
}

    /**
     * SQL for updating Customer
     * @param customerID
     * @param name
     * @param address
     * @param firstLevel
     * @param postal
     * @param phone
     */
    public static void updateCustomer(Integer customerID, String name, String address, String firstLevel, String postal, String phone) {
        int currentDivision = First_Level_DivisionsImplementation.convertToDivision(firstLevel);
        try {
            Statement statment = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("UPDATE customers" + " SET Customer_Name = '%s', Address = '%s', Postal_Code = '%s', Phone = '%s', Last_Update = '%s', Last_Updated_By = '%s', Division_ID = '%s'" + " WHERE Customer_ID='%s'",
                    name, address, postal, phone, LocalDateTime.now(), User.getUserName(), currentDivision, customerID);
            Query.makeQuery(sqlQuery);
            statment.close();
        } catch (Exception e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    /**
     * SQL for deleting Customer
     * @param customerID
     */
    public static void deleteCustomer(Integer customerID) {
        //Delete all appointments for customer first from appointments table
        try{
            Query.makeQuery(String.format("DELETE FROM appointments" + " WHERE Customer_ID = '%s'", customerID));
        }
        catch (Exception e){
            System.out.println("Error deleting customer: " + e.getMessage());
        }
        //Delete actual customer from customers table
        try {
            Query.makeQuery(String.format("DELETE FROM customers" + " WHERE Customer_ID = '%s'", customerID));
        } catch (Exception e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    /**
     * Provides information about Customer for populating customer modify form
     * @param customerID
     * @return Customer
     */
    public static Customer readCustomer(Integer customerID) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Customer_ID, Customer_Name, Address, Division_ID, Postal_Code, Phone" + " FROM customers" + " WHERE Customer_ID='%s'", customerID);
            Query.makeQuery(sqlQuery);
            Customer customerResult;
            ResultSet result = Query.getResult();
            while(result.next()){
                int cid = result.getInt("Customer_ID");
                String cname = result.getString("Customer_Name");
                String caddress = result.getString("Address");
                String cpostal = result.getString("Postal_Code");
                String cphone = result.getString("Phone");
                String cdivision = First_Level_DivisionsImplementation.convertToStateProvince(result.getInt("Division_ID"));
                String ccountry = First_Level_DivisionsImplementation.convertToCountry(result.getInt("Division_ID"));
                customerResult = new Customer(cid, cname, caddress, cpostal, cphone, cdivision, ccountry);
                return customerResult;
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Unable to find customer with ID: " + customerID);
        }
        return null;
    }

    /**
     * Provides information about Customer for populating customer table
     * @return ObservableList
     */
    public static ObservableList<Customer> readAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Customer_ID, Customer_Name, Phone, Address, Postal_Code, Division_ID" + " FROM customers";
            Query.makeQuery(sqlQuery);
            Customer customerResult;
            ResultSet result = Query.getResult();
            while(result.next()){
                int cid = result.getInt("Customer_ID");
                String cname = result.getString("Customer_Name");
                String cphone = result.getString("Phone");
                String caddress = result.getString("Address");
                String cpostal = result.getString("Postal_Code");
                String cdivision = First_Level_DivisionsImplementation.convertToStateProvince(result.getInt("Division_ID"));
                String ccountry = First_Level_DivisionsImplementation.convertToCountry(result.getInt("Division_ID"));
                customerResult = new Customer(cid, cname, cphone, caddress, cpostal, cdivision, ccountry);
                customers.add(customerResult);
            }
            statement.close();
            return customers;
        } catch (Exception e) {
            System.out.println("Unable to find any customers.");
        }
        return null;
    }

    /**
     * Populates First Level Division based on country
     * @param country
     * @return ObservableList
     */
    public static ObservableList<First_Level_Division> populateDivision(String country) {
        ObservableList<First_Level_Division> first_level_division = FXCollections.observableArrayList();
        first_level_division.clear();

        int countryID;
        switch (country){
            case "U.S": {countryID = 1; break;}
            case "UK": {countryID = 2; break;}
            case "Canada": {countryID = 3; break;}
            default:
                throw new IllegalStateException("Unexpected value: " + country);
        }

        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Division" + " FROM first_level_divisions" + " WHERE COUNTRY_ID='%s'", countryID);
            Query.makeQuery(sqlQuery);
            First_Level_Division divisionResult;
            ResultSet result = Query.getResult();
            while (result.next()) {
                String division = result.getString("Division");
                divisionResult = new First_Level_Division(division);
                first_level_division.add(divisionResult);
            }
            statement.close();
            return first_level_division;
        } catch (Exception e) {
            System.out.println("Unable to populate first_level_division.");
        }
        return null;
    }

    /**
     * Gets next available customerID
     * @return String
     */
    public static String getNextCustomer(){
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Customer_ID" + " FROM customers" + " ORDER BY Customer_ID DESC" + " LIMIT 1";
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return (String.valueOf(result.getInt("Customer_ID") + 1));
        } catch (Exception e) {
            System.out.println("Unable to verify current Customer: " +e.getMessage());
            return null;
        }
    }
}
