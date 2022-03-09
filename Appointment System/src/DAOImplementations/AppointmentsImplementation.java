package DAOImplementations;

import Models.*;
import Utilities.TimeUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

/**
 * Class for Appointment Implementations
 */
public class AppointmentsImplementation {

    /**
     * Sets TableView for all appointments
     * @return appointments
     */
    public static ObservableList<Appointment> allAppointments() {
        //Populates all appointments for Contact Report
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID" + " FROM appointments a" + " INNER JOIN contacts c ON c.Contact_ID=a.Contact_ID";
            Query.makeQuery(sqlQuery);
            Appointment appointment;
            ResultSet result = Query.getResult();
            while (result.next()) {
                Integer appointmentId = result.getInt("Appointment_ID");
                String atitle = result.getString("Title");
                String adescription = result.getString("Description");
                String alocation = result.getString("Location");
                String ccontact = result.getString("Contact_Name");
                String atype = result.getString("Type");
                String astart = TimeUtility.timeToLocalShort(result.getString("Start")).replace('T',' ');
                String aend = TimeUtility.timeToLocalShort(result.getString("End")).replace('T',' ');
                Integer cid = result.getInt("Customer_ID");
                appointment = new Appointment(appointmentId, atitle, adescription, alocation, ccontact, atype, astart, aend, cid);
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to find any appointments for contact.");
        }
        return null;
    }

    /**
     * Sets TableView for current months appointments
     * @return appointments
     */
    public static ObservableList<Appointment> monthAppointments() {
        //Populates all appointments for Contact Report
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID" + " FROM appointments a" + " INNER JOIN contacts c ON c.Contact_ID=a.Contact_ID" + " WHERE MONTHNAME(Start)='%s'", LocalDateTime.now().getMonth());
            Query.makeQuery(sqlQuery);
            Appointment appointment;
            ResultSet result = Query.getResult();
            while (result.next()) {
                Integer appointmentId = result.getInt("Appointment_ID");
                String atitle = result.getString("Title");
                String adescription = result.getString("Description");
                String alocation = result.getString("Location");
                String ccontact = result.getString("Contact_Name");
                String atype = result.getString("Type");
                String astart = TimeUtility.timeToLocalShort(result.getString("Start")).toString().replace('T',' ');
                String aend = TimeUtility.timeToLocalShort(result.getString("End")).toString().replace('T',' ');
                Integer cid = result.getInt("Customer_ID");
                appointment = new Appointment(appointmentId, atitle, adescription, alocation, ccontact, atype, astart, aend, cid);
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to find any appointments for contact.");
        }
        return null;
    }

    /**
     * Sets TableView for current weeks appointments
     * @return ObservableList
     */
    public static ObservableList<Appointment> weekAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID" + " FROM appointments a" + " INNER JOIN contacts c ON c.Contact_ID=a.Contact_ID" + " WHERE WEEK(Start)='%s'", (LocalDateTime.now().get(WeekFields.ISO.weekOfWeekBasedYear())));
            Query.makeQuery(sqlQuery);
            Appointment appointment;
            ResultSet result = Query.getResult();
            while (result.next()) {
                Integer appointmentId = result.getInt("Appointment_ID");
                String atitle = result.getString("Title");
                String adescription = result.getString("Description");
                String alocation = result.getString("Location");
                String ccontact = result.getString("Contact_Name");
                String atype = result.getString("Type");
                String astart = TimeUtility.timeToLocalShort(result.getString("Start")).replace('T',' ');
                String aend = TimeUtility.timeToLocalShort(result.getString("End")).replace('T',' ');
                Integer cid = result.getInt("Customer_ID");
                appointment = new Appointment(appointmentId, atitle, adescription, alocation, ccontact, atype, astart, aend, cid);
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to find any appointments for contact.");
        }
        return null;
    }

    /**
     * Gets the Next available Appointment_ID
     * @return String
     */
    public static String getNextAppointment(){
        //Gets next available Appointment ID (and increments to next)
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Appointment_ID" + " FROM appointments" + " ORDER BY Appointment_ID DESC" + " LIMIT 1";
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return (String.valueOf(result.getInt("Appointment_ID") + 1));
        } catch (Exception e) {
            System.out.println("Unable to verify current Appointment: " +e.getMessage());
            return null;
        }
    }

    /**
     * SQL for adding appointment
     * @param appointmentID
     * @param userID
     * @param customerID
     * @param contact
     * @param title
     * @param description
     * @param location
     * @param type
     * @param date
     * @param start
     * @param end
     */
    public static void addAppointment(Integer appointmentID, Integer userID, Integer customerID, String contact, String title, String description, String location, String type, String date, String start, String end){
        int currentContact = convertToContactID(contact);
        String dateStart = TimeUtility.timeToUTC(date, start);
        String dateEnd = TimeUtility.timeToUTC(date, end);
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("INSERT INTO appointments" + " (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" + " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                     appointmentID, title, description, location, type, dateStart, dateEnd, LocalDateTime.now(), User.getUserName(), LocalDateTime.now(), User.getUserName(), customerID, userID, currentContact);
            Query.makeQuery(sqlQuery);
            statement.close();
        }
        catch(SQLException e){
            System.out.println("Error adding appointment: " + e.getMessage());
        }
    }

    /**
     * SQL for updating appointment
     * @param appointmentID
     * @param userID
     * @param customerID
     * @param contact
     * @param title
     * @param description
     * @param location
     * @param type
     * @param date
     * @param start
     * @param end
     */
    public static void updateAppointment(Integer appointmentID, Integer userID, Integer customerID, String contact, String title, String description, String location, String type, String date, String start, String end) {
        int currentContact = convertToContactID(contact);
        String dateStart = TimeUtility.timeToUTC(date, start);
        String dateEnd = TimeUtility.timeToUTC(date, end);
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("UPDATE appointments" + " SET User_ID = '%s', Customer_ID = '%s', Contact_ID = '%s', Title = '%s', Description = '%s', Location = '%s', Type = '%s', Start = '%s', End = '%s'" + " WHERE Appointment_ID='%s'",
                    userID, customerID, currentContact, title, description, location, type, dateStart, dateEnd, appointmentID);
            Query.makeQuery(sqlQuery);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
    }

    /**
     *SQL for deleting appointment
     * @param appointmentID int
     */
    public static void deleteAppointment(Integer appointmentID) {
        //Delete currently selected appointment
        try{
            Query.makeQuery(String.format("DELETE FROM appointments" + " WHERE Appointment_ID = '%s'", appointmentID));
        }
        catch (Exception e){
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    /**
     * Populates all User_IDs for dropdowns
     * @return ObservableList
     */
    public static ObservableList<Integer> populateUserID(){
        ObservableList<Integer> userid = FXCollections.observableArrayList();
        userid.clear();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT User_ID" + " FROM users" + " ORDER BY User_ID";
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            while (result.next()) {
                Integer id = result.getInt("User_ID");
                userid.add(id);
            }
            statement.close();
            return userid;
        } catch (Exception e) {
            System.out.println("Unable to populate User ID's.");
        }
        return null;
    }

    /**
     * Populates all Customer_IDs for dropdowns
     * @return ObservableList
     */
    public static ObservableList<Integer> populateCustomerID(){
        ObservableList<Integer> customer = FXCollections.observableArrayList();
        customer.clear();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Customer_ID" + " FROM customers" + " ORDER BY Customer_ID";
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            while (result.next()) {
                Integer customerid = result.getInt("Customer_ID");
                customer.add(customerid);
            }
            statement.close();
            return customer;
        } catch (Exception e) {
            System.out.println("Unable to populate Customer ID's.");
        }
        return null;
    }

    /**
     * Populates all Contact_Names for dropdowns
     * @return ObservableList
     */
    public static ObservableList<Contact> populateContact(){
        ObservableList<Contact> contact = FXCollections.observableArrayList();
        contact.clear();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Contact_Name" + " FROM contacts" + " ORDER BY Contact_ID";
            Query.makeQuery(sqlQuery);
            Contact contactResult;
            ResultSet result = Query.getResult();
            while (result.next()) {
                String contactname = result.getString("Contact_Name");
                contactResult = new Contact(contactname);
                contact.add(contactResult);
            }
            statement.close();
            return contact;
        } catch (Exception e) {
            System.out.println("Unable to populate Contact Names");
        }
        return null;
    }

    /**
     * Populates all User_IDs for dropdowns
     * @param appointmentID int
     * @return ObservableList
     */
    public static Appointment readAppointment(Integer appointmentID){
        //Provides information about Appointment for populating appointment modify form
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Appointment_ID, User_ID, Customer_ID, Contact_Name, Title, Description, Location, Type, Start, End" + " FROM appointments a" + " INNER JOIN contacts c ON c.Contact_ID=a.CONTACT_ID" + " WHERE Appointment_ID='%s'", appointmentID);
            Query.makeQuery(sqlQuery);
            Appointment appointmentResult;
            ResultSet result = Query.getResult();
            while(result.next()){
                String dateStart = TimeUtility.timeToLocalShort((result.getString("Start").substring(0,10)) + " " + (result.getString("Start").substring(11,19)));
                String dateEnd = TimeUtility.timeToLocalShort((result.getString("Start").substring(0,10)) + " " + (result.getString("End").substring(11,19)));
                int aid = result.getInt("Appointment_ID");
                int uid = result.getInt("User_ID");
                int cid = result.getInt("Customer_ID");
                String conName = result.getString("Contact_Name");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                String date = dateStart.substring(0,10);
                String start = dateStart.substring(11,19);
                String end = dateEnd.substring(11,19);
                appointmentResult = new Appointment(aid, uid, cid, conName, title, description, location, type, date, start, end);
                return appointmentResult;
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Unable to find appointment with ID: " + appointmentID);
        }
        return null;
    }

    /**
     * Transforms Contact names to IDs
     * @param contact string
     * @return int
     */
    public static int convertToContactID(String contact) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Contact_ID" + " FROM contacts" + " WHERE Contact_Name='%s'", contact);
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return result.getInt("Contact_ID");
        } catch (SQLException e) {
            System.out.println("SQL Error (Convert to ContactID): " + e.getMessage());
            return 0;
        }
    }

    /**
     * Transforms Contact IDs to Names
     * @param contact int
     * @return String
     */
    public static String convertToContactName(int contact) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Contact_Name" + " FROM contacts" + " WHERE Contact_ID='%s'", contact);
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return result.getString("Contact_Name");
        } catch (SQLException e) {
            System.out.println("SQL Error (Convert to ContactName): " + e.getMessage());
            return null;
        }
    }
}
