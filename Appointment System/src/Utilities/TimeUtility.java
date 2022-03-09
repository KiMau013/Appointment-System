package Utilities;

import DAOImplementations.DatabaseConnection;
import DAOImplementations.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Class for handling all Time Wizardry
 */
public class TimeUtility {

    /**
     * Transforms time to UTC
     * @param Date
     * @param Time
     * @return String
     */
    public static String timeToUTC(String Date, String Time){
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate currentDate = LocalDate.parse(Date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime currentTime = LocalTime.parse(Time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        ZonedDateTime currentZone = ZonedDateTime.of(currentDate, currentTime, ZoneId.systemDefault());
        ZonedDateTime currentZonetoUTC = currentZone.withZoneSameInstant(ZoneOffset.UTC);
        return currentZonetoUTC.format(dateTimeFormat);
    }

    /**
     * Transforms time to Local
     * @param DateTime
     * @return String
     */
    public static String timeToLocalShort(String DateTime){
        String currentDateTime = DateTime.replace(' ','T');
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(currentDateTime + "+00:00");
        OffsetDateTime getSystemOffset = offsetDateTime.withOffsetSameInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
        return getSystemOffset.format(dateTimeFormat);
    }

    /**
     * Transforms time for Login
     * @param DateTime
     * @return String
     */
    public static String timetoLocalLogin(String DateTime){
        String currentDateTime = DateTime.replace(' ','T');
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(currentDateTime + "+00:00");
        OffsetDateTime getSystemOffset = offsetDateTime.withOffsetSameInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
        return getSystemOffset.format(dateTimeFormat);
    }

    /**
     * Transforms time to Business
     * @param Date
     * @param Time
     * @return String
     */
    public static String timeToBusiness(String Date, String Time){
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate currentDate = LocalDate.parse(Date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime currentTime = LocalTime.parse(Time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        ZonedDateTime currentZone = ZonedDateTime.of(currentDate, currentTime, ZoneId.systemDefault());
        ZonedDateTime currentZonetoEST = currentZone.withZoneSameInstant(ZoneOffset.ofHours(-4));
        return currentZonetoEST.format(dateTimeFormat);
    }

    /**
     * Populates Time Dropdowns
     * @return ObservableList
     */
    public static ObservableList<String> populateTime() {
        ObservableList<String> timeslots = FXCollections.observableArrayList();
        try {
            timeslots.removeAll();
            for (int i = 0; i < 24; i++ ){
                String hour;
                if (i < 10) { hour = "0" + i; }
                else { hour = Integer.toString(i); }
                timeslots.add(hour + ":00:00");
                timeslots.add(hour + ":15:00");
                timeslots.add(hour + ":30:00");
                timeslots.add(hour + ":45:00");
            }
            timeslots.add("24:00:00");

        } catch (Exception e) {
            System.out.println("Error with time: " + e.getMessage());
        }
        return timeslots;
    }

    /**
     * Checks for Appointment Overlap
     * @param date
     * @param start
     * @param end
     * @return boolean
     */
    public static boolean checkAppointmentOverlap(String date, String start, String end){
        try{
            String currentStart = timeToUTC(date, start);
            String currentEnd = timeToUTC(date, end);

            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Start, End, Customer_Name" + " FROM appointments a " + " INNER JOIN customers c ON c.Customer_ID=a.Customer_ID" + " WHERE ('%s' >= Start AND '%s' < End)"  + " OR ('%s' <= Start AND '%s' > Start)" + " OR ('%s' < End AND '%s' >= End)" + " OR ('%s' < Start AND '%s' >= End)", currentStart, currentStart, currentStart, currentEnd, currentStart, currentEnd, currentEnd, currentEnd);
            Query.makeQuery(sqlQuery);
            ResultSet overlap = Query.getResult();
            statement.close();
            overlap.next();
            System.out.println("Appointment Overlaps: " + overlap.getString("Customer_Name"));
            return false;
        } catch (SQLException e) {
            System.out.println("SQL Error (Appointment Overlap): " + e.getMessage());
            return true;
        }
    }

    /**
     * Checks to see if Appointment already Exists
     * @param date
     * @param start
     * @param end
     * @param appointmentid
     * @param customer
     * @return boolean
     */
    public static boolean checkAlreadyExists(String date, String start, String end, Integer appointmentid, Integer customer){
        try {
            String currentStart = timeToUTC(date, start);
            String currentEnd = timeToUTC(date, end);

            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Start, End, Customer_Name, Appointment_ID, a.Customer_ID" + " FROM appointments a" + " INNER JOIN customers c ON c.Customer_ID=a.Customer_ID" + " WHERE ('%s' >= Start AND '%s' < End)" + " OR ('%s' <= Start AND '%s' > Start)" + " OR ('%s' < End AND '%s' >= End)" + " OR ('%s' < Start AND '%s' >= End)", currentStart, currentStart, currentStart, currentEnd, currentStart, currentEnd, currentEnd, currentEnd);
            Query.makeQuery(sqlQuery);
            ResultSet overlap = Query.getResult();
            statement.close();
            overlap.next();
            if (overlap.getInt("Customer_ID") == customer && overlap.getInt("Appointment_ID") == appointmentid) {
                System.out.println("Appointment Overlaps Self (Allowed): " + overlap.getString("Customer_Name"));
                return true;
            } else {
                System.out.println("Appointment overlaps another: " + overlap.getString("Customer_Name") + " - Customer ID: " + customer + " & Appointment_ID: " + overlap.getString("Appointment_ID") + " - " + appointmentid);
                return false;
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Error (Already Exists): " + e.getMessage());
            return true;
        }
    }

    /**
     * For checking to make sure appointments are within Business Hours
     * @param date
     * @param start
     * @param end
     * @return boolean
     */
    public static boolean businessTime(String date, String start, String end){
        String currentStart = timeToBusiness(date, start);
        String currentEnd = timeToBusiness(date, end);
        String bStart = currentStart.substring(11, 16);
        String bEnd = currentEnd.substring(11, 16);

        LocalTime startTime = LocalTime.parse(bStart);
        LocalTime endTime = LocalTime.parse(bEnd);
        LocalTime businessOpen = LocalTime.parse("07:59");
        LocalTime businessClose = LocalTime.parse("22:01");
        Boolean startVerify = startTime.isAfter(businessOpen);
        Boolean endVerify = endTime.isBefore(businessClose);

        return endVerify && startVerify;
    }
}
