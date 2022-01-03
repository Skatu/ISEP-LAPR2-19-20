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
import pt.ipp.isep.dei.lapr2.pot.controller.ShowFreelancerPerformanceStatisticsController;
import pt.ipp.isep.dei.lapr2.pot.model.Freelancer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PerformanceStatisticsOptionUI implements Initializable {
    private ShowFreelancerPerformanceStatisticsController controller;


    @FXML
    private ComboBox<String> cmbFreelancer;

    @FXML
    private Button btnAllFreelancers;

    @FXML
    private AnchorPane optionsPane;

    @FXML
    private Button btnSelectFreelancer;

    @FXML
    private ListView<String> lstViewFreelancers;

    @FXML
    void btnAllFreelancersOnAction(ActionEvent event) {
        goToAllFreelancersPerformance();
    }

    @FXML
    void btnSelectFreelancerOnAction(ActionEvent actionEvent) {
        if (cmbFreelancer.getValue() != null && !cmbFreelancer.getItems().isEmpty()) {
            ShowFreelancerPerformanceStatisticsController.setFreelancerID(cmbFreelancer.getValue());
            goToFreelancerPerformance();
        }else{
            createAlert(Alert.AlertType.ERROR, "Unselected Freelancer", "You must select a freelancer.").showAndWait();
        }
    }

    private void goToAllFreelancersPerformance(){
        try{
            optionsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatPaymentChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private void goToFreelancerPerformance(){
        try{
            optionsPane.getChildren().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatFreelPayChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnGoBackToMenuOnAction(ActionEvent actionEvent) {
        try{
            optionsPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/AdminMenuScene.fxml" )) );
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
        List<String> list= controller.performanceStatisticsDataToString();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing controller
        controller = new ShowFreelancerPerformanceStatisticsController();

        //populating comboBox
        cmbFreelancer.setItems(getFreelancersID());

        //populating listView
        lstViewFreelancers.setItems(getStatisticsData());

    }
}

