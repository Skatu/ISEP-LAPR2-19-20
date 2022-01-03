package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.ipp.isep.dei.lapr2.pot.controller.ApplicationPOT;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagerMenuUI implements Initializable {


    @FXML
    private AnchorPane managerPane;

    @FXML
    void btnLoadTransactionsAction(ActionEvent event) {
        try {
            managerPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/LoadTransactionsScene.fxml" )) );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
     void btnViewOrganizationStats(ActionEvent event) {
        try {
            managerPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationsStatsMenuScene.fxml" )) );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
    

    @FXML
    public void btnDefineDateAction(ActionEvent actionEvent) {
        Stage defineDateStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DefineDateOfPaymentsScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            defineDateStage=new Stage ();
            defineDateStage.initModality ( Modality.APPLICATION_MODAL );
            defineDateStage.setTitle ( "Define Date of Payments" );
            defineDateStage.setResizable ( false );
            defineDateStage.setScene ( scene );
            defineDateStage.show ();

        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    public void btnLogoutAction(ActionEvent actionEvent) {
        try {
            Alert confirm=alert( Alert.AlertType.CONFIRMATION,"Logout","Are you sure you want to log out?" );
            ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.OK )).setText ( "Yes" );
            ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.CANCEL )).setText ( "No" );
            Optional<ButtonType> result=confirm.showAndWait ();
            if(result.orElse ( null )== ( ButtonType.OK ) ){
                ApplicationPOT.getInstance ().doLogout ();
                managerPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/MainMenuScene.fxml" )) );
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private Alert alert(Alert.AlertType type, String header, String content){
        Alert alert=new Alert ( type );
        alert.setTitle ( "T4J" );
        alert.setHeaderText ( header );
        alert.setContentText ( content );
        return alert;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
