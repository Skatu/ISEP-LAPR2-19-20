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
import pt.ipp.isep.dei.lapr2.pot.controller.RegisterPaymentTransactionController;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RegisterTransactionUI implements Initializable {

    RegisterPaymentTransactionController controller;

    @FXML
    private AnchorPane registerTransactionPane;

    @FXML
    private TextField txtTransactionID;

    @FXML
    private TextField txtTaskAssignedDuration;

    @FXML
    private TextField txtFreelancerExpertise;

    @FXML
    private TextField txtFreelancerEmail;

    @FXML
    private TextField txtExecutionFinishDate;

    @FXML
    private TextField txtFreelancerIBAN;

    @FXML
    private TextField txtTaskDescription;

    @FXML
    private TextField txtFreelancerName;

    @FXML
    private TextField txtTaskID;

    @FXML
    private TextField txtFreelancerID;

    @FXML
    private TextField txtExecutionDelay;

    @FXML
    private TextField txtTaskCostPerHour;

    @FXML
    private TextField txtFreelancerAddress;

    @FXML
    private TextField txtTaskCategory;

    @FXML
    private TextField txtExecutionBriefDescription;

    @FXML
    private TextField txtFreelancerNIF;

    @FXML
    private TextField txtFreelancerCountry;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Transaction> tableTransaction;

    @FXML
    private TableColumn<Transaction, String> columnTransID;

    @FXML
    private TableColumn<Transaction, Task> columnTask;

    @FXML
    private TableColumn<Transaction, Execution> columnExecution;

    @FXML
    private TableColumn<Transaction, Freelancer> columnFreelancer;

    @FXML
    private ComboBox cmbExpertise;

    @FXML
    private DatePicker datePicker;


    @FXML
    public void btnRegisterOnAction(ActionEvent actionEvent) {
        if(isAnyFieldEmpty ()){
            createAlert ( Alert.AlertType.WARNING,"Empty fields","No field can be empty" ).showAndWait ();
        }
        else if(existsTransactionByID()){
            createAlert ( Alert.AlertType.WARNING,"Existing Transaction","The transaction entered is already registered in the system" ).showAndWait ();
        }
        else if (!existsFreelancerByNIF()){
            createAlert ( Alert.AlertType.WARNING,"Unregistered Freelancer","Register the introduced Freelancer" ).showAndWait ();
        }
        else if (!existsTaskByID()){
            createAlert ( Alert.AlertType.WARNING,"Non-Existent Task","Create the introduced Task" ).showAndWait ();
        }
        else{
            if(!isCreationOfTransactionSuccessful ()){
                createAlert ( Alert.AlertType.ERROR,"Invalid fields","Invalid input. " +
                        "Please check if all the information is correct.").showAndWait ();
            }else{
                confirmRegister ();
                tableTransaction.getItems().add(controller.getTransaction());
                tableTransaction.scrollTo(controller.getTransaction());
                showPayment();
            }
        }
    }

    @FXML
    public void btnCancelOnAction(ActionEvent actionEvent) {
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Cancel","Are you sure you want to cancel?" );
        ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.OK )).setText ( "Yes" );
        ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.CANCEL )).setText ( "No" );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            goBackToCollaboratorMenu ();
        }
    }

    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        txtTransactionID.clear();
        txtTaskID.clear();
        txtTaskDescription.clear();
        txtTaskAssignedDuration.clear();
        txtTaskCostPerHour.clear();
        txtTaskCategory.clear();
        txtExecutionBriefDescription.clear();
        datePicker.getEditor().clear();
        txtExecutionDelay.clear();
        txtFreelancerID.clear();
        txtFreelancerName.clear();
        txtFreelancerEmail.clear();
        txtFreelancerNIF.clear();
        txtFreelancerIBAN.clear();
        txtFreelancerAddress.clear();
        cmbExpertise.getSelectionModel().clearSelection();
    }

    private boolean isCreationOfTransactionSuccessful() {

        return controller.newTransaction(txtTransactionID.getText().trim(), txtTaskID.getText().trim(), txtTaskDescription.getText().trim(),
                Double.parseDouble(txtTaskAssignedDuration.getText().trim()), Double.parseDouble(txtTaskCostPerHour.getText().trim()),
                txtTaskCategory.getText().trim(), (datePicker.getValue().toString()),
                Double.parseDouble(txtExecutionDelay.getText().trim()),txtExecutionBriefDescription.getText().trim(),
                txtFreelancerID.getText().trim(), txtFreelancerName.getText().trim(), cmbExpertise.getValue().toString(),
                txtFreelancerEmail.getText().trim(), txtFreelancerNIF.getText().trim(), txtFreelancerIBAN.getText().trim(),
                txtFreelancerAddress.getText().trim(), txtFreelancerCountry.getText().trim());
    }

    private void confirmRegister(){
        String dataTransaction=getDataTransaction();
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Create Transaction?", dataTransaction );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            createTransactionAndShowConfirmation ();
        }
        else{
            confirm.close ();
        }
    }

    private String getDataTransaction() {
        return "Transaction: " + txtTransactionID.getText() +
                "\nTask: " +
                "\n\t ID: " + txtTaskID.getText()  +
                "\n\t Description: " + txtTaskDescription.getText () +
                "\n\t Duration: " + txtTaskAssignedDuration.getText()  +
                "\n\t Cost per Hour: " + txtTaskCostPerHour.getText () +
                "\n\t Category: " + txtTaskCategory.getText () +
                "\nExecution: " +
                "\n\t Brief Description: " + txtExecutionBriefDescription.getText () +
                "\n\t Finish Date: " + datePicker.getValue().toString() +
                "\n\t Delay: " + txtExecutionDelay.getText () +
                "\nFreelancer: " +
                "\n\t ID: " + txtFreelancerID.getText () +
                "\n\t Name: " + txtFreelancerName.getText () +
                "\n\t E-mail: " + txtFreelancerEmail.getText () +
                "\n\t NIF: " + txtFreelancerNIF.getText () +
                "\n\t IBAN: " + txtFreelancerIBAN.getText () +
                "\n\t Address: " + txtFreelancerAddress.getText () +
                "\n\t Expertise: " + cmbExpertise.getValue().toString();
    }

    private void createTransactionAndShowConfirmation(){
        controller.registerTransaction();
        createAlert ( Alert.AlertType.INFORMATION,"Transaction created",
                "The Transaction was added to the system.").showAndWait ();
    }

    private void goBackToCollaboratorMenu(){
        try{
            registerTransactionPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/CollaboratorMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );

        alert.setTitle ( "New Transaction" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }

    private boolean isAnyFieldEmpty(){
        return txtTransactionID.getText().trim().isEmpty() || txtTaskID.getText().trim().isEmpty() ||
                txtTaskDescription.getText().trim().isEmpty() || txtTaskAssignedDuration.getText().trim().isEmpty()
                || txtTaskCostPerHour.getText().trim().isEmpty() || txtTaskCategory.getText().trim().isEmpty() ||
                txtExecutionBriefDescription.getText().trim().isEmpty() || datePicker.getValue().toString().trim().isEmpty()
                || txtExecutionDelay.getText().trim().isEmpty() || txtFreelancerID.getText().trim().isEmpty() ||
                txtFreelancerName.getText().trim().isEmpty() || txtFreelancerEmail.getText().trim().isEmpty()
                || txtFreelancerNIF.getText().trim().isEmpty() || txtFreelancerIBAN.getText().trim().isEmpty() ||
                txtFreelancerAddress.getText().trim().isEmpty() || cmbExpertise.getValue().toString().trim().isEmpty();
    }

    private boolean existsFreelancerByNIF(){
        return controller.existsFreelancerByNIF(txtFreelancerNIF.getText().trim());
    }

    private boolean existsTaskByID(){
        return controller.existsTaskById(txtTaskID.getText().trim());
    }

    private boolean existsTransactionByID(){
        return controller.existsTransactionByID(txtTransactionID.getText().trim());
    }

    private ObservableList<Transaction> getTransactions(){
        ObservableList<Transaction> transactions= FXCollections.observableArrayList ();
        List<Transaction> list=getListTransactions();
        transactions.addAll ( list );
        return transactions;
    }

    private List<Transaction> getListTransactions(){
        return controller.getListTransactions().getM_lstTransactions();
    }

    private void showPayment(){
        createAlert(Alert.AlertType.INFORMATION, "Payment for entered Freelancer:", getPayment()).showAndWait();
    }

    private String getPayment(){
        return String.format("Freelancer %s: \n\tAmount to Pay: %.2f€", txtFreelancerName.getText(), controller.computeAmount());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new RegisterPaymentTransactionController();
        columnTransID.setCellValueFactory ( new PropertyValueFactory<>( "transactionID" ) );
        columnTask.setCellValueFactory ( new PropertyValueFactory<> ( "task" ) );
        columnExecution.setCellValueFactory ( new PropertyValueFactory<> ( "execution" ) );
        columnFreelancer.setCellValueFactory ( new PropertyValueFactory<> ( "freelancer" ) );
        tableTransaction.setItems(getTransactions());

        cmbExpertise.setItems(FXCollections.observableList(Arrays.asList(EnumFreelancerExpertise.values())));

        Locale myLocale=Locale.getDefault (Locale.Category.FORMAT);
        datePicker.setOnShowing ( e-> Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH) );
        datePicker.setOnShown ( e->Locale.setDefault ( Locale.Category.FORMAT, myLocale) );
    }

}
