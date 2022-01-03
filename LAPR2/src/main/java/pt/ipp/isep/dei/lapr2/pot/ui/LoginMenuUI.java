package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pt.ipp.isep.dei.lapr2.pot.controller.AuthenticationController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenuUI implements Initializable {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblSuccess;

    private AuthenticationController authController;

    private boolean success;

    public void btnLoginAction(ActionEvent actionEvent) {
        String email=txtEmail.getText ();
        String pwd=txtPassword.getText ();
        success=authController.doLogin ( email, pwd );
        //setLblSuccess ( success );
        if(success) {
            String function=authController.getUserFunction ().getFunction ();
            redirectToUI ( function );
        }
        else{
            lblSuccess.setText ("Wrong username and/or password.");
        }
    }


    public void btnBackAction(ActionEvent actionEvent) {
        try {
            loginPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/MainMenuScene.fxml" )) );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void redirectToUI(String function)
    {
        if (function == null)
        {
            lblSuccess.setText ("Wrong username and/or password.");
            return;
        }
        if (function.isEmpty())
        {
            lblSuccess.setText ("Wrong username and/or password.");
            return;
        }

        if (function.equals ("ADMINISTRATOR"))
        {
            try {
                loginPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/AdminMenuScene.fxml" )) );
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        if (function.equals ("MANAGER_ORGANIZATION"))
        {
            try {
                loginPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/ManagerMenuScene.fxml" )) );
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        if (function.equals ("COLLABORATOR_ORGANIZATION"))
        {
            try {
                loginPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/CollaboratorMenuScene.fxml" )) );
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

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
        authController=new AuthenticationController ();
        success=false;
    }
}
