package DAOImplementations;

import Models.Appointment;
import Models.Contact;
import Utilities.TimeUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class for Contact Implementations
 */
public class ContactsImplementation {

    /**
     * Provides information about Customer for populating customer table
     * @return ObservableList
     */
    public static ObservableList<Contact> readAllContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Contact_ID, Contact_Name" + " FROM contacts";
            Query.makeQuery(sqlQuery);
            Contact contactResult;
            ResultSet result = Query.getResult();
            while(result.next()){
                Integer cid = result.getInt("Contact_ID");
                String cname = result.getString("Contact_Name");
                contactResult = new Contact(cid, cname);
                contacts.add(contactResult);
            }
            statement.close();
            return contacts;
        } catch (Exception e) {
            System.out.println("Unable to find any contacts.");
        }
        return null;
    }

    /**
     * Populates all appointments for Contact Report
     * @param contact contact
     * @return ObservableList
     */
    public static ObservableList<Appointment> getContactAppointments(Contact contact) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID" + " FROM appointments a" + " INNER JOIN contacts c ON c.Contact_ID=a.Contact_ID" + " WHERE c.Contact_Name='%s'", contact);
            Query.makeQuery(sqlQuery);
            Appointment contactResult;
            ResultSet result = Query.getResult();
            while(result.next()){
                Integer appointmentId = result.getInt("Appointment_ID");
                String atitle = result.getString("Title");
                String atype = result.getString("Type");
                String adescription = result.getString("Description");
                String astart = TimeUtility.timeToLocalShort(result.getString("Start")).replace('T',' ');
                String aend = TimeUtility.timeToLocalShort(result.getString("End")).replace('T',' ');
                Integer cid = result.getInt("Customer_ID");
                contactResult = new Appointment(appointmentId, atitle, atype, adescription, astart, aend, cid);
                appointments.add(contactResult);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to find any appointments for contact.");
        }
        return null;
    }
}
