package pt.ipp.isep.dei.lapr2.pot.ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pt.ipp.isep.dei.lapr2.pot.controller.RegistryFreelancerController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.lapr2.pot.model.EnumFreelancerExpertise;
import pt.ipp.isep.dei.lapr2.pot.model.Freelancer;

/**
 * FXML Controller class
 *
 * @author Tiago Alves
 */
public class RegistryFreelancerUI implements Initializable {

    private RegistryFreelancerController registryFreelancerController;

    @FXML
    private AnchorPane tablePane;

 
    @FXML
    private TextField txtFreelancerName;
    @FXML
    private TextField txtFreelancerEmail;
    private TextField txtFreelancerExpertise;
    @FXML
    private TextField txtFreelancerIBAN;
    @FXML
    private TextField txtFreelancerNIF;
    @FXML
    private TextField txtFreelancerAddress;
    @FXML
    private TextField txtFreelancerCountry;
    @FXML
    private Pane registerFreePane;
    @FXML
    private AnchorPane registerFreelancerPane;
    @FXML
    private TableView<Freelancer> tableView;
    @FXML
    private TableColumn<Freelancer, String> columnID;
    @FXML
    private TableColumn<Freelancer, String> columnFreelancer;
    @FXML
    private TableColumn<Freelancer, String> columnEmail;
    @FXML
    private TableColumn<Freelancer, String> columnExpertise;

    private ArrayList<Freelancer> freelancerList;
    @FXML
    private ComboBox cmbExpertise;

    @FXML
    void btnRegister(ActionEvent event) {
        if (anyFieldIsEmpty()) {
            createAlert(Alert.AlertType.WARNING, "Empty fields", "No field can be empty").showAndWait();
        } else {
            if (!creationOfFreelancerIsSuccessful()) {
                createAlert(Alert.AlertType.ERROR, "Invalid fields", "Invalid input. "
                        + "Please check if all the information is correct or if it already exists in the system.").showAndWait();
            } else {
                confirmCreation();
                tableView.setItems(getFreelancers());
            }

        }
    }

    @FXML
    void btnCancel(ActionEvent event) {
        Alert confirm = createAlert(Alert.AlertType.CONFIRMATION, "Cancel", "Are you sure you want to cancel?");
        ((Button) confirm.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            goBackToCollaboratorMenu();
        }
    }

    @FXML
    void btnClear(ActionEvent event) {

       
        txtFreelancerName.clear();
        txtFreelancerEmail.clear();
        txtFreelancerIBAN.clear();
        txtFreelancerNIF.clear();
        txtFreelancerAddress.clear();
        txtFreelancerCountry.clear();
        cmbExpertise.getSelectionModel().clearSelection();
    }

    private boolean anyFieldIsEmpty() {
        return txtFreelancerName.getText().trim().isEmpty() || txtFreelancerEmail.getText().trim().isEmpty() || 
               cmbExpertise.getValue().toString().trim().isEmpty()|| txtFreelancerIBAN.getText().trim().isEmpty() ||
               txtFreelancerNIF.getText().trim().isEmpty()|| txtFreelancerAddress.getText().trim().isEmpty() || 
               txtFreelancerCountry.getText().trim().isEmpty();

    }

    private void confirmCreation() {
        String data = getData();
        Alert confirm = createAlert(Alert.AlertType.CONFIRMATION, "Create Organization?", data);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            createFreelancerAndShowConfirmation();
        } else {
            confirm.close();
        }
    }

    private void createFreelancerAndShowConfirmation() {
        registryFreelancerController.registerFreelancer();
        createAlert(Alert.AlertType.CONFIRMATION, "Freelancer created",
                "Freelancer has been created ").showAndWait();;

    }

    private void goBackToCollaboratorMenu() {
        try {
            tablePane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/fxml/CollaboratorMenuScene.fxml")));
            registerFreePane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/fxml/CollaboratorMenuScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getData() {
        return "Freelancer: "
                + "\n\t Name: " + txtFreelancerName.getText()
                + "\n\t E-mail: " + txtFreelancerEmail.getText()
                + "\n\t Level of Expertise: " + cmbExpertise.getValue().toString()
                + "\n\t IBAN: " + txtFreelancerIBAN.getText()
                + "\n\t NIF: " + txtFreelancerNIF.getText()
                + "\n\t Address: " + txtFreelancerAddress.getText()
                + "\n\t Country: " + txtFreelancerCountry.getText();
    }

    private Alert createAlert(Alert.AlertType type, String header, String message) {
        Alert alert = new Alert(type);

        alert.setTitle("New Freelancer");
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }

    private boolean creationOfFreelancerIsSuccessful() {

        return registryFreelancerController.newFreelancer(txtFreelancerName.getText().trim(),
                cmbExpertise.getValue().toString(), txtFreelancerEmail.getText().trim(),
                txtFreelancerNIF.getText().trim(), txtFreelancerIBAN.getText().trim(),
                txtFreelancerAddress.getText().trim(), txtFreelancerCountry.getText().trim());
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location The location used to resolve relative paths for the root
     * object, or
     * <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     * <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registryFreelancerController = new RegistryFreelancerController();
        freelancerList = new ArrayList<Freelancer>();
        
        cmbExpertise.setItems(FXCollections.observableList(Arrays.asList(EnumFreelancerExpertise.values())));

        columnID.setCellValueFactory(new PropertyValueFactory<>("freelancerID"));
        columnFreelancer.setCellValueFactory(new PropertyValueFactory<>("freelancerName"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("freelancerEmail"));
        columnExpertise.setCellValueFactory(new PropertyValueFactory<>("freelancerExpertise"));
        
        tableView.setItems(getFreelancers());
           
        
        
    }

    private ObservableList<Freelancer> getFreelancers() {
        ObservableList<Freelancer> freelancers = FXCollections.observableArrayList();
        Freelancer freelancer = getFreelancer();
        freelancerList.add(freelancer);
        freelancers.addAll(freelancerList);

        return freelancers;
    }

    private Freelancer getFreelancer() {
        return registryFreelancerController.getFreelancer();
    }
}
