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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pt.ipp.isep.dei.lapr2.pot.controller.ShowFreelancerPerformanceStatisticsController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class PerformanceStatDelayChartUI implements Initializable {

    ShowFreelancerPerformanceStatisticsController controller = new ShowFreelancerPerformanceStatisticsController();

    @FXML
    private AnchorPane delayPane;

    @FXML
    private Label lblNormalDistribution;

    @FXML
    private BarChart<String, Number> barChartDelay;

    @FXML
    private NumberAxis numberTasksDelay;

    @FXML
    private CategoryAxis intervalsDelay;

    @FXML
    private ListView<String> lstViewDelay;

    @FXML
    private Button btnGoBackToPaymentStatistics;

    @FXML
    private Button btnGoBackToMenu;

    @FXML
    private TextField txtOverallMean;

    @FXML
    private TextField txtOverallDeviation;

    @FXML
    void btnGoBackToMenuOnAction(ActionEvent event) {
        try{
            delayPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatisticsOptionScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    @FXML
    void btnGoBackToPaymentStatisticsOnAction(ActionEvent event) {
        try{
            delayPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatPaymentChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    //Interval Definition
    // <editor-fold defaultstate="collapsed">
    private String determineFirstInterval(){
        double firstInterval = controller.determineIntervalsMean(controller.determineDelayPlatform()) -
                controller.determineIntervalsDeviation(controller.determineDelayPlatform());
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("] -%s, %.2f]", infinitySymbol, firstInterval);
    }

    private String determineSecondInterval(){
        double firstInterval = controller.determineIntervalsMean(controller.determineDelayPlatform()) -
                controller.determineIntervalsDeviation(controller.determineDelayPlatform());
        double secondInterval = controller.determineIntervalsMean(controller.determineDelayPlatform()) +
                controller.determineIntervalsDeviation(controller.determineDelayPlatform());
        return String.format("]%.2f, %.2f[", firstInterval, secondInterval);
    }

    private String determineThirdInterval(){
        double secondInterval = controller.determineIntervalsMean(controller.determineDelayPlatform()) +
                controller.determineIntervalsDeviation(controller.determineDelayPlatform());
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("[%.2f, +%s[", secondInterval, infinitySymbol);
    }
    // </editor-fold>

    //NumberAxis Definition
    // <editor-fold defaultstate="collapsed">
    private double getLowerLimitValue(){
        return  controller.determineIntervalsMean(controller.determineDelayPlatform()) -
                controller.determineIntervalsDeviation(controller.determineDelayPlatform());
    }

    private double getHigherLimitValue(){
        return controller.determineIntervalsMean(controller.determineDelayPlatform()) +
                controller.determineIntervalsDeviation(controller.determineDelayPlatform());
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
    // </editor-fold>

    private double determineNormalDistribution(){
        return controller.calculateNormalDistribution();
    }

    private ObservableList<String> populateWithDelayData(){
        ObservableList<String> lst= FXCollections.observableArrayList ();
        List<String> list= controller.dataDelayToString();
        lst.addAll ( list );
        return lst;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new ShowFreelancerPerformanceStatisticsController();

        //initializing ListView
        lstViewDelay.setItems(populateWithDelayData());

        //Defining Mean Bar Chart values
        XYChart.Series<String, Number> seriesMean = new XYChart.Series<>();
        seriesMean.getData().add(new XYChart.Data<>(determineFirstInterval(), valuesFirstInterval(controller.determineDelayPlatform())));
        seriesMean.getData().add(new XYChart.Data<>(determineSecondInterval(), valuesSecondInterval(controller.determineDelayPlatform())));
        seriesMean.getData().add(new XYChart.Data<>(determineThirdInterval(), valuesThirdInterval(controller.determineDelayPlatform())));

        //Adding series to Mean Bar Chart
        barChartDelay.getData().add(seriesMean);

        String normalDistribution = String.format("The probability of the execution delay time of the freelancers being higher than 3 hours is : P(X>3) = %.2f", determineNormalDistribution());
        lblNormalDistribution.setText(normalDistribution);

        txtOverallMean.setText(String.format("%.2f",controller.determineIntervalsMean(controller.determineDelayPlatform())));
        txtOverallDeviation.setText(String.format("%.2f",controller.determineIntervalsDeviation(controller.determineDelayPlatform())));
    }
}
