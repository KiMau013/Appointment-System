package ViewControllers;

import DAOImplementations.AppointmentsImplementation;
import Exceptions.AppointmentExceptions;
import Models.Appointment;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for Modifying Appointments
 */
public class AppointmentModifyController implements Initializable {

    Appointment appointment;

    public AppointmentModifyController(Appointment appointment){ this.appointment = appointment;}

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
     * Save Appointment Edits with Error Checking
     * @param event
     * @throws IOException
     */
    @FXML
    void SaveClick(MouseEvent event) throws IOException {
        int checkStart = StartTime.getSelectionModel().getSelectedIndex();
        int checkEnd = EndTime.getSelectionModel().getSelectedIndex();

        if(UserID.getSelectionModel().isEmpty()){
            AppointmentExceptions.appointmentError(1);
            return;
        }
        if(CustomerID.getSelectionModel().isEmpty()){
            AppointmentExceptions.appointmentError(2);
            return;
        }
        if(Contact.getSelectionModel().isEmpty()){
            AppointmentExceptions.appointmentError(3);
            return;
        }
        if(Title.getText().trim().isEmpty()){
            AppointmentExceptions.appointmentError(4);
            return;
        }
        if(Description.getText().trim().isEmpty()){
            AppointmentExceptions.appointmentError(5);
            return;
        }
        if(Location.getText().trim().isEmpty()){
            AppointmentExceptions.appointmentError(6);
            return;
        }
        if(Type.getText().trim().isEmpty()){
            AppointmentExceptions.appointmentError(7);
            return;
        }
        if(Date.getValue() == null){
            AppointmentExceptions.appointmentError(8);
            return;
        }
        if(StartTime.getValue().equals(EndTime.getValue())){
            AppointmentExceptions.appointmentError(9);
            return;
        }
        if(Contact.getSelectionModel().isEmpty()){
            AppointmentExceptions.appointmentError(10);
            return;
        }
        if(Contact.getSelectionModel().isEmpty()){
            AppointmentExceptions.appointmentError(11);
            return;
        }
        if(checkEnd < checkStart){
            AppointmentExceptions.appointmentError(12);
            return;
        }
        else{
            String currentDate = Date.getValue().toString().trim();
            String currentStart = StartTime.getSelectionModel().getSelectedItem().trim();
            String currentEnd = EndTime.getSelectionModel().getSelectedItem().trim();
            Integer currentAppointmentID = (Integer.parseInt(AppointmentID.getText().trim()));
            Integer currentCustomerID = (Integer.parseInt(CustomerID.getSelectionModel().getSelectedItem().toString().trim()));

            if (TimeUtility.checkAlreadyExists(currentDate, currentStart, currentEnd, currentAppointmentID, currentCustomerID) && TimeUtility.businessTime(currentDate, currentStart, currentEnd)) {
                    AppointmentsImplementation.updateAppointment(Integer.parseInt(AppointmentID.getText().trim()), (Integer.parseInt(UserID.getSelectionModel().getSelectedItem().toString().trim())), (Integer.parseInt(CustomerID.getSelectionModel().getSelectedItem().toString().trim())), Contact.getSelectionModel().getSelectedItem().toString().trim(), Title.getText().trim(), Description.getText().trim(), Location.getText().trim(), Type.getText().trim(), Date.getValue().toString().trim(), StartTime.getSelectionModel().getSelectedItem().trim(), EndTime.getSelectionModel().getSelectedItem().trim());
                    ReturnClick(event);
            }
            else{
                if (!TimeUtility.businessTime(currentDate, currentStart, currentEnd)) {
                    AppointmentExceptions.appointmentError(14);
                    return;
                }
                if (!TimeUtility.checkAlreadyExists(currentDate, currentStart, currentEnd, currentAppointmentID, currentCustomerID)) {
                    AppointmentExceptions.appointmentError(15);
                    return;
                }
            }
        }
    }

    /**
     * Populates the Appointment with correct information
     */
    private void populateAppointment(){
        this.AppointmentID.setText((Integer.toString(appointment.getAppointmentId())));
        this.UserID.getSelectionModel().select(appointment.getUserId());
        this.CustomerID.getSelectionModel().select(appointment.getCustomerId());
        this.Title.setText(appointment.getTitle());
        this.Description.setText(appointment.getDescription());
        this.Location.setText(appointment.getLocation());
        this.Type.setText(appointment.getType());
        this.Date.setValue(LocalDate.parse(appointment.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        this.StartTime.getSelectionModel().select(appointment.getStart());
        this.EndTime.getSelectionModel().select(appointment.getEnd());
    }

    /**
     * Selects correct Contact
     */
    public void pickContact(){
        for (Contact contact : Contact.getItems()) {
            if (contact.toString().equals(appointment.getContact())){
                Contact.getSelectionModel().select(contact);
                break;
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
        populateAppointment();
        pickContact();
    }
}

