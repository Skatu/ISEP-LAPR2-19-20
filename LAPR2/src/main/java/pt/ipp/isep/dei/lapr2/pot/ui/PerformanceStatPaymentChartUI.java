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
import pt.ipp.isep.dei.lapr2.pot.controller.ShowFreelancerPerformanceStatisticsController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class PerformanceStatPaymentChartUI implements Initializable {

    private ShowFreelancerPerformanceStatisticsController controller;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private BarChart<String, Number> barChartPayment;

    @FXML
    private CategoryAxis intervalsPayment;

    @FXML
    private NumberAxis numberTasks;

    @FXML
    private Button btnReturnToMenu;

    @FXML
    private Button btnDelay;

    @FXML
    private ListView<String> lstViewPayments;


    @FXML
    private TextField txtOverallMean;

    @FXML
    private TextField txtOverallDeviation;


    @FXML
    void btnReturnToMenuOnAction(ActionEvent event) {
        try{
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatisticsOptionScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }

    }

    @FXML
    void btnDelayOnAction(ActionEvent event) {
        try{
            paymentPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/PerformanceStatDelayChartScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    //Interval Definition
    // <editor-fold defaultstate="collapsed">
    private  String determineFirstInterval(){
        double firstInterval = controller.determineIntervalsMean(controller.determinePaymentPlatform()) -
                controller.determineIntervalsDeviation(controller.determinePaymentPlatform());
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("] -%s, %.2f]", infinitySymbol, firstInterval);
    }

    private String determineSecondInterval(){
        double firstInterval = controller.determineIntervalsMean(controller.determinePaymentPlatform()) -
                controller.determineIntervalsDeviation(controller.determinePaymentPlatform());
        double secondInterval = controller.determineIntervalsMean(controller.determinePaymentPlatform()) +
                controller.determineIntervalsDeviation(controller.determinePaymentPlatform());
        return String.format("]%.2f, %.2f[", firstInterval, secondInterval);
    }

    private String determineThirdInterval(){
        double secondInterval = controller.determineIntervalsMean(controller.determinePaymentPlatform()) +
                controller.determineIntervalsDeviation(controller.determinePaymentPlatform());
        String infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
        return String.format("[%.2f, +%s[", secondInterval, infinitySymbol);
    }
    // </editor-fold>


    //NumberAxis Definition
    // <editor-fold defaultstate="collapsed">
    private double getLowerLimitValue(){
        return  controller.determineIntervalsMean(controller.determinePaymentPlatform()) -
                controller.determineIntervalsDeviation(controller.determinePaymentPlatform());
    }

    private double getHigherLimitValue(){
        return  controller.determineIntervalsMean(controller.determinePaymentPlatform()) +
                controller.determineIntervalsDeviation(controller.determinePaymentPlatform());
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

    private ObservableList<String> populateWithPaymentData(){
        ObservableList<String> lst= FXCollections.observableArrayList ();
        List<String> list= controller.dataPaymentToString();
        lst.addAll ( list );
        return lst;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new ShowFreelancerPerformanceStatisticsController();

        //populating ListView
        lstViewPayments.setItems(populateWithPaymentData());

        //Defining Mean Bar Chart values
        XYChart.Series<String, Number> seriesMean = new XYChart.Series<>();
        seriesMean.getData().add(new XYChart.Data<>(determineFirstInterval(), valuesFirstInterval(controller.determinePaymentPlatform())));
        seriesMean.getData().add(new XYChart.Data<>(determineSecondInterval(), valuesSecondInterval(controller.determinePaymentPlatform())));
        seriesMean.getData().add(new XYChart.Data<>(determineThirdInterval(), valuesThirdInterval(controller.determinePaymentPlatform())));

        //Adding series to Mean Bar Chart
        intervalsPayment.setCategories(FXCollections.observableArrayList(determineFirstInterval(), determineSecondInterval(), determineThirdInterval()));
        barChartPayment.getData().add(seriesMean);


        txtOverallMean.setText(String.format("%.2f",controller.determineIntervalsMean(controller.determinePaymentPlatform())));
        txtOverallDeviation.setText(String.format("%.2f",controller.determineIntervalsDeviation(controller.determinePaymentPlatform())));

    }
}
