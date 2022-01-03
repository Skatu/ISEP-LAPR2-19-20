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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pt.ipp.isep.dei.lapr2.pot.controller.ShowOrganizationStatsController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;


public class OrganizationStatsPaymentChartUI implements Initializable {

    @FXML
    private AnchorPane paymentPane;
    @FXML
    private BarChart<String, Number> barChartPayment;
    @FXML
    private NumberAxis numberTasks;
    @FXML
    private CategoryAxis intervalsPayment;
    @FXML
    private ListView<String> lstViewPayments;
    @FXML
    private Button btnReturnToMenu;
    @FXML
    private Button btnDelay;

    @FXML
    private TextField txtOverallMean;

    @FXML
    private TextField txtOverallDeviation;


    ShowOrganizationStatsController controller;

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
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/OrganizationStatDelayChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }
    //Interval Definition
  
    private  String determineFirstInterval(){
        double firstInterval = controller.determineIntervalsMean(controller.determinePaymentOrganization()) -
                controller.determineIntervalsDeviation(controller.determinePaymentOrganization());
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("] -%s, %.2f]", infinitySymbol, firstInterval);
    }

    private String determineSecondInterval(){
        double firstInterval = controller.determineIntervalsMean(controller.determinePaymentOrganization()) -
                controller.determineIntervalsDeviation(controller.determinePaymentOrganization());
        double secondInterval = controller.determineIntervalsMean(controller.determinePaymentOrganization()) +
                controller.determineIntervalsDeviation(controller.determinePaymentOrganization());
        return String.format("]%.2f, %.2f[", firstInterval, secondInterval);
    }

    private String determineThirdInterval(){
        double secondInterval = controller.determineIntervalsMean(controller.determinePaymentOrganization()) +
                controller.determineIntervalsDeviation(controller.determinePaymentOrganization());
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("[%.2f, +%s[", secondInterval, infinitySymbol);
    }
   


    //NumberAxis Definition
    
    private double getLowerLimitValue(){
        return  controller.determineIntervalsMean(controller.determinePaymentOrganization()) -
                controller.determineIntervalsDeviation(controller.determinePaymentOrganization());
    }

    private double getHigherLimitValue(){
        return  controller.determineIntervalsMean(controller.determinePaymentOrganization()) +
                controller.determineIntervalsDeviation(controller.determinePaymentOrganization());
    }

    private int valuesFirstInterval(TreeMap<String, List<Double>> map){
        int counter = 0;
        for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i) <= getLowerLimitValue()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int valuesSecondInterval(TreeMap<String, List<Double>> map){
        int counter = 0;
        for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i) > getLowerLimitValue() && entry.getValue().get(i)< getHigherLimitValue()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int valuesThirdInterval(TreeMap<String, List<Double>> map){
        int counter = 0;
        for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i) >= getHigherLimitValue()) {
                    counter++;
                }
            }
        }
        return counter;
    }
  

    private ObservableList<String> populateWithPaymentData(){
        ObservableList<String> lst= FXCollections.observableArrayList ();
        List<String> list= controller.dataPaymentToString();
        lst.addAll ( list );
        return lst;
    }

    public void initialize(URL location, ResourceBundle resources) {
        controller = new ShowOrganizationStatsController();

        //populating ListView
        lstViewPayments.setItems(populateWithPaymentData());

        //Defining Mean Bar Chart values
        XYChart.Series<String, Number> seriesMean = new XYChart.Series<>();
        seriesMean.getData().add(new XYChart.Data<>(determineFirstInterval(), valuesFirstInterval(controller.determinePaymentOrganization())));
        seriesMean.getData().add(new XYChart.Data<>(determineSecondInterval(), valuesSecondInterval(controller.determinePaymentOrganization())));
        seriesMean.getData().add(new XYChart.Data<>(determineThirdInterval(), valuesThirdInterval(controller.determinePaymentOrganization())));

        //Adding series to Mean Bar Chart
        intervalsPayment.setCategories(FXCollections.observableArrayList(determineFirstInterval(), determineSecondInterval(), determineThirdInterval()));
        barChartPayment.getData().add(seriesMean);

        txtOverallMean.setText(String.format("%.2f",controller.determineIntervalsMean(controller.determinePaymentOrganization())));
        txtOverallDeviation.setText(String.format("%.2f",controller.determineIntervalsDeviation(controller.determinePaymentOrganization())));

    }
}


