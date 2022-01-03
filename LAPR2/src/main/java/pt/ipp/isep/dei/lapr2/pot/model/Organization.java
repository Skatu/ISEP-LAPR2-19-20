/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author paulomaio
 * @translated Rafael Moreira
 */
public class Organization implements Serializable {

    /**
     * Name of the organization
     */
    private String name;

    /**
     * NIF of the organization
     */
    private String nif;

    /**
     * Manager of the organization
     */
    private Manager manager;

    /**
     * Collaborator of the organization
     */
    private Collaborator collaborator;

    /**
     * Date of previous payment
     */
    private Date LastPaymentDate;

    /**
     * Date of payment
     */
    private Date PaymentDate;

    /**
     * ListTasks instance of the organization
     */
    private ListTasks listTasks;

    /**
     * Billing instance of the Organization
     */
    private Bill m_oBill;

    /**
     * ListTransactions instance of the organization
     */
    private ListTransactions listTransactions;

    /**
     * Serialization ID for the class.
     */
    private static final long serialVersionUID = 1494657484875352010L;

    /**
     * List of pending transactions of the organization
     */
    private List<Transaction> m_lstPendingTransactions;

    /**
     * Constructor method of the organization instance
     *
     * @param strName name of the organization
     * @param strNIF NIF of the organization
     * @param manager Manager of the organization
     * @param collaborator Collaborator of the organization
     * @throws IllegalArgumentException if any of the arguments are invalid.
     */
    public Organization(String strName, String strNIF, Manager manager, Collaborator collaborator) {
        if (strName == null || strName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (strNIF == null || strNIF.isEmpty()) {
            throw new IllegalArgumentException("NIF cannot be null or empty.");
        }
        if (manager == null) {
            throw new IllegalArgumentException("Manager cannot be null");
        }
        if (collaborator == null) {
            throw new IllegalArgumentException("Collaborator cannot be null");
        }

        this.name = strName;
        this.nif = strNIF;
        this.manager = manager;
        this.collaborator = collaborator;
        this.PaymentDate = new Date();
        this.listTasks = new ListTasks();
        this.listTransactions = new ListTransactions();
    }


    // </editor-fold>

    //UC5 Implementation

    /**
     * Returns the pending transactions made from the last paymente date
     * @return
     */
    List<Transaction> getPendingTransactionList() {
        m_lstPendingTransactions.clear();
        for(Transaction tran: getListTransactions().getM_lstTransactions()){
            if (       tran.getExecution().getEndDate().getTime().getYear() <= LastPaymentDate.getYear() && tran.getExecution().getEndDate().getTime().getYear() >= PaymentDate.getYear()
                    || tran.getExecution().getEndDate().getTime().getMonth() <= LastPaymentDate.getMonth() && tran.getExecution().getEndDate().getTime().getMonth() >= PaymentDate.getMonth()
                    || tran.getExecution().getEndDate().getTime().getDay() <= LastPaymentDate.getDay() && tran.getExecution().getEndDate().getTime().getDay() >= PaymentDate.getDay()
                    || tran.getExecution().getEndDate().getTime().getHours() <= LastPaymentDate.getHours() && tran.getExecution().getEndDate().getTime().getHours() >= PaymentDate.getHours()) {

            } else {
                m_lstPendingTransactions.add(tran);
            }
        }
        return this.m_lstPendingTransactions;
    }

    /**
     * Creates a Bill instance from a list of transactions
     * @param m_lstTran
     * @return
     * @throws IOException
     */
    Bill create(List<Transaction> m_lstTran) throws IOException {
        m_oBill.create(m_lstPendingTransactions);
        return m_oBill;
    }

    /**
     * Validates the PaymentDate
     * @param date
     * @return
     */
    private boolean validatePaymentDate(Date date) {
        if(date != null & date != PaymentDate & date != LastPaymentDate){
            return true;
        }
        return false;
    }

    /**
     * Registers the date in the organization instance
     * @param date
     */
    public void RegisterDate(Date date) {
        if (validatePaymentDate(date)) {
            setPaymentDate(date);
        } else {
            System.out.println("Invalid Date!");
        }
    }

    /**
     * Sets the date of payment
     * @param date Date of payment in {@link Date} format.
     */
    private void setPaymentDate(Date date) {
        Date curr = new Date();
        if (LastPaymentDate != null) {
            if (curr.compareTo(LastPaymentDate) > 0) {
                this.PaymentDate = date;
            } else
                this.LastPaymentDate = this.PaymentDate;
                this.PaymentDate = date;
        } else {
            this.LastPaymentDate = this.PaymentDate;
            this.PaymentDate = date;
        }
        //System.out.println("ok!");
    }

    /**
     * Returns the ListTransactions of the organization.
     * @return {@link ListTransactions}object of the respective organization.
     */
    public ListTransactions getListTransactions() {
        return listTransactions;
    }

    /**
     * Returns the name of the organization
     * @return {@link String} name of the Organization.
     */
    public String getName() { return name; }

    /**
     * Returns the NIF of the organization
     * @return {@link String} NIF of the Organization.
     */
    public String getNif() { return nif; }

    /**
     * Returns the Manager instance of the organization
     * @return {@link Manager}.
     */
    public Manager getManager() { return manager; }

    /**
     * Returns the Collaborator instance of the organization
     * @return {@link Collaborator}.
     */
    public Collaborator getCollaborator() { return this.collaborator; }

    /**
     * Returns the List of tasks by organization.
     * @return List of tasks on an organization in {@link ListTasks} format.
     */
    public ListTasks getListTasks() {
        return listTasks;
    }

    /**
     * Creates a new Manager instance.
     * @param managerName The name of the manager.
     * @param managerEmail The e-mail of the manager.
     * @return New {@link Manager} instance.
     */
    public static Manager newManager(String managerName, String managerEmail) {
        try {
            return new Manager(managerName, managerEmail);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new Collaborator instance.
     * @param collabName The name of the collaborator.
     * @param collabEmail The e-mail of the collaborator.
     * @return New {@link Collaborator} instance.
     */
    public static Collaborator newCollaborator(String collabName, String collabEmail) {
        try{
            return new Collaborator(collabName, collabEmail);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates the hash code for the instance.
     * @return Hashcode for the Organization object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nif );
        return hash;
    }

    /**
     * Compares the instance of this object with another object.
     * @param o {@link Object} Object to be compared to,
     * @return <code>true</code> if this instance and the other object are equal. <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object o) {
        // Inspired in https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o) {
            return true;
        }
        // null check
        if (o == null) {
            return false;
        }
        // type check and cast
        if (getClass() != o.getClass()) {
            return false;
        }
        // field comparison
        Organization obj = (Organization) o;
        return (Objects.equals( nif, obj.nif ));
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.name, this.nif );
    }

    //UC6 - Show Freelancer Performance Statistics
    // <editor-fold defaultstate="collapsed">

    /**
     * This method starts with a loop for each transaction in the list of transactions. For each transaction
     * it gets the freelancer, the freelancerID and the payment received, if the TreeMap received by parameter
     * doesn't have any value associated to the freelancer, it initializes the arraylist. Otherwise it adds
     * the value to the existing freelancer ID of the list.
     *
     * @param mapOrganizationPayment a treeMap containing the freelancers' ID and all the payments associated to them
     * @return a treeMap containing the freelancers' ID and all the payments associated to them
     */
    public TreeMap<String, List<Double>> determinePaymentOrganization(TreeMap<String,List<Double>> mapOrganizationPayment){
        List<Transaction> transactionList = listTransactions.getM_lstTransactions();
        for (Transaction transaction : transactionList) {
            Freelancer freelancer = transaction.getFreelancer();
            String freelancerID=freelancer.getFreelancerID();
            double transactionAmount=transaction.computeAmount();
            if(mapOrganizationPayment.get ( freelancerID )==null){
                mapOrganizationPayment.put ( freelancerID,new ArrayList<> ());
            }
            mapOrganizationPayment.get ( freelancerID ).add ( transactionAmount );
        }
        return mapOrganizationPayment;

    }

    /**
     * This method starts with a loop for each transaction in the list of transactions. For each transaction
     * it gets the freelancer, the freelancerID and the delay received, if the TreeMap received by parameter
     * doesn't have any value associated to the freelancer, it initializes the arraylist. Otherwise it adds
     * the value to the existing freelancer ID of the list.
     *
     * @param mapOrganizationDelay a treeMap containing the freelancers' ID and all the delays associated to them
     * @return a treeMap containing the freelancers' ID and all the delays associated to them
     */
    public TreeMap<String, List<Double>> determineDelayOrganization(TreeMap<String,List<Double>> mapOrganizationDelay){
        List<Transaction> transactionList = listTransactions.getM_lstTransactions();
        for (Transaction transaction : transactionList) {
            Freelancer freelancer = transaction.getFreelancer();
            String freelancerID=freelancer.getFreelancerID();
            double transactionAmount=transaction.getExecution().getDelay();
            if(mapOrganizationDelay.get ( freelancerID )==null){
                mapOrganizationDelay.put ( freelancerID,new ArrayList<> ());
            }
            mapOrganizationDelay.get ( freelancerID ).add ( transactionAmount );
        }
        return mapOrganizationDelay;
    }

    /**
     * Determines the number of tasks in the transaction list.
     * @return  the number of tasks
     */
    public int determineNumberOfTasks(){
        List<Transaction> transactionList = listTransactions.getM_lstTransactions();
        return transactionList.size();
    }
    // </editor-fold>

    /**
     * Determinates the delay of an organization of the current year
     * @param mapOrganizationDelay the TreeMap that stores the delays
     * @return returns the TreeMap with the id of the freelancers and the delays
     */
    public TreeMap<String, List<Double>> determinateDelayOrganizationThatYear(TreeMap<String,List<Double>> mapOrganizationDelay){

         List<Transaction> transactionList = listTransactions.getM_lstTransactions();
         for (Transaction transaction : transactionList) {
             //if (transaction.getExecution().getEndDate().getTime().getYear()==Calendar.getInstance().getTime().getYear()) {
             int executionYear=transaction.getExecution().getEndDate().get (Calendar.YEAR );
             int currentYear=Calendar.getInstance().get ( Calendar.YEAR )-2000;
             if (executionYear==currentYear) {
                Freelancer freelancer = transaction.getFreelancer();
                 String freelancerID = freelancer.getFreelancerID();
                 double transactionAmount = transaction.getExecution().getDelay();
                 if (mapOrganizationDelay.get(freelancerID) == null) {
                     mapOrganizationDelay.put(freelancerID, new ArrayList<>());
                 }
                 mapOrganizationDelay.get(freelancerID).add(transactionAmount);
             }
         }
         return mapOrganizationDelay;
     }


    //UC7
    /*
    Payments Calculation
    */
    /**
     * @return returns a treeMap containing all Organization freelancers' ID and all payments associated to them
     */
     
    
    public TreeMap<String, List<Double>> determinePaymentOrganization() {
        List<Transaction> transactionList = listTransactions.getM_lstTransactions();
        TreeMap<String, List<Double>> paymentTreeMap = new TreeMap<>();
        for (Transaction transaction : transactionList) {
            Freelancer freelancer = transaction.getFreelancer();
            String freelancerID = freelancer.getFreelancerID();
            double transactionAmount = transaction.computeAmount();
            if (paymentTreeMap.get(freelancerID) == null) {
                paymentTreeMap.put(freelancerID, new ArrayList<>());
            }
            paymentTreeMap.get(freelancerID).add(transactionAmount);
        }
        return paymentTreeMap;

    }
    /**
     * Calculates average payment
     * @param paymentTreeMap a treeMap containing all Organization freelancers' ID and all payments associated to them
     * @return returns a treeMap containing the mean of the all Organization freelancers means payment
     */ 
    public TreeMap<String, Double> calculateMeanPayment(TreeMap<String, List<Double>> paymentTreeMap) {
        TreeMap<String, Double> meanPaymentsMap = new TreeMap<>();
        double sum = 0, mean = 0;
        for (Map.Entry<String, List<Double>> entry : paymentTreeMap.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                sum += entry.getValue().get(i);
                mean = sum / entry.getValue().size();
                meanPaymentsMap.put(entry.getKey(), mean);
            }
            sum = 0;
        }
        return meanPaymentsMap;
    }
    /**
     * Calculates payment deviation
     * @param paymentTreeMap a treeMap containing all Organization freelancers ID and all payments associated to them
     * @param meanPaymentsMap a treeMap containing the mean of the all Organization freelancers means payment
     * @return returns a treeMap containing all Organization freelancers' ID and their respective standard deviation
     */
    public TreeMap<String, Double> calculateDeviationPayment(TreeMap<String, List<Double>> paymentTreeMap, TreeMap<String, Double> meanPaymentsMap) {
        TreeMap<String, Double> deviationPaymentsMap = new TreeMap<>();
        double x, mean, deviation, subtraction = 0;
        String freelancerID;
        for (Map.Entry<String, List<Double>> entry : paymentTreeMap.entrySet()) {
            freelancerID = entry.getKey();
            mean = meanPaymentsMap.get(freelancerID);
                for (int i=0; i < entry.getValue().size(); i++) {
                    x = entry.getValue().get(i);
                    subtraction += Math.pow(x - mean, 2);
                }
                deviation = Math.sqrt(subtraction / entry.getValue().size());
                deviationPaymentsMap.put(entry.getKey(), deviation);
                subtraction = 0;
            }
        return deviationPaymentsMap;
        }

    /*
    Delay calculation
    */

    /**
     * Determines Organization delay
     * @return returns a treeMap containing all Organization freelancers ID and all delays associated to them
     */
    public TreeMap<String, List<Double>> determineDelayOrganization() {
        List<Transaction> transactionList = listTransactions.getM_lstTransactions();

        TreeMap<String, List<Double>> delayTreeMap = new TreeMap<>();

        for (Transaction transaction : transactionList) {
            Freelancer freelancer = transaction.getFreelancer();
            String freelancerID = freelancer.getFreelancerID();
            double transactionAmount = transaction.getExecution().getDelay();
            if (delayTreeMap.get(freelancerID) == null) {
                delayTreeMap.put(freelancerID, new ArrayList<>());
            }
            delayTreeMap.get(freelancerID).add(transactionAmount);
        }
        return delayTreeMap;
    }

    /**
     * Calculates average delay
     * @param delayTreeMap a treeMap containing all Organization freelancers ID and all delays associated to them
     * @return returns a treeMap containing the mean of the all Organization freelancers delay deviation 
     */
    public TreeMap<String, Double> calculateMeanDelay(TreeMap<String, List<Double>> delayTreeMap) {
        TreeMap<String, Double> meanDelaysMap = new TreeMap<>();
        double sum = 0, mean = 0;
        for (Map.Entry<String, List<Double>> entry : delayTreeMap.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                sum += entry.getValue().get(i);
                mean = sum / entry.getValue().size();
                meanDelaysMap.put(entry.getKey(), mean);
            }
            sum = 0;
        }
        return meanDelaysMap;

    }

    /**
     * Calculates Deviation Delay
     * @param delayTreeMap a treeMap containing all Organization freelancers ID and all delays associated to them
     * @param meanDelaysMap a treeMap containing the mean of the all Organization freelancers delay deviation 
     * @return returns a deviation delay
     */
     public TreeMap<String, Double> calculateDeviationDelay(TreeMap<String, List<Double>> delayTreeMap, TreeMap<String, Double> meanDelaysMap) {
        TreeMap<String, Double> deviationDelaysMap = new TreeMap<>();
        double x, mean, deviation, subtraction = 0;
        String freelancerID;
        for (Map.Entry<String, List<Double>> entry : delayTreeMap.entrySet()) {
            freelancerID = entry.getKey();
            mean = meanDelaysMap.get(freelancerID);
            for (int i = 0; i < entry.getValue().size(); i++) {
                        x = entry.getValue().get(i);
                        subtraction += Math.pow(x - mean, 2);
                }
                deviation = Math.sqrt(subtraction / entry.getValue().size());
                deviationDelaysMap.put(entry.getKey(), deviation);
                subtraction = 0;
            }
         return deviationDelaysMap;
        }

    /**
     * Calculates average intervals mean
     * It is used for the definition of the intervals for the histogram.
     * @param map a treeMap containing the freelancer ID and a list of delays or payments
     * @return returns overall mean 
     */
    public double determineIntervalsMean(TreeMap<String, List<Double>> map) {
        double sum = 0, mean = 0, counter = 0;
        for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                sum += entry.getValue().get(i);
                counter++;
            }
            mean = sum /counter;
        }

        return mean;
    }

    /**
     * Determines the intervals deviation
     * It is used for the definition of the intervals for the histogram.
     * @param map a treeMap containing the freelancer ID and a list of delays or payments
     * @return returns overall standard deviation
     */
    public double determineIntervalsDeviation(TreeMap<String, List<Double>> map) {
        double x = 0, subtraction = 0;
        int counter = 0;
        for (Map.Entry<String, List<Double>> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                x = entry.getValue().get(i);
                subtraction += Math.pow(x - determineIntervalsMean(map), 2);
                counter++;
            }
        }
        return Math.sqrt(subtraction / counter);
    }

    /**
     * Returns a list of strings representing the payment data
     * @return returns the textual representation of the treeMaps
     */
    public List<String> dataPaymentToString() {
        List<String> meanData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : calculateMeanPayment(determinePaymentOrganization()).entrySet()) {
            String mean = String.format("\nPayment Mean for Freelancer %s: \n\tMean Payment: %.2f", entry.getKey(), entry.getValue());
            meanData.add(mean);
        }
        for (Map.Entry<String, Double> entry : calculateDeviationPayment(determinePaymentOrganization(), calculateMeanPayment(determinePaymentOrganization())).entrySet()) {
            String mean = String.format( "\nPayment Deviation for Freelancer %s: \n\tDeviation Payment: %.2f ", entry.getKey(), entry.getValue());
            meanData.add(mean);
        }
        return meanData;
    }

    /**
     * Returns a list of strings that represent the delay of data
     * @return returns the textual representation of the treeMaps
     */ 
    public List<String> dataDelayToString() {
        List<String> delayData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : calculateMeanDelay(determineDelayOrganization()).entrySet()) {
            String mean = String.format( "\nDelay Mean for Freelancer %s: \n\tMean Delay: %.2f ", entry.getKey(), entry.getValue());
            delayData.add(mean);
        }
        for (Map.Entry<String, Double> entry : calculateDeviationDelay(determineDelayOrganization(), calculateMeanDelay(determineDelayOrganization())).entrySet()) {
            String mean = String.format( "\nDelay Deviation for Freelancer %s: \n\tDeviation Delay: %.2f ", entry.getKey(), entry.getValue());
            delayData.add(mean);
        }
        return delayData;
    }

    /**
     * Returns a list of Strings that represent the organization's stat's data
     * @return
     */
    public List<String> organizationStatsDataToString(){
        List<String> list = new ArrayList<>(getListTransactions().performanceStatisticsDataToString());
        Comparator<String> stringComparator = (o1, o2) -> o1.compareToIgnoreCase(o2);
        list.sort(stringComparator);
        return list;
    }

    /**
     * Returns the payment date
     * @return returns payment date
     */ 
    public Date getPaymentDate(){
        return PaymentDate;
    }


}
