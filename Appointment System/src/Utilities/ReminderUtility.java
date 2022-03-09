package Utilities;

import DAOImplementations.DatabaseConnection;
import DAOImplementations.Query;
import Models.Appointment;
import Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Class for 15 minute Reminder
 */
public class ReminderUtility  {
    public static Appointment Fifteen(){
        Appointment appointment;
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = TimeUtility.timeToUTC(LocalDate.now().toString(), LocalTime.now().toString().substring(0,8));
        LocalDateTime inFifteen = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(15);
        String later = inFifteen.format(dateTimeFormat);
        String currentUser = User.getUserName();

        try{
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT Appointment_ID, Type, Start, End" + " FROM appointments a" + " INNER JOIN users u ON u.User_ID=a.User_ID WHERE Start BETWEEN '" + now + "' AND '" + later + "' AND " + "User_Name='" + currentUser + "'";
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            if (result.next()) {
                Integer appointmentId = result.getInt("Appointment_ID");
                String atype = result.getString("Type");
                String adate = result.getString("Start").substring(0,10);
                String astart = result.getString("Start").substring(11,19);
                String aend = result.getString("End").substring(11,19);
                appointment = new Appointment(appointmentId, atype, adate, astart, aend);
                return appointment;
            }
            else{
                return null;
            }
        }
        catch (SQLException e){
            System.out.println("SQL Error (FifteenReminder): " + e.getMessage());
        }
        return null;
    }
}
