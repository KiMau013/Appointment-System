package ViewControllers;

import DAOImplementations.AppointmentsImplementation;
import Exceptions.AppointmentExceptions;
import Models.Contact;
import Utilities.TimeUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Adding Appointments
 */
public class AppointmentAddController implements Initializable {

    @FXML
    private TextField AppointmentID;

    @FXML
    private ComboBox<Integer> UserID;

    @FXML
    private ComboBox<Integer> CustomerID;

    @FXML
    private ComboBox<Contact> Contact;

    @FXML
    private TextField Title;

    @FXML
    private TextField Description;

    @FXML
    private TextField Location;

    @FXML
    private TextField Type;

    @FXML
    private DatePicker Date;

    @FXML
    private ComboBox<String> StartTime;

    @FXML
    private ComboBox<String> EndTime;

    /**
     * Returns to Main
     * @param event
     * @throws IOException
     */
    @FXML
    void ReturnClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentSystem.fxml"));
        AppointmentSystemController controller = new AppointmentSystemController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Save Appointment with Error Checking
     * @param event
     * @throws IOException
     */
    @FXML
    void SaveClick(MouseEvent event) throws IOException {
        int checkStart = StartTime.getSelectionModel().getSelectedIndex();
        int checkEnd = EndTime.getSelectionModel().getSelectedIndex();

        if(UserID.getSelectionModel().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(1);
            return;
        }
        if(CustomerID.getSelectionModel().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(2);
            return;
        }
        if(Contact.getSelectionModel().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(3);
            return;
        }
        if(Title.getText().trim().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(4);
            return;
        }
        if(Description.getText().trim().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(5);
            return;
        }
        if(Location.getText().trim().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(6);
            return;
        }
        if(Type.getText().trim().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(7);
            return;
        }
        if(Date.getValue() == null){
            Exceptions.AppointmentExceptions.appointmentError(8);
            return;
        }
        if(StartTime.getValue().equals(EndTime.getValue())){
            Exceptions.AppointmentExceptions.appointmentError(9);
            return;
        }
        if(StartTime.getSelectionModel().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(10);
            return;
        }
        if(EndTime.getSelectionModel().isEmpty()){
            Exceptions.AppointmentExceptions.appointmentError(11);
            return;
        }
        if(checkEnd < checkStart){
            Exceptions.AppointmentExceptions.appointmentError(12);
            return;
        }
        else{
            String currentDate = Date.getValue().toString().trim();
            String currentStart = StartTime.getSelectionModel().getSelectedItem().trim();
            String currentEnd = EndTime.getSelectionModel().getSelectedItem().trim();
            if (TimeUtility.checkAppointmentOverlap(currentDate, currentStart, currentEnd) && TimeUtility.businessTime(currentDate, currentStart, currentEnd)) {
                AppointmentsImplementation.addAppointment(Integer.parseInt(AppointmentID.getText().trim()), (Integer.parseInt(UserID.getSelectionModel().getSelectedItem().toString().trim())), (Integer.parseInt(CustomerID.getSelectionModel().getSelectedItem().toString().trim())), Contact.getSelectionModel().getSelectedItem().toString().trim(), Title.getText().trim(), Description.getText().trim(), Location.getText().trim(), Type.getText().trim(), Date.getValue().toString().trim(), StartTime.getSelectionModel().getSelectedItem().trim(), EndTime.getSelectionModel().getSelectedItem().trim());
                ReturnClick(event);
            }
            else {
                if (!TimeUtility.checkAppointmentOverlap(currentDate, currentStart, currentEnd)){
                    AppointmentExceptions.appointmentError(13);
                }
                if (!TimeUtility.businessTime(currentDate, currentStart, currentEnd)){
                    AppointmentExceptions.appointmentError(14);
                }
            }
        }
    }

    /**
     * Initializer
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
           StartTime.setItems(TimeUtility.populateTime());
           EndTime.setItems(TimeUtility.populateTime());
           AppointmentID.setText(AppointmentsImplementation.getNextAppointment());
           UserID.getItems().setAll(AppointmentsImplementation.populateUserID());
           CustomerID.getItems().setAll(AppointmentsImplementation.populateCustomerID());
           Contact.getItems().setAll(AppointmentsImplementation.populateContact());
    }
}
