package Exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class for Appointment Errors and Exceptions
 */
public class AppointmentExceptions {

    /**
     * Appointment Errors
     * @param code int
     */
    public static void appointmentError(int code) {
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setTitle("Appointment Error");
        warning.setHeaderText("Cannot save appointment.");

        switch (code) {
            case 1: {
                warning.setContentText("User ID is not selected");
                break;
            }
            case 2: {
                warning.setContentText("Customer ID is not selected");
                break;
            }
            case 3: {
                warning.setContentText("Contact is is not selected");
                break;
            }
            case 4: {
                warning.setContentText("Title is Blank");
                break;
            }
            case 5: {
                warning.setContentText("Description is Blank");
                break;
            }
            case 6: {
                warning.setContentText("Location is Blank");
                break;
            }
            case 7: {
                warning.setContentText("Type is Blank");
                break;
            }
            case 8: {
                warning.setContentText("Date is not complete");
                break;
            }
            case 9: {
                warning.setContentText("Start and End time can not be at same time");
                break;
            }
            case 10: {
                warning.setContentText("Start Time is not selected");
                break;
            }
            case 11: {
                warning.setContentText("End Time is not selected");
                break;
            }
            case 12: {
                warning.setContentText("End Time can not be before Start Time");
                break;
            }
            case 13: {
                warning.setContentText("Appointment overlaps an already existing appointment - Please adjust timeslot");
                break;
            }
            case 14: {
                warning.setContentText("Appointment is outside of business hours (8am to 10pm EST) - Please adjust timeslot");
                break;
            }
            case 15: {
                warning.setContentText("Appointment Change Not Allowed - Appointment overlaps another");
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
     * No Appointments Handler
     */
    public static void noAppointment() {
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setTitle("Appointment Error");
        warning.setHeaderText("No Appointment Selected");
        warning.setContentText("No Appointment was Selected");
        warning.showAndWait();
    }

    /**
     * Verify Appointment Delete
     * @param appointmentId, type
     * @return boolean
     */
    public static boolean verifyDelete(Integer appointmentId, String type)
    {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Delete Appointment");
        warning.setHeaderText("Confirm Delete of Appointment with ID: " + appointmentId + ", Type: " + type);
        warning.setContentText("Click OK to Confirm");
        Optional<ButtonType> answer = warning.showAndWait();
        return answer.get() == ButtonType.OK;
    }
}
