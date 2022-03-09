package ViewControllers;

import DAOImplementations.UsersImplementation;
import Models.Appointment;
import Utilities.ReminderUtility;
import Utilities.TimeUtility;
import Utilities.ZoneInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for Login
 */
public class LoginController implements Initializable{

    @FXML
    private Button LoginButton;

    @FXML
    private TextField LoginUsername;

    @FXML
    private Label UsernameLabel;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Label LoginWarning;

    @FXML
    private Label MessageLabel;

    @FXML
    private PasswordField LoginPassword;

    @FXML
    private Label ZoneIDLabel;

    private String LoginError;

    /**
     * Main Login checks for correct User and Password with Error Checks
     * @param event
     * @throws IOException
     */
    @FXML
    void LoginButtonClick(MouseEvent event) throws IOException {
        String user = LoginUsername.getText();
        String pass = LoginPassword.getText();
        boolean isUser = UsersImplementation.loginAttempt(user, pass);
        if (isUser) {
            LoginWarning.setText("");
            appointmentCheck();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentSystem.fxml"));
            AppointmentSystemController controller = new AppointmentSystemController();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {
            LoginWarning.setText(LoginError);
        }
    }

    /**
     * Checks for appointment soon
     */
    private void appointmentCheck() {
        Appointment appointment = ReminderUtility.Fifteen();
        if (appointment != null) {
            String dateTime = appointment.getDate() + " " + appointment.getStart();
            String notify = String.format("ID: %s, Date: %s, Time: %s, Type: %s", appointment.getAppointmentId(), appointment.getDate(), TimeUtility.timetoLocalLogin(dateTime).substring(11,19), appointment.getType());
            Alert warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setTitle("Reminder");
            warning.setHeaderText("Appointment in next 15 minutes.");
            warning.setContentText(notify);
            warning.showAndWait();
        } else {
            Alert warning = new Alert(Alert.AlertType.INFORMATION);
            warning.setTitle("Reminder");
            warning.setHeaderText("15 Minute Alert");
            warning.setContentText("Notification: No Appointments within 15 minutes.");
            warning.showAndWait();
        }
    }

    /**Lambda Function - Interface; Sets text for TimeZoneID on login page
     * Initializer
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Locale.setDefault(new Locale("fr")); -- Test for French
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("language/login", locale);
        UsernameLabel.setText(rb.getString("username"));
        PasswordLabel.setText(rb.getString("password"));
        LoginButton.setText(rb.getString("login"));
        MessageLabel.setText(rb.getString("message"));
        ZoneInterface current = () -> "Current TimeZone ID: " + ZoneId.systemDefault().toString();
        ZoneIDLabel.setText(current.ZoneIDTimeZone());
        LoginError = rb.getString("errorMessage");
    }
}

