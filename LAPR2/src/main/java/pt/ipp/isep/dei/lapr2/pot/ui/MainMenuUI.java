package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * author Rafael Moreira
 */

public class MainMenuUI implements Initializable {

    @FXML
    private AnchorPane rootPane;

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

    @FXML
    public void btnLoginAction(ActionEvent actionEvent) {
        try{
            rootPane.getChildren ().setAll ( (Node) FXMLLoader.load ( getClass ().getResource ( "/fxml/LoginMenuScene.fxml" ) ) );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    void btnAboutUsAction(ActionEvent event) {
        Alert alert=new Alert ( Alert.AlertType.INFORMATION);
        alert.setTitle ( "LAPR2 - 2019/2020" );
        alert.setHeaderText ( "Team PTRRR" );
        alert.setContentText ( "Made by:\n" +
                                "\t1181055 - Rafael Moreira\n"+
                                "\t1181609 - Tiago Alves\n"+
                                "\t1190975 - Petra Lopes\n"+
                                "\t1191008 - Rodrigo Rodrigues\n"+
                                "\t1191017 - Rogério Sousa");
        alert.showAndWait ();
    }

    @FXML
    public void btnExitAction(ActionEvent actionEvent) {
        Window window=((Node)actionEvent.getSource ()).getScene ().getWindow ();
        window.fireEvent ( new WindowEvent ( window, WindowEvent.WINDOW_CLOSE_REQUEST ) );
        //((Node)actionEvent.getSource ()).getScene ().getWindow ().hide ();
    }
}
