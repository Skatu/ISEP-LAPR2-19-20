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
import pt.ipp.isep.dei.lapr2.pot.controller.ShowFreelancerPerformanceStatisticsController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class PerformanceStatFreelDelChartUI implements Initializable {

    @FXML
    private TextField txtFreelancerID;

    @FXML
    private Label lblFreelancer;

    @FXML
    private CategoryAxis intervalsDelay;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private Button btnReturnToMenu;

    @FXML
    private Button btnPayment;

    @FXML
    private BarChart<String, Number> barChartDelay;

    @FXML
    private NumberAxis numberDelays;

    @FXML
    private TextField txtMean;

    @FXML
    private TextField txtDeviation;

    ShowFreelancerPerformanceStatisticsController controller;
    String freelancerID;

    @FXML
    void btnReturnToMenuOnAction(ActionEvent event) {
        try{
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatisticsOptionScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        try{
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatFreelPayChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    //Interval Definition
    // <editor-fold defaultstate="collapsed">
    private String determineFirstInterval() {
        double firstInterval =controller.calculateMeanDelay().get(freelancerID) -
                controller.calculateDeviationDelay().get(freelancerID);
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("] -%s, %.2f]", infinitySymbol, firstInterval);
    }

    private String determineSecondInterval() {
        double firstInterval = controller.calculateMeanDelay().get(freelancerID)
                - controller.calculateDeviationDelay().get(freelancerID);
        double secondInterval = controller.calculateMeanDelay().get(freelancerID)
                + controller.calculateDeviationDelay().get(freelancerID);
        return String.format("]%.2f, %.2f[", firstInterval, secondInterval);
    }

    private String determineThirdInterval() {
        double secondInterval = controller.calculateMeanDelay().get(freelancerID)
                + controller.calculateDeviationDelay().get(freelancerID);
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("[%.2f, +%s[", secondInterval, infinitySymbol);
    }
    // </editor-fold>

    //NumberAxis Definition
    // <editor-fold defaultstate="collapsed">

    private double getLowerLimitValue(){
        return controller.calculateMeanDelay().get(freelancerID) -
                controller.calculateDeviationDelay().get(freelancerID);
    }

    private double getHigherLimitValue(){
        return controller.calculateMeanDelay().get(freelancerID) +
                controller.calculateDeviationDelay().get(freelancerID);
    }

    private double valuesFirstInterval() {
        int counter = 0;
        if (controller.determinePaymentPlatform().get(freelancerID).size() == 1){
            return 0;
        }
        for (double payment : controller.determineDelayPlatform().get(freelancerID)){
            if (payment <= getLowerLimitValue()){
                counter++;
            }
        }
        return counter;
    }

    private double valuesSecondInterval() {
        int counter = 0;
        for (double payment : controller.determineDelayPlatform().get(freelancerID)){
            if (payment > getLowerLimitValue() && payment < getHigherLimitValue()){
                counter++;
            }
        }
        return counter;
    }

    private double valuesThirdInterval() {
        int counter = 0;
        for (double payment : controller.determineDelayPlatform().get(freelancerID)){
            if (payment >= getHigherLimitValue()){
                counter++;
            }
        }
        return counter;
    }
    // </editor-fold>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new ShowFreelancerPerformanceStatisticsController();
        freelancerID = ShowFreelancerPerformanceStatisticsController.getFreelancerID();

        String mean = String.format("%.2f", controller.calculateMeanDelay().get(freelancerID));
        txtMean.setText(mean);

        String deviation = String.format("%.2f", controller.calculateDeviationDelay().get(freelancerID));
        txtDeviation.setText(deviation);

        txtFreelancerID.setText(freelancerID);

        //Defining Mean Bar Chart values
        XYChart.Series<String, Number> seriesMean = new XYChart.Series<>();
        seriesMean.getData().add(new XYChart.Data<>(determineFirstInterval(), valuesFirstInterval()));
        seriesMean.getData().add(new XYChart.Data<>(determineSecondInterval(), valuesSecondInterval()));
        seriesMean.getData().add(new XYChart.Data<>(determineThirdInterval(), valuesThirdInterval()));

        //Adding series to Mean Bar Chart
        intervalsDelay.setCategories(FXCollections.observableArrayList(determineFirstInterval(), determineSecondInterval(), determineThirdInterval()));
        barChartDelay.getData().add(seriesMean);
    }


}

