package ViewControllers;

import DAOImplementations.ContactsImplementation;
import DAOImplementations.CustomersImplementation;
import Models.Appointment;
import Models.Contact;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Contact Report
 */
public class ReportContactController implements Initializable {

    @FXML
    private TableView<Appointment> ContactTable;

    @FXML
    private ComboBox<Contact> ContactDropdown;

    /**
     * Return to Main
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
     * Populates Dropdown of Contacts to Choose From
     */
    public void populateContacts() {
        ContactDropdown.setPromptText("Please select a Contact");
        ContactDropdown.getItems().setAll(ContactsImplementation.readAllContacts());
        ContactDropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                ContactTable.getItems().clear();
                ContactTable.refresh();
            }
            else{
                populateContactTable();
            }
        });
    }

    /**
     * Populates Table based on Dropdown
     */
    public void populateContactTable(){
        ContactTable.getItems().setAll(ContactsImplementation.getContactAppointments(ContactDropdown.getSelectionModel().getSelectedItem()));
        ContactTable.refresh();
    }

    /**
     * Initializer
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        populateContacts();
    }
}
