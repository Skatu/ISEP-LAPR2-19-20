package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.Calendar;

public class RegisterPaymentTransactionController {

    /**
     * The platform
     */
    private Platform platform;

    /**
     * The organization
     */
    private Organization organization;

    /**
     * The list of transactions
     */
    private ListTransactions listTransactions;

    /**
     * The transaction
     */
    private Transaction transaction;

    /**
     * The execution
     */
    private Execution execution;

    /**
     * Builds an instance of RegisterPaymentTransactionController
     */
    public RegisterPaymentTransactionController() {
        if(!ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_COLLABORATOR_ORGANIZATION))
            throw new IllegalStateException("Non authorized User.");

        platform =ApplicationPOT.getInstance ().getPlatform ();
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession user = app.getCurrentSession();
        String emailUser = user.getUserEmail();
        RegistryOrganizations registryOrganizations = platform.getRegistryOrganizations();
        organization = registryOrganizations.getOrganizationByCollaboratorEmail(emailUser);
        listTransactions = organization.getListTransactions();
    }

    /**
     * Method that verifies if the Transaction created is valid.
     *
     * @param transId                               transaction ID
     * @param taskId                                task ID
     * @param taskDescription                       task description
     * @param taskAssignedDuration                  task duration
     * @param taskCostPerHour                       task cost per hour
     * @param taskCategory                          task category
     * @param strExecutionFinishDate                execution finish date
     * @param delay                                 delay in executing the task
     * @param executionBriefDescription             execution brief description
     * @param freelancerId                          freelancer ID
     * @param freelancerName                        freelancer name
     * @param freelancerExpertise                   freelancer expertise
     * @param freelancerEmail                       freelancer email
     * @param freelancerNIF                         freelancer nif
     * @param freelancerBankAccount                 freelancer IBAN
     * @param address                               freelancer address
     * @param country                               freelancer transaction
     * @return true if the transaction is valid, otherwise, returns false
     */
    public boolean newTransaction(String transId, String taskId, String taskDescription, double taskAssignedDuration,
                                  double taskCostPerHour, String taskCategory, String strExecutionFinishDate, double delay,
                                  String executionBriefDescription, String freelancerId, String freelancerName,
                                  String freelancerExpertise, String freelancerEmail, String freelancerNIF,
                                  String freelancerBankAccount, String address, String country){

        try {
            Calendar executionFinishDate=Parse.parseDate ( strExecutionFinishDate );
            transaction = listTransactions.newTransaction(transId, taskId, taskDescription, taskAssignedDuration,
                    taskCostPerHour, taskCategory, executionFinishDate, delay, executionBriefDescription, freelancerId,
                    freelancerName, freelancerExpertise, freelancerEmail, freelancerNIF, freelancerBankAccount, address, country);

            if (transaction == null){
                return false;
            }
            else if (existsTransactionByID(transId)){
                return false;
            }
            else if (!existsFreelancerByNIF(freelancerNIF)){
                return false;
            }
            else if (!existsTaskById(taskId)){
                return false;
            }
        }catch (IllegalArgumentException e) {
            e.getStackTrace();
        }
        return listTransactions.validateTransaction(transaction);
    }

    /**
     * Returns the transaction
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Determines the payment
     * @return the payment
     */
    public double computeAmount(){
        return transaction.computeAmount();
    }

    /**
     * Returns the list of transactions
     * @return the list of transactions
     */
    public ListTransactions getListTransactions() {
        return listTransactions;
    }

    /**
     * Calls the method registerTransaction from ListTransactions class.
     *
     * @return true if the transaction is added to the list of transactions, otherwise, returns false
     */
    public boolean registerTransaction(){
        return this.listTransactions.registerTransaction(this.transaction);
    }

    /**
     * Returns the textual representation of Execution
     * @return characteristics of Execution
     */
    public String getExecutionString(){
        return this.execution.toString();
    }

    /**
     * Calls the method from ListTasks that checks if the TaskID received as parameter
     * is registered in the system.
     *
     * @param taskID task ID
     * @return true if the task exists, otherwise, returns false
     */
    public boolean existsTaskById (String taskID) {
        return organization.getListTasks().existsTaskById(taskID);
    }

    /**
     * Calls the method from RegistryFreelancers that checks if the freelancerNIF received as parameter
     * is registered in the system.
     *
     * @param freelancerNIF freelancer NIF
     * @return true if the freelancer exists, otherwise, returns false
     */
    public boolean existsFreelancerByNIF(String freelancerNIF){
        return platform.getRegistryFreelancers().existsFreelancerByNIF(freelancerNIF);
    }

    /**
     * Calls the method from ListTransactions that checks if the TransactionID received as parameter
     * is registered in the system.
     * @param transactionID transaction ID
     * @return true if the transaction exists, otherwise, returns false
     */
    public boolean existsTransactionByID(String transactionID){
        return organization.getListTransactions().existsTransaction(transactionID);
    }

    /**
     * Converts the string received by parameter to a {@link Calendar} object.
     *
     * @param date  String to be parsed to Calendar.
     * @return {@link Calendar} object.
     */
    public Calendar parseDate(String date){
        Calendar calendar = Calendar.getInstance();
        String[] array=date.split ( "-" );
        int year=Integer.parseInt ( array[0]);
        int month=Integer.parseInt ( array[1]);
        int day=Integer.parseInt ( array[2]);
        calendar.set(year, month-1, day);
        return calendar;
    }
}
