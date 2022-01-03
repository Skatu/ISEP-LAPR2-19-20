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
import pt.ipp.isep.dei.lapr2.pot.controller.RegisterTaskController;
import pt.ipp.isep.dei.lapr2.pot.model.Task;
import pt.ipp.isep.dei.lapr2.pot.model.Transaction;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterTaskUI implements Initializable {

    /**
     * The text field of task's the ID
     */
    @FXML
    public TextField txtTaskID;

    /**
     * The text field of task's the description
     */
    @FXML
    public TextField txtTaskDescription;

    /**
     * The text field of task's the duration
     */
    @FXML
    public TextField txtTaskDuration;

    /**
     * The text field of task's the cost
     */
    @FXML
    public TextField txtTaskCost;

    /**
     * The text field of task's the category
     */
    @FXML
    public TextField txtTaskCategory;

    /**
     * The button to register
     */
    @FXML
    public Button btnRegister;

    /**
     * The text field of the execution's date
     */
    @FXML
    public TextField txtExecutionDate;

    /**
     * The text field of the execution's delay
     */
    @FXML
    public TextField txtExecutionDelay;

    /**
     * The text field of the execution's description
     */
    @FXML
    public TextField txtExecutionDescription;

    /**
     * The button to clear
     */
    @FXML
    public Button btnClear;

    /**
     * The button to cancel
     */
    @FXML
    public Button btnCancel;

    /**
     * The table of the tasks
     */
    @FXML
    private TableView<Task> tableTask;


    //private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * The pane of the register
     */
    @FXML
    public AnchorPane registerTaskPane;

    /**
     * The column of the task's id
     */
    @FXML
    public TableColumn<Task,String> columnTaskID;

    /**
     * The column of the task's description
     */
    @FXML
    public TableColumn<Task,String> columnTaskDesc;

    /**
     * The column of the task's duration
     */
    @FXML
    public TableColumn<Task,Double> columnTaskDuration;

    /**
     * The column of the task's cost
     */
    @FXML
    public TableColumn<Task,Double> columnTaskCost;

    /**
     * The column of the task's category
     */
    @FXML
    public TableColumn<Task,String> columnTaskCategory;

    /**
     * The controller
     */
    private RegisterTaskController m_controller;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * @param location The location used to resolve relative paths for the root object, or
     *                 <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m_controller=new RegisterTaskController();
        columnTaskID.setCellValueFactory ( new PropertyValueFactory<>( "id" ) );
        columnTaskDesc.setCellValueFactory ( new PropertyValueFactory<>( "briefDescription" ) );
        columnTaskDuration.setCellValueFactory ( new PropertyValueFactory<>( "duration" ) );
        columnTaskCost.setCellValueFactory ( new PropertyValueFactory<>( "cost" ) );
        columnTaskCategory.setCellValueFactory ( new PropertyValueFactory<>( "category" ) );
        tableTask.setItems(getTasks());
    }

    /**
     * Gets the list of all the tasks in the system
     * @return returns the list of the tasks
     */
    private List<Task> getListTasks(){
        return m_controller.getListTasks().getListTasks();
    }

    /**
     * * Creates an Observable list with all the tasks so they can
     * be displayed in the TableView.
     * @return the list created
     */
    private ObservableList<Task> getTasks(){
        ObservableList<Task> tasks= FXCollections.observableArrayList ();
        List<Task> list=getListTasks();
        tasks.addAll ( list );
        return tasks;
    }

    /**
     * Clears all the text fields
     * @param actionEvent button is clicked
     */
    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        txtTaskCategory.clear();
        txtTaskCost.clear();
        txtTaskDescription.clear();
        txtTaskDuration.clear();
        txtTaskID.clear();
    }

    /**
     * Cancels the register of the task
     * @param actionEvent button is clicked
     */
    @FXML
    public void btnCancelOnAction(ActionEvent actionEvent) {
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Cancel","Are you sure you want to cancel?" );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            goBackToCollaboratorMenu ();
        }
    }

    /**
     * Do the register of the task
     * @param actionEvent button is clicked
     */
    @FXML
    public void btnRegisterOnAction(ActionEvent actionEvent) {
        if(isAnyFieldEmpty ()){
            createAlert ( Alert.AlertType.WARNING,"Empty fields","No field can be empty" ).showAndWait ();
        }
        else{
            if(!isCreationOfTaskSuccessful ()){
                createAlert ( Alert.AlertType.ERROR,"Invalid fields","Invalid input. " +
                        "Please check if all the information is correct or if it already exists in the system.").showAndWait ();
            }else{
                confirmRegister ();
            }
        }
    }

    /**
     * Verifies if any field is empty
     * @return returns true or false
     */
    private boolean isAnyFieldEmpty(){
        return txtTaskID.getText ().trim ().isEmpty () || txtTaskDuration.getText ().trim ().isEmpty () ||
                txtTaskDescription.getText ().trim ().isEmpty () || txtTaskCost.getText ().trim ().isEmpty () ||
                txtTaskCategory.getText ().trim ().isEmpty ();
    }

    /**
     * Creates an alert box to inform the user of any unexpected event that
     * occurred.
     * @param type The type of alert.
     * @param header The text for the header of the alert box.
     * @param message The message to be displayed in the body of the alert box.
     * @return the alert
     */
    private Alert createAlert(Alert.AlertType type, String header, String message){
        Alert alert=new Alert ( type );

        alert.setTitle ( "New Task" );
        alert.setHeaderText ( header );
        alert.setContentText ( message );
        return alert;
    }

    /**
     * Verifies if the register is successful
     * @return returns true or false
     */
    private boolean isCreationOfTaskSuccessful() {
        return m_controller.newTask(txtTaskID.getText().trim(), txtTaskDescription.getText().trim(), Double.parseDouble(txtTaskDuration.getText().trim()),
                Double.parseDouble(txtTaskCost.getText().trim()), txtTaskCategory.getText().trim());
    }

    /**
     * Asks for confirmation of the register
     */
    private void confirmRegister(){
        String dataTask=getDataTask();
        Alert confirm=createAlert ( Alert.AlertType.CONFIRMATION,"Create Task?", dataTask );
        Optional<ButtonType> result=confirm.showAndWait ();
        if(result.orElse ( null )==ButtonType.OK){
            createTaskAndShowConfirmation ();
        }
        else{
            confirm.close ();
        }
    }

    /**
     * Gets the data of the task
     * @return returns all the data of the task created
     */
    private String getDataTask() {
        return "\nTask: " +
                "\n\t ID: " + txtTaskID.getText()  +
                "\n\t Description: " + txtTaskDescription.getText () +
                "\n\t Duration: " + txtTaskDuration.getText()  +
                "\n\t Cost per Hour: " + txtTaskCost.getText () +
                "\n\t Category: " + txtTaskCategory.getText ();
                    }

    /**
     * Creates the task and show its data
      */
    private void createTaskAndShowConfirmation(){
        m_controller.registerTask();
        createAlert ( Alert.AlertType.CONFIRMATION,"Task and Execution created",
                "The Task and Execution were added to the system.").showAndWait ();;
        goBackToCollaboratorMenu();
    }

    /**
     * Goes back to the Collaborator Menu
     */
    private void goBackToCollaboratorMenu(){
        try{
            registerTaskPane.getChildren ().setAll ((Node) FXMLLoader.load(getClass().getResource( "/fxml/CollaboratorMenuScene.fxml" )) );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
