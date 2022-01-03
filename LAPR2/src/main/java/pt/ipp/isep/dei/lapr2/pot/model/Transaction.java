package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.Calendar;

public class Transaction implements Serializable {

    /**
     *  The serial version of the class
     */
    private static final long serialVersionUID = -582962415322819796L;

    /**
     * The transaction ID
     */
    private String transactionID;

    /**
     * The task
     */
    private Task task;

    /**
     *  The execution
     */
    private Execution execution;

    /**
     * The Freelancer
     */
    private Freelancer freelancer;

    /**
     * Builds an instance of transaction receiving attributes of Task, Execution and freelancer
     * by parameter.
     *
     * @param transId                               transaction ID
     * @param taskId                                task ID
     * @param taskDescription                       task description
     * @param taskAssignedDuration                  task duration
     * @param taskCostPerHour                       task cost per hour
     * @param taskCategory                          task category
     * @param executionFinishDate                   execution finish date
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
     * @return the created transaction
     */
    public Transaction (String transId, String taskId, String taskDescription,
                        double taskAssignedDuration, double taskCostPerHour, String taskCategory,
                        Calendar executionFinishDate, double delay, String executionBriefDescription, String freelancerId,
                        String freelancerName, String freelancerExpertise, String freelancerEmail,
                        String freelancerNIF, String freelancerBankAccount, String address, String country){
        if(transId==null || transId.isEmpty()){
            throw new IllegalArgumentException ("Transaction ID cannot be empty");
        }
        if (taskId == null || taskId.isEmpty() || taskCostPerHour <= 0 || taskCategory == null || taskCategory.isEmpty()
                || executionFinishDate == null || executionBriefDescription==null || executionBriefDescription.isEmpty()){
            throw new IllegalArgumentException ("Task cannot be empty");
        }
        if (freelancerId == null || freelancerId.isEmpty() || freelancerName == null || freelancerName.isEmpty()
                || freelancerExpertise == null || freelancerExpertise.isEmpty() || freelancerEmail == null || freelancerEmail.isEmpty()
                || freelancerNIF == null || freelancerNIF.isEmpty() || freelancerBankAccount == null || freelancerBankAccount.isEmpty()
                || address == null || address.isEmpty() || country == null || country.isEmpty()){
            throw new IllegalArgumentException ("Freelancer cannot be empty");
        }
        this.transactionID = transId;
        task = new Task(taskId, taskDescription, taskAssignedDuration, taskCostPerHour, taskCategory);
        execution = newExecution(executionFinishDate, delay, executionBriefDescription);
        freelancer = new Freelancer(freelancerId, freelancerName, freelancerExpertise, freelancerEmail, freelancerNIF,
                freelancerBankAccount, address, country);
    }


    /**
     * Creates a new instance of Transaction receiving the transaction ID, the Task and the Freelancer
     * by parameter.
     *
     * @param transactionID         the transactionID
     * @param task                  task
     * @param freelancer            freelancer
     * @return the created transaction
     */
    public Transaction(String transactionID, Task task,Freelancer freelancer) {
        if(transactionID==null || transactionID.isEmpty ()){
            throw new IllegalArgumentException ("Transaction ID cannot be empty");
        }
        if(task==null){
            throw new IllegalArgumentException ("Task cannot be empty");
        }
        if(freelancer==null){
            throw new IllegalArgumentException ("Freelancer cannot be empty");
        }
        this.transactionID = transactionID;
        this.task = task;
        this.freelancer = freelancer;
    }

    /**
     * Creates a new instance of Transaction receiving the transaction ID, the Task, the Execution and the Freelancer
     * by parameter.
     *
     * @param transactionID         the transactionID
     * @param task                  task
     * @param execution             execution
     * @param freelancer            freelancer
     * @return the created transaction
     */
    public Transaction(String transactionID, Task task, Execution execution, Freelancer freelancer) {
        if(transactionID==null || transactionID.isEmpty ()){
            throw new IllegalArgumentException ("Transaction ID cannot be empty");
        }
        if(task==null){
            throw new IllegalArgumentException ("Task cannot be empty");
        }
        if (execution == null){
            throw new IllegalArgumentException ("Execution cannot be empty");
        }
        if(freelancer==null) {
            throw new IllegalArgumentException("Freelancer cannot be empty");
        }
        this.transactionID = transactionID;
        this.task = task;
        this.execution = execution;
        this.freelancer = freelancer;
    }

    /**
     * Builds an instance of Transaction receiving Transaction by parameter
     *
     * @param t Transaction
     */
    public Transaction(Transaction t) {
        this(t.transactionID,t.task,t.execution,t.freelancer);
    }

    /**
     * Creates an instance of Execution receiving the endDate, delay and brief description by
     * parameter.
     *
     * @param endDate               execution finish date
     * @param delay                 execution delay
     * @param briefDescription      execution brief description
     * @return the created Execution
     */
    public Execution newExecution(Calendar endDate, double delay, String briefDescription){
        return new Execution(endDate, delay, briefDescription);
    }

    /**
     * Returns the transaction ID
     * @return the transaction ID
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Returns the task
     *
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    /**
     * Returns the Execution
     *
     * @return the Execution
     */
    public Execution getExecution() {
        return execution;
    }

    /**
     * Compares the object received by parameter with the Transaction. First
     * it verifies if both objects share references, then it verifies if the object
     * is null or if the class is different from Transaction. And finally down casts
     * the object to Transaction type and compares the transaction ID.
     * @param o object to compare
     * @return true if the objects are equal, otherwise, returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID.equals(that.transactionID) &&
            task.getId().equals(that.task.getId());
    }

    /**
     * Returns the freelancer
     * @return the freelancer
     */
    public Freelancer getFreelancer() {
        return freelancer;
    }

    /**
     * Determines the value to pay to a freelancer
     *
     * @return payment
     */
    public double computeAmount(){
        return freelancer.calculateTotalPaymentAmount(task.paymentJuniorFreelancer());
    }

    /**
     * Creates an instance of Execution receiving the finish date, the delay and the brief description by parameter.
     *
     * @param strExecutionFinishDate        execution finish date
     * @param strExecutionDelayHours        execution delay
     * @param executionBriefDescription     execution brief description
     * @return the created Execution
     */
    public static Execution newExecution(String strExecutionFinishDate,String strExecutionDelayHours, String executionBriefDescription){
        if(Parse.tryParseDouble ( strExecutionDelayHours )){
            double executionDelayHours=Double.parseDouble ( strExecutionDelayHours );
            Calendar executionFinishDate=Parse.parseDate ( strExecutionFinishDate );
            return new Execution ( executionFinishDate,executionDelayHours,executionBriefDescription );
        }
        return null;
    }
}

