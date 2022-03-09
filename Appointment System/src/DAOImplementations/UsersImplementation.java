package DAOImplementations;

import Models.User;
import Utilities.LogUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for User Implementations
 */
public class UsersImplementation {
    private static User newUser;

    public static User getNewUser() {
        return newUser;
    }

    /**
     * Login Attempt handler
     * @param user
     * @param pass
     * @return boolean
     */
    public static Boolean loginAttempt(String user, String pass) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = "SELECT * FROM users WHERE User_Name = '" + user + "' AND Password = '" + pass + "'";
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            if (result.next()) {
                newUser = new User();
                User.setUserName(result.getString("user_Name"));
                statement.close();
                LogUtility.logAttempt(user, true);
                return true;
            } else {
                LogUtility.logAttempt(user, false);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error (Login Attempt): " + e.getMessage());
            return false;
        }
    }
}