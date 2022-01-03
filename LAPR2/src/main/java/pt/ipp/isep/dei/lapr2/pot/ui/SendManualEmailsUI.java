package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pt.ipp.isep.dei.lapr2.pot.controller.SendManualEmailsController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SendManualEmailsUI implements Initializable {

    /**
     * Label with the message
     */
    @FXML
    public Label txtMessage;

    /**
     * The pane
     */
    @FXML
    public AnchorPane manualEmailsPane;

    /**
     * The button
     */
    @FXML
    public Button btnButton;

    /**
     * The controller
     */
    private SendManualEmailsController m_controller;

    /**
     *
     * @param location The location used to resolve relative paths for the root object, or
     *                 <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_controller=new SendManualEmailsController();
        m_controller.sendWarningEmails();
        txtMessage.setText("Emails sent with success!");
        btnButton.setText("Back");
    }

    /**
     * The button on action
     * @param actionEvent button clicked
     */
    public void btnCancel(ActionEvent actionEvent) {
        if (btnButton.getText().equals("Cancel")) {
            Alert confirm = createAlert(Alert.AlertType.CONFIRMATION, "Cancel", "Are you sure you want to cancel?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.orElse(null) == ButtonType.OK) {
                goBackToAdminMenu();
            }
        }
        if (btnButton.getText().equals("Back")){
            goBackToAdminMenu();
        }
    }

    /**
     * Creates an alert box to inform the user of any unexpected event that
     * occurred.
     * @param type The type of alert.
     * @param header The text for the header of the alert box.
     * @param message The message to be displayed in the body of the alert box.
     * @return the alert
     */
    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );

        alert.setTitle ( "Send Manual Emails" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }

    /**
     * Goes back to the Admin Menu
     */
    private void goBackToAdminMenu(){
        try{
            manualEmailsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/AdminMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
