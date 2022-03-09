package DAOImplementations;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class for Reports Implementations
 */
public class ReportsImplementation {

    /**
     * For generating Appointment report by Type
     * @return ObservableList
     */
    public static ObservableList<Appointment> byType() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT MONTHNAME(Start), Type, COUNT(Type)" + " FROM appointments" + " GROUP BY MONTHNAME(Start), Type;";
            Query.makeQuery(sqlQuery);
            Appointment appointmentResult;
            ResultSet result = Query.getResult();
            while (result.next()) {
                String month = result.getString("MONTHNAME(Start)");
                String type = result.getString("Type");
                Integer count = result.getInt("COUNT(Type)");
                appointmentResult = new Appointment(month, type, count);
                appointments.add(appointmentResult);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to populate appointments.");
        }
        return null;
    }

    /**
     * For generating Appointment report by Customer
     * @return ObservableList
     */
    public static ObservableList<Appointment> byCustomer() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT MONTHNAME(Start), Customer_ID, COUNT(Customer_ID)" + " FROM appointments" + " GROUP BY MONTHNAME(Start), Customer_ID;";
            Query.makeQuery(sqlQuery);
            Appointment appointmentResult;
            ResultSet result = Query.getResult();
            while (result.next()) {
                String month = result.getString("MONTHNAME(Start)");
                Integer customer = result.getInt("Customer_ID");
                Integer count = result.getInt("COUNT(Customer_ID)");
                appointmentResult = new Appointment(month, customer, count);
                appointments.add(appointmentResult);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to populate appointments.");
        }
        return null;
    }

    /**
     * For generating Appointment report by Location
     * @return ObservableList
     */
    public static ObservableList<Appointment> byLocation() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT MONTHNAME(Start), Location, COUNT(Location)" + " FROM appointments" + " GROUP BY MONTHNAME(Start), Location;";
            Query.makeQuery(sqlQuery);
            Appointment appointmentResult;
            ResultSet result = Query.getResult();
            while (result.next()) {
                String month = result.getString("MONTHNAME(Start)");
                String location = result.getString("Location");
                Integer count = result.getInt("COUNT(Location)");
                appointmentResult = new Appointment(month, location, count);
                appointments.add(appointmentResult);
            }
            statement.close();
            return appointments;
        } catch (Exception e) {
            System.out.println("Unable to populate appointments.");
        }
        return null;
    }
}

