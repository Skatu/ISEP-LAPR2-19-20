package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.ipp.isep.dei.lapr2.pot.controller.ApplicationPOT;


public class MainApp extends Application
{
     public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource( "/fxml/MainMenuScene.fxml" ));

        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Payments Management");
        stage.setScene(scene);
        stage.setMaxWidth ( 1000 );
        stage.setMaxHeight (662 );
        stage.setOnCloseRequest ( new EventHandler<WindowEvent> () {
            @Override
            public void handle(WindowEvent event) {
                Alert alert= new Alert ( Alert.AlertType.CONFIRMATION );

                alert.setTitle ( "T4J" );
                alert.setHeaderText ( "Confirm action" );
                alert.setContentText ( "Do you wish to close the application?" );

                ((Button) alert.getDialogPane ().lookupButton ( ButtonType.OK )).setText ( "Yes" );
                ((Button)alert.getDialogPane ().lookupButton ( ButtonType.CANCEL )).setText ( "No" );

                if(alert.showAndWait ().get ()==ButtonType.CANCEL){
                    event.consume ();
                }
                else{
                    ApplicationPOT.getInstance ().getPlatform ().serializeData ();
                }
            }
        });
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}