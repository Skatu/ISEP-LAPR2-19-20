package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import pt.ipp.isep.dei.lapr2.pot.controller.LoadTransactionsController;
import pt.ipp.isep.dei.lapr2.pot.model.Execution;
import pt.ipp.isep.dei.lapr2.pot.model.Freelancer;
import pt.ipp.isep.dei.lapr2.pot.model.Task;
import pt.ipp.isep.dei.lapr2.pot.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author Rafael Moreira - 1181055
 */
public class LoadTransactionsUI implements Initializable {

    @FXML
    private AnchorPane loadTransactionsPane;
    @FXML
    private TextField txtDirectory;
    @FXML
    private TableView<Transaction> tvTable;
    @FXML
    private TableColumn<Transaction, String> tcTransID;
    @FXML
    private TableColumn<Transaction, Task> tcTask;
    @FXML
    private TableColumn<Transaction, Execution> tcExec;
    @FXML
    private TableColumn<Transaction, Freelancer> tcFreel;
    @FXML
    private TextArea txtLog;

    private LoadTransactionsController controller;
    private int nbrLinesReadSuccessfully;
    private int nbrLinesFailedRead;

    @FXML
    private void btnBrowseAction(ActionEvent event) {
        FileChooser fileChooser= FileChooserLoadTransactionsUI.createFileChooseTransactionsFile ();
        File fileToImport=fileChooser.showOpenDialog ( txtDirectory.getScene ().getWindow () );

        if(fileToImport!=null){
            txtDirectory.setText ( fileToImport.getAbsolutePath () );
        }
    }

    @FXML
    private void btnConfirm(ActionEvent event) {
        if(!isValidFile ( txtDirectory.getText () )){
            createAlert ( Alert.AlertType.ERROR,"File","Please choose a .txt or .csv file and try again" ).showAndWait ();
        }
        if(controller.newFileReading ( txtDirectory.getText () )){
            nbrLinesReadSuccessfully=controller.getNumberSuccessfullLines ();
            nbrLinesFailedRead=controller.getNumberFailedLines ();
            String content="Total number of lines read: "+(nbrLinesReadSuccessfully+nbrLinesFailedRead)+
                    "\nNumber of successful lines : "+nbrLinesReadSuccessfully+
                    "\nNumber of failed lines: "+nbrLinesFailedRead;
            createAlert ( Alert.AlertType.INFORMATION,"File Read", content ).showAndWait ();
            tvTable.setItems ( getTransactions () );
            txtLog.setText ( controller.getLog () );
        }
    }

    @FXML
    private void btnCancel(ActionEvent event) {
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Back","Are you sure you want to go back to the manager menu?" );
        ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.OK )).setText ( "Yes" );
        ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.CANCEL )).setText ( "No" );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            goBackToManagerMenu ();
        }
    }

    private boolean isValidFile(String directory){
        return controller.isValidFile ( directory );
    }

    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );
        alert.setTitle ( "Load Transactions" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }

    private void goBackToManagerMenu(){
        try{
            loadTransactionsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/ManagerMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private ObservableList<Transaction> getTransactions(){
        ObservableList<Transaction> transactions= FXCollections.observableArrayList ();
        List<Transaction> list=getListTransactionsRead ();
        transactions.addAll ( list );
        return transactions;
    }

    private List<Transaction> getListTransactionsRead(){
        return controller.getListTransactionsRead();
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
        controller=new LoadTransactionsController ();
        controller.initiateFileLoading ();
        //setup columns
        tcTransID.setCellValueFactory ( new PropertyValueFactory<> ( "transactionID" ) );
        tcTask.setCellValueFactory ( new PropertyValueFactory<> ( "task" ) );
        tcExec.setCellValueFactory ( new PropertyValueFactory<> ( "execution" ) );
        tcFreel.setCellValueFactory ( new PropertyValueFactory<> ( "freelancer" ) );
    }
}
