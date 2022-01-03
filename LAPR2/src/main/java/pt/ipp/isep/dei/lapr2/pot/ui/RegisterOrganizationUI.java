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
import pt.ipp.isep.dei.lapr2.pot.controller.RegisterOrganizationController;
import pt.ipp.isep.dei.lapr2.pot.model.Organization;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is responsible for interacting with the user when creating an organization.
 *  It handles with the input data, the verification of this data(if any field is empty),
 *  alerts the user in case any of the fields are invalid(invalid format or repeated NIF or e-mails),
 *  and informs the user if the registration is successful.
 *  It also shows all the organizations already registered in the system.
 *
 * @author Rafael Moreira - 1181055
 */

public class RegisterOrganizationUI implements Initializable {
    /**
     * Controller variable for the UI.
     */
    private RegisterOrganizationController registerOrganizationController;

    /**
     * {@link AnchorPane} of the Scene.
     */
    @FXML
    private AnchorPane registerOrgPane;

    /**
     * Name of the organization's {@link TextField}.
     */
    @FXML
    private TextField txtOrgName;

    /**
     * NIF of the organization's {@link TextField}.
     */
    @FXML
    private TextField txtOrgNIF;

    /**
     * Name of the manager of the organization's {@link TextField}.
     */
    @FXML
    private TextField txtManagerName;

    /**
     * E-mail of the manager of the organization's {@link TextField}.
     */
    @FXML
    private TextField txtManagerEmail;

    /**
     * Name of the collaborator of the organization's {@link TextField}.
     */
    @FXML
    private TextField txtCollabName;

    /**
     * E-mail of the collaborator of the organization's {@link TextField}.
     */
    @FXML
    private TextField txtCollabEmail;

    /**
     * {@link TableView} which holds all the existing organizations.
     */
    @FXML
    private TableView<Organization> tableOrgs;

    /**
     * {@link TableColumn} for the names of the organizations.
     */
    @FXML
    private TableColumn<?, ?> tbOrganizationName;

    /**
     * {@link TableColumn} for the NIFs of the organizations.
     */
    @FXML
    private TableColumn<?, ?> tbOrganizationNIF;

    /**
     * {@link TableColumn} for the managers of the organizations.
     */
    @FXML
    private TableColumn<?, ?> tbManager;

    /**
     * {@link TableColumn} for the collaborators of the organizations.
     */
    @FXML
    private TableColumn<?, ?> tbCollaborator;

    /**
     * Triggers the cancellation of the registration of the organization.
     * It also functions as a way to return to the administrator menu.
     * Calls method <code>goBackToAdminMenu</code> to return to the administrator menu.
     * @param event Button is clicked.
     */
    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Back","Are you sure you want to go back?" );
        ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.OK )).setText ( "Yes" );
        ((Button)confirm.getDialogPane ().lookupButton ( ButtonType.CANCEL )).setText ( "No" );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            goBackToAdminMenu ();
        }
    }

    /**
     * Calls method <code>clearFields</code> to reset all the fields in the menu.
     * @param event Button is clicked.
     */
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields ();
    }

    /**
     * Initiates the creation of the organization.
     * The method calls the <code>anyFieldIsEmpty</code> and
     * <code>creationOfOrganizationIsSuccessful</code> to ensure
     * the validity of the data.
     * Finally, calls the <code>confirmCreation</code>
     * method to confirm the creation of the organization.
     * @param event Button is clicked.
     */
    @FXML
    void btnConfirmOnAction(ActionEvent event) {

        if(anyFieldIsEmpty ()){
            createAlert ( Alert.AlertType.WARNING,"Empty fields","No field can be empty" ).showAndWait ();
        }
        else{
            if(!creationOfOrganizationIsSuccessful ()){
                createAlert ( Alert.AlertType.ERROR,"Invalid fields","Invalid input. " +
                        "Please check if all the information is correct or if it already exists in the system.").showAndWait ();
            }else{
                confirmCreation ();
            }
        }
    }

    /**
     * Shows an alert box asking for confirmation for the creation of the organization.
     * If the user confirms, the organization is created and the {@link TableView}
     * is updated with the new organization.
     */
    private void confirmCreation(){
        String data=getData();
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Create Organization?",data );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            createOrganizationAndShowConfirmation ();
            clearFields ();
            tableOrgs.setItems ( getOrganizations () );
        }
        else{
            confirm.close ();
        }
    }

    /**
     * Registers a new organization and shows an alert box informing of the success of the operation and of
     * the sending of e-mails to the newly created users.
     */
    private void createOrganizationAndShowConfirmation(){
        if(registerOrganizationController.registerOrganization ()){
            createAlert ( Alert.AlertType.INFORMATION,"Organization created",
                    "An E-mail has been sent to the respective manager and collaborator " +
                            "informing the creation of the accounts." ).showAndWait ();
        } else{
            createAlert ( Alert.AlertType.ERROR,"Error",
                    "An error has occurred in the creation of the accounts." ).showAndWait ();
        }
        //registerOrganizationController.exportOrganizations ();

    }

    /**
     * Tries to create a new organization with the input data.
     * @return <code>true</code> if the organization is created. <code>false</code> otherwise.
     */
    private boolean creationOfOrganizationIsSuccessful(){
        return registerOrganizationController.newOrganization ( txtOrgName.getText ().trim (), txtOrgNIF.getText ().trim (),
                txtManagerName.getText ().trim (),txtManagerEmail.getText ().trim (),
                txtCollabName.getText ().trim (),txtCollabEmail.getText ().trim ());
    }

    /**
     * Loads the fxml file associated the Administrator menu.
     */
    private void goBackToAdminMenu(){
        try{
            registerOrgPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/AdminMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    /**
     * Verifies if any of the fields are empty before initiating the registration process.
     * @return <code>true</code> if any of the fields are empty. <code>false</code> otherwise.
     */
    private boolean anyFieldIsEmpty(){
        return txtOrgName.getText ().trim ().isEmpty () || txtOrgNIF.getText ().trim ().isEmpty () ||
                txtManagerName.getText ().trim ().isEmpty () || txtManagerEmail.getText ().trim ().isEmpty () ||
                txtCollabName.getText ().trim ().isEmpty () || txtCollabEmail.getText ().trim ().isEmpty ();
    }

    /**
     * Creates a {@link String} representation of the input data so it can be displayed
     * in an alert box for the user to confirm. It contains the name and NIF of the
     * organization and name and e-mails of manager and collaborator.
     * @return Textual representation of the data to be registered.
     */
    private String getData(){
        return "Organization: " +
                "\n\t Name: " + txtOrgName.getText () +
                "\n\t NIF: " + txtOrgNIF.getText () +
                "\nManager: " +
                "\n\t Name: " + txtManagerName.getText () +
                "\n\t E-mail: " + txtManagerEmail.getText () +
                "\nCollaborator: " +
                "\n\t Name: " + txtCollabName.getText () +
                "\n\t E-mail: " + txtCollabEmail.getText ();
    }

    /**
     * Creates an alert box to inform the user of any unexpected event that
     * occurred.
     * @param type The type of alert. It accepts any variable of the Enum {@link javafx.scene.control.Alert.AlertType}.
     * @param header The text for the header of the alert box.
     * @param message The message to be displayed in the body of the alert box.
     * @return Object of type {@link Alert}.
     */
    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );

        alert.setTitle ( "New Organization" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }

    /**
     * Creates an Observable list with all the organizations so they can
     * be displayed in the TableView.
     * @return {@link ObservableList} of type {@link Organization}.
     */
    private ObservableList<Organization> getOrganizations(){
        ObservableList<Organization> list=FXCollections.observableArrayList ();
        List<Organization> organizations=registerOrganizationController.getListOrganizations ();
        list.addAll ( organizations );
        return list;
    }

    /**
     * Resets all the values attributed to the instances variables.
     */
    private void clearFields(){
        txtOrgName.clear ();
        txtOrgNIF.clear ();
        txtManagerName.clear ();
        txtManagerEmail.clear ();
        txtCollabName.clear ();
        txtCollabEmail.clear ();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * Initializes the {@link RegisterOrganizationController} class and the {@link TableView} class,
     * along with its columns.
     * Writes the {@link TableView} with all the current registered organizations.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerOrganizationController=new RegisterOrganizationController ();
        tbOrganizationName.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
        tbOrganizationNIF.setCellValueFactory ( new PropertyValueFactory<> ( "nif" ) );
        tbManager.setCellValueFactory ( new PropertyValueFactory<> ( "manager" ) );
        tbCollaborator.setCellValueFactory ( new PropertyValueFactory<> ( "collaborator" ) );
        tableOrgs.setItems ( getOrganizations () );
    }
}
