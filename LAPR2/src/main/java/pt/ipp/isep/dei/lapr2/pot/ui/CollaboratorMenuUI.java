package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import pt.ipp.isep.dei.lapr2.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.lapr2.pot.controller.RegisterPaymentTransactionController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CollaboratorMenuUI implements Initializable {

    private RegisterPaymentTransactionController controller;
    @FXML
    private AnchorPane collabPane;

    @FXML
    private Button btnRegisterTransaction;

    @FXML
    public void btnRegisterTransactionOnAction(ActionEvent actionEvent) {
        try {
            collabPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/fxml/RegisterTransactionScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnRegisterTaskOnAction(ActionEvent actionEvent) {
        try {
            collabPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/fxml/RegisterTaskScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnRegisterFreelancerOnAction(ActionEvent event) {
        try {
            collabPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/fxml/RegistryFreelancersScen.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnLogout(ActionEvent event) {
        try {
            Alert confirm = alert(Alert.AlertType.CONFIRMATION, "Logout", "Are you sure you want to log out?");
            ((Button) confirm.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.orElse(null) == (ButtonType.OK)) {
                ApplicationPOT.getInstance().doLogout();
                collabPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/fxml/MainMenuScene.fxml")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Alert alert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("T4J");
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
     void btnViewOrganizationsStats(ActionEvent event) {
         try {
            collabPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationsStatsMenuScene.fxml" )) );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
     
}

