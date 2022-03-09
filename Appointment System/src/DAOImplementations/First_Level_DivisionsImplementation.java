package DAOImplementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for First_Level_Divisions Implementation
 */
public class First_Level_DivisionsImplementation {

    /**
     * Converts to correct First Level Division
     * @param firstLevel
     * @return int
     */
    public static int convertToDivision(String firstLevel) {
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Division_ID" + " FROM first_level_divisions" + " WHERE Division='%s'", firstLevel);
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return result.getInt("Division_ID");
        } catch (SQLException e) {
            System.out.println("SQL1 Error: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Converts to State/Province
     * @param firstLevel
     * @return String
     */
    public static String convertToStateProvince(Integer firstLevel){
        try {
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Division" + " FROM first_level_divisions" + " WHERE Division_ID='%s'", firstLevel);
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return result.getString("Division");
        } catch (SQLException e) {
            System.out.println("SQL2 Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converts to Country
     * @param firstLevel
     * @return String
     */
    public static String convertToCountry(Integer firstLevel){
        try{
            Statement statement = DatabaseConnection.getConnection().createStatement();
            String sqlQuery = String.format("SELECT Country" + " FROM countries c" + " INNER JOIN first_level_divisions f" + " ON f.Country_ID=c.Country_ID" + " WHERE Division_ID='%s'", firstLevel);
            Query.makeQuery(sqlQuery);
            ResultSet result = Query.getResult();
            statement.close();
            result.next();
            return result.getString("Country");
        } catch (SQLException e) {
            System.out.println("SQL3 Error: " + e.getMessage());
            return null;
        }
    }
}