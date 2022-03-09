package ViewControllers;

import DAOImplementations.ReportsImplementation;
import Models.Appointment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Reports by Type
 */
public class ReportByTypeController implements Initializable {

    @FXML
    private TableView<Appointment> CountTable;

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

    public void initialize(URL url, ResourceBundle rb){
        CountTable.getItems().setAll(ReportsImplementation.byType());
        CountTable.refresh();
    }
}

