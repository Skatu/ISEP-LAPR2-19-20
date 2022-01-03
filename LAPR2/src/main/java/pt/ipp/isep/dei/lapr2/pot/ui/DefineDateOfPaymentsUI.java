package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.lapr2.pot.controller.DefineDateOfPaymentsController;
import pt.ipp.isep.dei.lapr2.pot.model.Parse;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class DefineDateOfPaymentsUI implements Initializable {

    private DefineDateOfPaymentsController m_oController;

    @FXML
    private AnchorPane defineDatePane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtHour;

    @FXML
    private TextField txtMinute;

    @FXML
    private void btnCancelAction(ActionEvent event) {
        Stage stage=(Stage)defineDatePane.getScene ().getWindow ();
        stage.close ();
    }

    @FXML
    private void btnConfirmAction(ActionEvent event) {
        if(!areValidEntries ()){
            createAlert ( Alert.AlertType.ERROR,"Date","Please insert a valid date and time" ).showAndWait ();
        }
        else{
            String date= datePicker.getValue ().toString ();

            String[] strDate=date.split ( "-" );
            int y=Integer.parseInt ( strDate[0]);
            int m=Integer.parseInt ( strDate[1]);
            int d=Integer.parseInt ( strDate[2]);

            int hour=Integer.parseInt ( txtHour.getText ());
            int minute=Integer.parseInt ( txtMinute.getText () );
            Date NewDate = new Date(y,m,d,hour,minute);

            this.m_oController.newPaymentTimer(NewDate);
            if(hour<10) txtHour.setText ( "0"+hour );
            if(minute<10) txtMinute.setText ( "0"+minute );
            createAlert ( Alert.AlertType.INFORMATION,"Date","Date of Payment set to "+datePicker.getValue ().toString ()+" at "+txtHour.getText ()+":"+txtMinute.getText () ).showAndWait ();
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
        m_oController=new DefineDateOfPaymentsController ();
        Locale myLocale=Locale.getDefault (Locale.Category.FORMAT);
        datePicker.setValue ( LocalDate.now() );
        datePicker.setOnShowing ( e-> Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH) );
        datePicker.setOnShown ( e->Locale.setDefault ( Locale.Category.FORMAT, myLocale) );
    }
    private boolean areValidEntries(){
        if(!Parse.tryParseInt ( txtHour.getText ().trim () )){
            return false;
        }
        if(!Parse.tryParseInt ( txtMinute.getText ().trim () )){
            return false;
        }
        int compareDates= datePicker.getValue ().compareTo ( LocalDate.now ());
        if(compareDates<0){
            return false;
        }

        int hour=Integer.parseInt ( txtHour.getText ().trim () );
        if(hour<0 || hour >23){
            return false;
        }
        int minute=Integer.parseInt ( txtMinute.getText ().trim () );
        if( minute < 0 || minute > 59){
            return false;
        }
        if(compareDates==0) {
            int currentHour = LocalDateTime.now ().getHour ();
            int currentMinute=LocalDateTime.now ().getMinute ();
            if(hour>currentHour) return true;
            if(hour==currentHour) return currentMinute < minute;
            return false;
        }
        return true;
    }

    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );
        alert.setTitle ( "Load Transactions" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }
}
