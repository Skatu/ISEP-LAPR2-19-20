package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListTransactions implements Serializable {
    /**
     *  The serial version of the class
     */
    private static final long serialVersionUID = -8808573044194657672L;

    /**
     * The list of transactions
     */
    private List<Transaction> m_lstTransactions;

    /**
     * Builds an instance of ListTransaction.
     */
    public ListTransactions() {
        this.m_lstTransactions=new ArrayList<> ();
    }

    /**
     * Builds a new instance of transaction receiving attributes of Task, Execution and freelancer
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
    public Transaction newTransaction(String transId, String taskId, String taskDescription,
                                      double taskAssignedDuration, double taskCostPerHour, String taskCategory,
                                      Calendar executionFinishDate, double delay, String executionBriefDescription, String freelancerId,
                                      String freelancerName, String freelancerExpertise, String freelancerEmail,
                                      String freelancerNIF, String freelancerBankAccount, String address, String country)
    {
        return new Transaction(transId, taskId, taskDescription, taskAssignedDuration, taskCostPerHour, taskCategory,
                executionFinishDate, delay, executionBriefDescription, freelancerId, freelancerName, freelancerExpertise,
                freelancerEmail, freelancerNIF, freelancerBankAccount, address, country);
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
    public Transaction newTransaction(String transactionID, Task task, Execution execution,Freelancer freelancer) {
        return new Transaction ( transactionID,task,execution,freelancer );
    }

    /**
     * Verifies if the transaction is valid, if the transaction received by parameter
     * equals the other, returns false, otherwise returns true.
     *
     * @param transaction transaction
     * @return true if the transaction is valid, otherwise, returns false
     */
    public boolean validateTransaction(Transaction transaction){
        for(Transaction t: m_lstTransactions){
            if(transaction.equals ( t )) return false;
        }
        return true;
    }

    /**
     * Method that adds the transaction received by parameter to the
     * transaction list.
     * @param transaction transaction
     * @return true if the transaction is added, otherwise, returns false
     */
    boolean addTransaction(Transaction transaction){
        return m_lstTransactions.add(transaction);
    }

    /**
     * Method that adds the transaction received by parameter to the list of transactions if it is
     * valid.
     * @param transaction the transaction to add
     * @return true if the transaction is added, otherwise, returns false
     */
    public boolean registerTransaction(Transaction transaction){
        if(validateTransaction ( transaction ))
            return addTransaction ( transaction );
        return false;
    }

    /*

    public void newFileReading(String strDirectory, RegistryFreelancers m_oRegistryFreelancers, ListTasks m_oListTasks){
        //read the file.
        this.loadTransactions=new LoadTransactions ();
        String extension=LoadTransactions.getFileExtension ( strDirectory );
        switch (extension){
            case ".csv":
                this.loadTransactions.setReadFileStrategy ( new ReadCsvFile ( m_oRegistryFreelancers, m_oListTasks, this ) );
                break;
            case ".txt":
                this.loadTransactions.setReadFileStrategy ( new ReadTxtFile ( m_oRegistryFreelancers, m_oListTasks, this ) );
                break;
        }
        this.loadTransactions.read ( strDirectory );
    }

     */


    /*
    public ArrayList<Integer>[] newFileReading(String strDirectory, RegistryFreelancers m_oRegistryFreelancers, ListTasks m_oListTasks){
        //read the file.
        //this.loadTransactions=new LoadTransactions (  strDirectory, m_oRegistryFreelancers,
        //        m_oListTasks, this);
        this.loadTransactions=new LoadTransactions ();
        loadTransactions.setReadFileStrategy ( new ReadCsvFile ( m_oRegistryFreelancers, m_oListTasks, this ) );
        loadTransactions.read ( strDirectory );
        serializeTransactions ();
        //return loadTransactions.newFileReading();
        return null;
    }

     */

  /*
    *Refactored to class LoadTransactions

    public String getLoadFileLog(){
        StringBuilder s=new StringBuilder ();

        s.append ( "Number of lines read: " );
        s.append ( getSuccessfulyReadLinesFromFile ().size () + getFailedReadLinesFromFile ().size () );
        s.append ( "\n" );
        s.append ( "Successful lines: " );
        s.append ( getSuccessfulyReadLinesFromFile ().size () );
        s.append ( "\n" );
        List<String> succLines=getSuccessfulyReadLinesFromFile ();
        for(String line:succLines ){
            s.append ( "\t" );
            s.append ( line );
            s.append ( "\n" );
        }
        s.append ( "Failed lines: " );
        s.append ( getFailedReadLinesFromFile ().size () );
        s.append ( "\n" );
        List<String> failLines=getFailedReadLinesFromFile ();
        for(String line:failLines ){
            s.append ( "\t" );
            s.append ( line );
            s.append ( "\n" );
        }
        return s.toString ();
    }



    public List<String> getSuccessfulyReadLinesFromFile(){
        return loadTransactions.getLstSuccessfulLines ();
    }

    public List<String> getFailedReadLinesFromFile(){
        return loadTransactions.getLstFailedLines ();
    }

    public String getFailedReadLinesFromFileString(){
        StringBuilder s=new StringBuilder ();
        List<String> failedLines=loadTransactions.getLstFailedLines ();
        for(String str: failedLines){
            s.append ( str );
            s.append ( "\n" );
        }
        return s.toString ();
    }
        public List<Transaction> getListTransactionsRead() {
        return loadTransactions.getLstTransactionsRead ();
    }
   */

    /**
     * Method that verifies if the transaction ID received by parameter is assigned to
     * any existent transaction.
     *
     * @param transactionID transaction ID
     * @return true if the transaction exists, otherwise, returns false
     */
    public boolean existsTransaction(String transactionID){
        for(Transaction t: m_lstTransactions){
            if(t.getTransactionID ().equalsIgnoreCase ( transactionID )){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list transaction.
     * @return the list transaction
     */
    public List<Transaction> getM_lstTransactions() {
        return m_lstTransactions;
    }


    /**
     * Returns the textual representation of several Transaction attributes.
     *
     * @return the textual representation of several Transaction attributes
     */
    public List<String> performanceStatisticsDataToString(){
        List<String> list = new ArrayList<>();
        for (Transaction t : m_lstTransactions){
            String data = String.format("Freelancer ID: %s\n\tFreelancer Expertise: %s\nTask ID: %s\n\t" +
                            "Task Cost Per Hour: %.2f€\n\tTask Assigned Duration: %.2fh\n\tExecution Delay: %.2fh\n" +
                            "Transaction ID: %s\n\tPayment: %.2f€",
                    t.getFreelancer().getFreelancerID(), t.getFreelancer().getFreelancerExpertise().getDesignation(),
                    t.getTask().getId(), t.getTask().getCost(), t.getTask().getDuration(), t.getExecution().getDelay(),
                    t.getTransactionID(), t.computeAmount());
            list.add(data);
        }
        return list;
    }

}
