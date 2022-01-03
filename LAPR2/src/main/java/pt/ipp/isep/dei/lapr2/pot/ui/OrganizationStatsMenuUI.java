/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import pt.ipp.isep.dei.lapr2.pot.controller.ShowOrganizationStatsController;
import pt.ipp.isep.dei.lapr2.pot.model.Freelancer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class OrganizationStatsMenuUI implements Initializable {
    
    ShowOrganizationStatsController controller;

    @FXML
    private AnchorPane optionsPane;
    @FXML
    private ListView<String> lstViewFreelancers;
    @FXML
    private Button btnGoBackToMenu;
    @FXML
    private ComboBox<String> cmbFreelancer;
    @FXML
    private Button btnSelectFreelancer;
    @FXML
    private Button btnAllFreelancers;

    @FXML
    void btnAllFreelancersOnAction(ActionEvent event) {
        goToAllFreelancersPerformance();
    }

    @FXML
    void btnSelectFreelancerOnAction(ActionEvent actionEvent) {
        if (cmbFreelancer.getValue() != null && !cmbFreelancer.getItems().isEmpty()) {
            ShowOrganizationStatsController.setFreelancerID(cmbFreelancer.getValue());
            goToFreelancerPerformance();
        }else{
            createAlert(Alert.AlertType.ERROR, "Unselected Freelancer", "You must select a freelancer.").showAndWait();
        }
    }

    private void goToAllFreelancersPerformance(){
        try{
            optionsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationStatsPaymentChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void goToFreelancerPerformance(){
        try{
            optionsPane.getChildren().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationStatsFreePayChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnGoBackToMenu(ActionEvent actionEvent) {
        if(controller.isCollaborator()){
            goBackToMenuCollaborator();
        }else if(controller.isManager()){
            goBackToMenuManager();
        }
    }

    private void goBackToMenuManager(){
        try{
            optionsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/ManagerMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goBackToMenuCollaborator(){
        try{
            optionsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/CollaboratorMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /*public List<String> getLstFreelancerID(){
        return controller.getFreelancersID();
    }

     */

    private ObservableList<String> getFreelancersID(){
        ObservableList<String> freelancersID = FXCollections.observableArrayList ();
        List<String> list= controller.getFreelancersID();
        freelancersID.addAll ( list );
        return freelancersID;
    }

    public List<Freelancer> getLstFreelancer(){
        return controller.getFreelancers();
    }

    private ObservableList<String> getStatisticsData(){
        ObservableList<String> data = FXCollections.observableArrayList ();
        List<String> list= controller.organizationStatsDataToString();
        data.addAll ( list );
        return data;
    }

    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );

        alert.setTitle ( "Performance Statistics" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }

    
    public void initialize(URL location, ResourceBundle resources) {
        //initializing controller
        controller = new ShowOrganizationStatsController();

        //populating comboBox
        cmbFreelancer.setItems(getFreelancersID());

        //populating listView
        lstViewFreelancers.setItems(getStatisticsData());
    }

    

}
