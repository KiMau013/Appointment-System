package ViewControllers;

import DAOImplementations.CustomersImplementation;
import Models.First_Level_Division;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Adding Customers
 */
public class CustomerAddController implements Initializable {

    @FXML
    private TextField CustomerID;

    @FXML
    private TextField Name;

    @FXML
    private TextField Address;

    @FXML
    private ComboBox<String> Country;

    @FXML
    private ComboBox<First_Level_Division> StateProvince;

    @FXML
    private TextField PostalCode;

    @FXML
    private TextField PhoneNumber;

    /**
     * Return to Main
     * @param event
     * @throws IOException
     */
    @FXML
    void ReturnClick(MouseEvent event) throws IOException {
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
     * Save Customer with Error Checking
     * @param event
     * @throws IOException
     */
    @FXML
    void SaveClick(MouseEvent event) throws IOException {
        if(Name.getText().trim().isEmpty()){
            Exceptions.CustomerExceptions.customerError(1);
            return;
        }
        if(Address.getText().trim().isEmpty()){
            Exceptions.CustomerExceptions.customerError(2);
            return;
        }
        if(Country.getSelectionModel().isEmpty()){
            Exceptions.CustomerExceptions.customerError(3);
            return;
        }
        if(StateProvince.getSelectionModel().isEmpty()){
            Exceptions.CustomerExceptions.customerError(4);
            return;
        }
        if(PostalCode.getText().trim().isEmpty()){
            Exceptions.CustomerExceptions.customerError(5);
            return;
        }
        if(PhoneNumber.getText().trim().isEmpty()){
            Exceptions.CustomerExceptions.customerError(6);
        }
        else{
            CustomersImplementation.addCustomer(Integer.parseInt(CustomerID.getText().trim()), Name.getText().trim(), Address.getText().trim(), StateProvince.getSelectionModel().getSelectedItem().toString().trim(), PostalCode.getText().trim(), PhoneNumber.getText().trim());
            ReturnClick(event);
        }
    }

    /**
     * Correctly populates Countries
     */
    public void populateCountries() {
        Country.getItems().setAll(
                "U.S",
                "UK",
                "Canada");
        StateProvince.setPromptText("You must select a Country first.");
        Country.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                StateProvince.getItems().clear();
                StateProvince.setDisable(true);
            }
            else{
                StateProvince.setPromptText("Select State/Province");
                populateCities();
            }
        });
    }

    /**
     * Correctly populates Cities
     */
    @FXML
    private void populateCities() {
        if(Country.getSelectionModel().isEmpty()){
            StateProvince.setPromptText("You must select a Country first.");
        }
        else {
            StateProvince.getItems().setAll(CustomersImplementation.populateDivision(Country.getSelectionModel().getSelectedItem()));
            StateProvince.setDisable(false);
        }
    }

    /**
     * Initializer
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        Country.getItems().clear();
        StateProvince.getItems().clear();
        CustomerID.setText(CustomersImplementation.getNextCustomer());
        populateCountries();
    }
}