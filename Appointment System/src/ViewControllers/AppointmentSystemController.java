package ViewControllers;

import DAOImplementations.AppointmentsImplementation;
import Exceptions.AppointmentExceptions;
import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Controller for Main Appointment System Screen
 */
public class AppointmentSystemController implements Initializable {

    @FXML
    private AnchorPane DatePicker;

    @FXML
    private TableView<Appointment> AppointmentTable;
    private ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

    @FXML
    private RadioButton RadioViewMonth;

    @FXML
    private ToggleGroup ViewToggle;

    @FXML
    private RadioButton RadioViewAll;

    @FXML
    private RadioButton RadioViewWeek;

    @FXML
    private DatePicker DateSelect;

    /**
     * Go To Add Appointment
     * @param event
     * @throws IOException
     */
    @FXML
    void AddClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentAdd.fxml"));
        AppointmentAddController controller = new AppointmentAddController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Go To Modify Appointment with Error Checking
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyClick(MouseEvent event) throws IOException{
        Appointment test = AppointmentTable.getSelectionModel().getSelectedItem();
        try {
            if (AppointmentList.isEmpty()) {
                AppointmentExceptions.noAppointment();
                return;
            }
            if (!AppointmentList.isEmpty() && test == null) {
                AppointmentExceptions.noAppointment();
                return;
            } else {
                Appointment current = AppointmentsImplementation.readAppointment(AppointmentTable.getSelectionModel().getSelectedItem().getAppointmentId());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentModify.fxml"));
                AppointmentModifyController controller = new AppointmentModifyController(current);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }
        catch(IOException e){
            System.out.println("Error getting appointment info: " + e.getMessage());
        }
    }

    /**
     * Deletes Selected Appointment
     * @param event
     */
    @FXML
    void DeleteClick(MouseEvent event) {
        Appointment current = AppointmentTable.getSelectionModel().getSelectedItem();
        if(AppointmentList.isEmpty()){
            AppointmentExceptions.noAppointment();
            return;
        }
        if(!AppointmentList.isEmpty() && current == null){
            AppointmentExceptions.noAppointment();
            return;
        }
        else {
            boolean confirm = AppointmentExceptions.verifyDelete(current.getAppointmentId(), current.getType());
            if (!confirm) {
                return;
            }
            AppointmentsImplementation.deleteAppointment(current.getAppointmentId());
            if (ViewToggle.getSelectedToggle().equals(RadioViewAll)) {
                setAllAppointments();
            } else if (ViewToggle.getSelectedToggle().equals(RadioViewMonth)) {
                setMonthAppointments();
            } else if (ViewToggle.getSelectedToggle().equals(RadioViewWeek)) {
                setWeekAppointments();
            }
            AppointmentTable.refresh();
        }
    }

    /**
     * Go To Schedule for Contacts
     * @param event
     * @throws IOException
     */
    @FXML
    void ContactScheduleClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportContact.fxml"));
        ReportContactController controller = new ReportContactController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Go To Customer Records
     * @param event
     * @throws IOException
     */
    @FXML
    void CustomerRecordClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerRecords.fxml"));
        CustomerRecordsController controller = new CustomerRecordsController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Log Out and return to Login page
     * @param event
     * @throws IOException
     */
    @FXML
    void LogOutClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        LoginController controller = new LoginController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Go To Report by Customers
     * @param event
     * @throws IOException
     */
    @FXML
    void ReportCustomerClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportByCustomer.fxml"));
        ReportByCustomerController controller = new ReportByCustomerController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Go To Report by Location
     * @param event
     * @throws IOException
     */
    @FXML
    void ReportLocationClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportByLocation.fxml"));
        ReportByLocationController controller = new ReportByLocationController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Go To Report by Type
     * @param event
     * @throws IOException
     */
    @FXML
    void ReportTypeClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportByType.fxml"));
        ReportByTypeController controller = new ReportByTypeController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     *Lambda Function - Inline; Toggle Listener waits for change in ToggleGroup to adjust TableView based on selection
     */
    public void toggle(){
        ViewToggle.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            RadioButton rb = (RadioButton) ViewToggle.getSelectedToggle();

            if (rb != null) {
                {
                    if (ViewToggle.getSelectedToggle().equals(RadioViewAll)) {
                        setAllAppointments();
                    } else if (ViewToggle.getSelectedToggle().equals(RadioViewMonth)) {
                        setMonthAppointments();
                    } else if (ViewToggle.getSelectedToggle().equals(RadioViewWeek)) {
                        setWeekAppointments();
                    }
                }
            }
        });
    }


    /**
     * Sets All Appointments
     */
    public void setAllAppointments(){
        AppointmentTable.getItems().clear();
        AppointmentList.setAll(AppointmentsImplementation.allAppointments());
        AppointmentTable.setItems(AppointmentList);
        AppointmentTable.refresh();
    }

    /**
     * Sets Current Month Appointments
     */
    public void setMonthAppointments(){
        AppointmentTable.getItems().clear();
        AppointmentList.setAll(AppointmentsImplementation.monthAppointments());
        AppointmentTable.setItems(AppointmentList);
        AppointmentTable.refresh();
    }

    /**
     * Sets Current Week Appointments
     */
    public void setWeekAppointments(){
        AppointmentTable.getItems().clear();
        AppointmentList.setAll(AppointmentsImplementation.weekAppointments());
        AppointmentTable.setItems(AppointmentList);
        AppointmentTable.refresh();
    }

    /**
     * Initializer
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        ViewToggle.selectToggle(RadioViewAll);
        setAllAppointments();
        toggle();
    }

}

