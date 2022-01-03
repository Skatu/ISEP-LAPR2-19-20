/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pt.ipp.isep.dei.lapr2.pot.controller.ShowOrganizationStatsController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;


public class OrganizationStatsFreePayChartUI implements Initializable {

    @FXML
    private AnchorPane paymentPane;
    @FXML
    private Pane tablesPane;
    @FXML
    private BarChart<String, Number> barChartPayment;
    @FXML
    private NumberAxis numberPayments;
    @FXML
    private CategoryAxis intervalsPayment;
    @FXML
    private TextField txtMean;
    @FXML
    private TextField txtDeviation;
    @FXML
    private Label lblFreelancerID;
    @FXML
    private TextField txtFreelancerID;
    @FXML
    private Button btnReturnToMenu;
    @FXML
    private Button btnDelay;
    
    ShowOrganizationStatsController controller;
    String freelancerID;

    @FXML
    void btnReturnToMenuOnAction(ActionEvent event) {
        try{
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationsStatsMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    void btnDelayOnAction(ActionEvent event) {
        try{
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationStatsFreeDelChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }
    //Interval Definition
    
    private String determineFirstInterval() {
        double firstInterval =controller.calculateMeanPayment().get(freelancerID) -
                controller.calculateDeviationPayment().get(freelancerID);
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("] -%s, %.2f]", infinitySymbol, firstInterval);
    }

    private String determineSecondInterval() {
        double firstInterval = controller.calculateMeanPayment().get(freelancerID)
                - controller.calculateDeviationPayment().get(freelancerID);
        double secondInterval = controller.calculateMeanPayment().get(freelancerID)
                + controller.calculateDeviationPayment().get(freelancerID);
        return String.format("]%.2f, %.2f[", firstInterval, secondInterval);
    }

    private String determineThirdInterval() {
        double secondInterval = controller.calculateMeanPayment().get(freelancerID)
                + controller.calculateDeviationPayment().get(freelancerID);
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("[%.2f, +%s[", secondInterval, infinitySymbol);
    }
   

    //NumberAxis Definition
   

    private double getLowerLimitValue(){
        return controller.calculateMeanPayment().get(freelancerID) -
                controller.calculateDeviationPayment().get(freelancerID);
    }

    private double getHigherLimitValue(){
        return controller.calculateMeanPayment().get(freelancerID) +
                controller.calculateDeviationPayment().get(freelancerID);
    }

    private double valuesFirstInterval() {
        int counter = 0;
        if (controller.determinePaymentOrganization().get(freelancerID).size() == 1){
            return 0;
        }
        for (double payment : controller.determinePaymentOrganization().get(freelancerID)){
            if (payment <= getLowerLimitValue()){
                counter++;
            }
        }
        return counter;
    }

    private double valuesSecondInterval() {
        int counter = 0;
        for (double payment : controller.determinePaymentOrganization().get(freelancerID)){
            if (payment > getLowerLimitValue() && payment < getHigherLimitValue()){
                counter++;
            }
        }
        return counter;
    }

    private double valuesThirdInterval() {
        int counter = 0;
        for (double payment : controller.determinePaymentOrganization().get(freelancerID)){
            if (payment >= getHigherLimitValue()){
                counter++;
            }
        }
        return counter;
    }
   

    
    public void initialize(URL location, ResourceBundle resources) {
        controller = new ShowOrganizationStatsController();
        freelancerID = ShowOrganizationStatsController.getFreelancerID();
        txtFreelancerID.setText(freelancerID);

        String mean = String.format("%.2f", controller.calculateMeanPayment().get(freelancerID));
        txtMean.setText(mean);

        String deviation = String.format("%.2f", controller.calculateDeviationPayment().get(freelancerID));
        txtDeviation.setText(deviation);

        //Defining Mean Bar Chart values
        XYChart.Series<String, Number> seriesPayment = new XYChart.Series<>();
        seriesPayment.getData().add(new XYChart.Data<>(determineFirstInterval(), valuesFirstInterval()));
        seriesPayment.getData().add(new XYChart.Data<>(determineSecondInterval(), valuesSecondInterval()));
        seriesPayment.getData().add(new XYChart.Data<>(determineThirdInterval(), valuesThirdInterval()));

        //Adding series to Mean Bar Chart
        intervalsPayment.setCategories(FXCollections.observableArrayList(determineFirstInterval(), determineSecondInterval(), determineThirdInterval()));
        barChartPayment.getData().add(seriesPayment);

    }
    
}
