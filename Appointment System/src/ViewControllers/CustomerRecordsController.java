package ViewControllers;

import DAOImplementations.CustomersImplementation;
import Exceptions.CustomerExceptions;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Customer Records
 */
public class CustomerRecordsController implements Initializable {

    @FXML private TableColumn<Customer, Integer> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> postalColumn;
    @FXML private TableColumn<Customer, String> stateProvinceColumn;
    @FXML private TableColumn<Customer, String> countryColumn;


    @FXML
    private TableView<Customer> CustomerTable;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * Populates all Customers
     */
    private void getCustomers(){
        customerList.setAll(CustomersImplementation.readAllCustomers());
        CustomerTable.setItems(customerList);
    }

    /**
     * Go to Customer Add
     * @param event
     * @throws IOException
     */
    @FXML
    void AddClick(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerAdd.fxml"));
        CustomerAddController controller = new CustomerAddController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Go to Customer Modify with Error Checking
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyClick(MouseEvent event) throws IOException{
        try {
            Customer current = CustomersImplementation.readCustomer(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId());
            if(customerList.isEmpty()){
                CustomerExceptions.noCustomer();
                return;
            }
            if(!customerList.isEmpty() && current == null){
                CustomerExceptions.noCustomer();
                return;
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerModify.fxml"));
                CustomerModifyController controller = new CustomerModifyController(current);
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
            System.out.println("Error getting customer info: " + e.getMessage());
        }
    }

    /**
     * Delete the selected Customer
     * @param event
     */
    @FXML
    void DeleteClick(MouseEvent event) {
        Customer current = CustomerTable.getSelectionModel().getSelectedItem();
        if(customerList.isEmpty()){
            CustomerExceptions.noCustomer();
            return;
        }
        if(!customerList.isEmpty() && current == null){
            CustomerExceptions.noCustomer();
            return;
        }
        else{
            boolean confirm = CustomerExceptions.verifyDelete(current.getCustomerName());
            if(!confirm){
                return;
            }
            CustomersImplementation.deleteCustomer(current.getCustomerId());
            getCustomers();
            CustomerTable.refresh();
        }
    }

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
     * Initializer
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        getCustomers();
    }

}

