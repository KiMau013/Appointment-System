package Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Class for Logging attempted Logins
 */
public class LogUtility {
    public static final String fileName = "src/LoginAttempts.txt";
    public LogUtility(){};
    public static void logAttempt(String user, boolean login){
        try {
            FileWriter filewriter = new FileWriter(fileName, true);
            PrintWriter actuallog = new PrintWriter(filewriter);
            LocalDateTime local = LocalDateTime.now();
            if (login){
                actuallog.println(user + " - Successful Login attempt at: " + local);
            }
            else {
                actuallog.println(user + " - Unsuccessful Login attempt at: " + local);
            }
            actuallog.close();
            System.out.println(user + " login attempt has been logged.");
        }
        catch (IOException ex){
            System.out.println("LogUtility error: " + ex.getMessage());
        }
    }
}
