package DAOImplementations;

import java.sql.ResultSet;
import java.sql.Statement;
import static DAOImplementations.DatabaseConnection.getConnection;

/**
 * Class for correct Query formatting
 */
public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet result;

    /**
     * Makes SQL Query
     * @param q String
     */
    public static void makeQuery(String q){
        query = q;
        try{
            statement=getConnection().createStatement();
            if(query.toLowerCase().startsWith("select"))
                result = statement.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                statement.executeUpdate(q);
        }
        catch(Exception ex){
            System.out.println("SQL Errror: " + ex.getMessage());
        }
    }

    /**
     * Returns result of query
     * @return ResultSet
     */
    public static ResultSet getResult(){
        return result;
    }

}
