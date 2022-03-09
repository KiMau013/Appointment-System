package Main;

import DAOImplementations.DatabaseConnection;
import ViewControllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class for Start of Program
 * @author Ki Mau
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        DatabaseConnection.startConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewControllers/Login.fxml"));
        ViewControllers.LoginController controller = new ViewControllers.LoginController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
