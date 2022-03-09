package Exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class for Customer Errors and Exceptions
 */
public class CustomerExceptions {

    /**
     * Customer Errors
     * @param code int
     */
    public static void customerError(int code) {
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setTitle("Customer Error");
        warning.setHeaderText("Cannot save customer data.");

        switch (code) {
            case 1: {
                warning.setContentText("Name is Blank");
                break;
            }
            case 2: {
                warning.setContentText("Address is Blank");
                break;
            }
            case 3: {
                warning.setContentText("Country is Blank");
                break;
            }
            case 4: {
                warning.setContentText("State/Province is Blank");
                break;
            }
            case 5: {
                warning.setContentText("Postal Code is Blank");
                break;
            }
            case 6: {
                warning.setContentText("Phone Number is Blank");
                break;
            }
            default: {
                warning.setContentText("Unknown error!");
                break;
            }
        }
        warning.showAndWait();
    }
    /**
     * No Customers Handler
     */
    public static void noCustomer() {
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setTitle("Customer Error");
        warning.setHeaderText("No Customer Selected");
        warning.setContentText("No Customer was Selected");
        warning.showAndWait();
    }

    /**
     * Verify Customer Delete
     * @param name
     * @return boolean
     */
    public static boolean verifyDelete(String name)
    {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Delete Customer");
        warning.setHeaderText("Confirm Delete of " + name + "?  This will also delete all associated appointments.");
        warning.setContentText("Click OK to Confirm");
        Optional<ButtonType> answer = warning.showAndWait();
        return answer.get() == ButtonType.OK;
    }
}
