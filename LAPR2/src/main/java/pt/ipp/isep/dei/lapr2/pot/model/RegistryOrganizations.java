package pt.ipp.isep.dei.lapr2.pot.model;

import org.apache.commons.math3.distribution.NormalDistribution;
//import pt.ipp.isep.dei.lapr2.EmailService.EmailToTextFile;
import pt.ipp.isep.dei.lapr2.authorization.AuthorizationFacade;
import pt.ipp.isep.dei.lapr2.emailService.EmailToTextFile;

import java.io.*;
import java.util.*;

public class RegistryOrganizations implements Serializable {

    private static final long serialVersionUID = -5518190504775396128L;
    private List<Organization> m_lstOrganizations;

    public RegistryOrganizations() {

        m_lstOrganizations = new ArrayList<>();

        if(ifOrganizationsFileExists ()) deserializeOrganizations ();
        else m_lstOrganizations=new ArrayList<> ();
    }

    public Organization getOrgByManagerEmail(String email) {
        for (Organization organization : m_lstOrganizations) {
            if (organization.getManager ().getEmail().equalsIgnoreCase (email)) {
                return organization;
            }
        }
        return null;
    }

    public Organization getOrganizationByCollaboratorEmail(String email) {
        for (Organization organization : m_lstOrganizations) {
            if (organization.getCollaborator().getEmail().equalsIgnoreCase (email)) {
                return organization;
            }
        }
        return null;
    }

    public Organization newOrganization(String orgName, String orgNIF, String managerName, String managerEmail,
                                        String collabName, String collabEmail) {
        try {
            Manager manager = Organization.newManager(managerName, managerEmail);
            Collaborator collab = Organization.newCollaborator(collabName, collabEmail);
            return new Organization(orgName, orgNIF, manager, collab);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Organization> getOrganizationsToPay() {
        List<Organization> lstOrgsToPay = new ArrayList<>();
        Date currentDate = new Date();
        for (Organization org: m_lstOrganizations){
            if(org.getPaymentDate().getYear() == currentDate.getYear() &
                    org.getPaymentDate().getMonth() == currentDate.getMonth() &
                    org.getPaymentDate().getDay() == currentDate.getDay() &
                    org.getPaymentDate().getHours() == currentDate.getHours() ){
                lstOrgsToPay.add(org);
            }
        }
        return lstOrgsToPay;
    }

    public boolean managerAndCollaboratorAlreadyExist(String manEmail, String colEmail){
        for(Organization o: m_lstOrganizations){
            if(manEmail.equalsIgnoreCase (o.getManager ().getEmail ())){
                return true;
            }
            if(colEmail.equalsIgnoreCase ( o.getCollaborator ().getEmail () )){
                return true;
            }
        }
        return false;
    }

    public boolean validateOrganization(Organization m_oOrg) {
        for (Organization o : m_lstOrganizations) {
            if (m_oOrg.equals(o))
                return false;
        }
        return true;
    }

    public boolean registerOrganization(Platform m_oPlatform, Organization m_oOrg) {
        if (!validateOrganization(m_oOrg)) return false;
        if (!registerManagerAndCollaboratorAsUsers(m_oPlatform, m_oOrg.getManager(), m_oOrg.getCollaborator()))
            return false;
        return addOrganization(m_oOrg);
    }

    public boolean registerOrganization(Organization m_oOrg){
        if (!validateOrganization ( m_oOrg )) return false;
        return addOrganization ( m_oOrg );
    }

    private boolean ifOrganizationsFileExists(){
        File f=new File ( Constants.BINARY_ORGANIZATIONS );
        return f.exists () && !f.isDirectory ();
    }

    private void deserializeOrganizations(){
        try {
            ObjectInputStream fileIn=new ObjectInputStream (  new FileInputStream ( Constants.BINARY_ORGANIZATIONS ));
            this.m_lstOrganizations=(ArrayList<Organization>)fileIn.readObject ();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace ();
        }
    }

    private boolean addOrganization(Organization m_oOrg) {
        return m_lstOrganizations.add(m_oOrg);
    }

    public void serializeOrganizations(){
        try{
            ObjectOutputStream out=new ObjectOutputStream ( new FileOutputStream ( Constants.BINARY_ORGANIZATIONS ) );
            out.writeObject ( this.m_lstOrganizations );
        }catch (IOException e) {
            e.printStackTrace ();
        }
    }

    private boolean registerManagerAndCollaboratorAsUsers(Platform m_oPlatform, Manager manager, Collaborator collab){

        AuthorizationFacade aut;
        ExternalEmailService eService;

        String managerEmail = manager.getEmail();
        String collabEmail = collab.getEmail();

        PasswordGeneratorAlgorithm alg = m_oPlatform.getPwdGeneratorAlgorithm();

        String managerPwd = alg.generatePassword();
        String collabPwd = alg.generatePassword();

        aut = m_oPlatform.getAuthorizationFacade();
        eService = new EmailToTextFile ();

        if (aut.registerUserWithFunction(managerEmail, managerPwd, Constants.FUNCTION_MANAGER_ORGANIZATION)
                && aut.registerUserWithFunction(collabEmail, collabPwd, Constants.FUNCTION_COLLABORATOR_ORGANIZATION)) {
            eService.sendPassword(managerEmail, managerPwd);
            eService.sendPassword(collabEmail, collabPwd);
            return true;
        }
        return false;
    }

    public List<Organization> getM_lstOrganizations() {
        return m_lstOrganizations;
    }

    //UC6 - Show Freelancer Performance Statistics
    // <editor-fold defaultstate="collapsed">

    /**
     * This method starts with a loop that for each organization registered in the system
     * calls the method determinePaymentOrganization() - in the Organization -, that returns
     * a TreeMap containing all freelancers' ID (that belong to the organization) associated to
     * all payments received by each one.
     *
     * @return a treeMap containing all freelancers' ID and all payments associated to them
     */
    public TreeMap<String, List<Double>> determinePaymentPlatform() {
        TreeMap<String, List<Double>> mapTotalPayments = new TreeMap<>();
        for (Organization o : m_lstOrganizations) {
            o.determinePaymentOrganization(mapTotalPayments);
        }
        return mapTotalPayments;
    }


    /**
     * This method receives by parameter one of the treeMap returned in the previous methods ( a treeMap containing all
     * freelancers' ID and all payments/delays associated to them), making a first loop for each entry of the treeMap
     * received and a second one for each element of the List<Double>. Inside the loop the mean is calculated
     * and added to a new TreeMap<String, Double> associated to the freelancer to whom the mean refers to.
     *
     * @param mapTotal  a treeMap containing all freelancers' ID and all payments/delays associated to them
     * @return a treeMap containing all freelancers' ID and their respective mean
     */
    public TreeMap<String, Double> calculateMean(TreeMap<String, List<Double>> mapTotal) {
        TreeMap<String, Double> mapMean = new TreeMap<>();
        double mean, sum = 0;
        for (Map.Entry<String, List<Double>> entry : mapTotal.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                sum += entry.getValue().get(i);
                mean = sum / entry.getValue().size();
                mapMean.put(entry.getKey(), mean);
            }
            sum = 0;
        }
        return mapMean;

    }

    /**
     * This method receives by parameter the treeMap returned in one of the previous methods ( a treeMap containing all
     * freelancers' ID and all payments/delays associated to them) and the treeMap containing all the mean values.
     * The method performs a first loop for each entry of the treeMap received in which it gets the freelancer
     * ID and calls the mapMean and gets the values associated to the chosen ID, the second loop is for
     * each element of the List<Double> - inside it part of the formula of the deviation is calculated; for each
     * payment/delay the mean is subtracted and the value is squared, increasing the value of the subtraction variable.
     * Once this loop gets all values referring to the freelancer, he goes back to the first loop and finishes
     * the calculus - determining the square root of the division between the subtraction variable and the number
     * of payments/delays associated to the freelancer.
     *
     * @param mapTotal  a treeMap containing all freelancers' ID and all payments/delays associated to them
     * @param mapMean a treeMap containing all freelancers' ID and their respective mean
     * @return a treeMap containing all freelancers' ID and their respective standard deviation
     */
    public TreeMap<String, Double> calculateDeviation(TreeMap<String, List<Double>> mapTotal, TreeMap<String, Double> mapMean) {
        TreeMap<String, Double> mapDeviation = new TreeMap<>();
        double x, mean, deviation, subtraction = 0;
        String freelancerID;
        for (Map.Entry<String, List<Double>> entry : mapTotal.entrySet()) {
            freelancerID = entry.getKey();
            mean = mapMean.get(freelancerID);
            for (int i = 0; i < entry.getValue().size(); i++) {
                x = entry.getValue().get(i);
                subtraction += Math.pow(x - mean, 2);
            }
            deviation = Math.sqrt(subtraction / entry.getValue().size());
            mapDeviation.put(entry.getKey(), deviation);
            subtraction = 0;
        }
        return mapDeviation;
    }


    /**
     * This method starts with a loop that for each organization registered in the system
     * calls the method determinePaymentOrganization() - in the Organization -, that returns
     * a TreeMap containing all freelancers' ID (that belong to the organization) associated to
     * all delays.
     *
     * @return a treeMap containing all freelancers' ID and all delays associated to them
     */
    public TreeMap<String, List<Double>> determineDelayPlatform() {
        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        for (Organization o : m_lstOrganizations) {
            o.determineDelayOrganization(mapTotalDelays);
        }
        return mapTotalDelays;
    }

    /**
     * This method determines the overall mean for the treeMap received by parameter
     * (which could be both the TreeMap returned in determinePaymentPlatform() and
     * determineDelayPlatform()). It is used for the definition of the intervals for
     * the histogram.
     * @param map a treeMap containing the freelancer ID and a list of delays or payments
     * @return overall mean
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
     * This method determines the overall standard deviation for the treeMap received by
     * parameter (which could be both the TreeMap returned in determinePaymentPlatform() and
     * determineDelayPlatform()). It is used for the definition of the intervals for
     * the histogram.
     * @param map a treeMap containing the freelancer ID and a list of delays or payments
     * @return overall standard deviation
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
     * This method determines the probability that the sample mean is higher than 3 hours,
     * using the Library Apache Commons Math. Firstly it calls a method in the Organization t
     * hat determines the number of tasks registered in the system.
     *
     * @return the value of the probability
     */
    public double calculateNormalDistribution(){
        int numberTasks = 0;
        double x=3, deviation=1.5, mean=2;
        for (Organization o : m_lstOrganizations){
            numberTasks += o.determineNumberOfTasks();
        }
        NormalDistribution n = new NormalDistribution(mean,deviation/Math.sqrt(numberTasks));
        return 1-n.cumulativeProbability(x);
    }

    /**
     * Returns the textual representation of both TreeMaps that contain the mean and the standard deviation
     * of the payment
     * @return the textual representation of the treeMaps
     */
    public List<String> dataPaymentToString() {
        List<String> meanData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : calculateMean(determinePaymentPlatform()).entrySet()) {
            String mean = String.format("\nPayment Mean for Freelancer %s: \n\tMean Payment: %.2f", entry.getKey(), entry.getValue());
            meanData.add(mean);
        }
        for (Map.Entry<String, Double> entry : calculateDeviation(determinePaymentPlatform(), calculateMean(determinePaymentPlatform())).entrySet()) {
            String mean = String.format( "\nPayment Deviation for Freelancer %s: \n\tDeviation Payment: %.2f ", entry.getKey(), entry.getValue());
            meanData.add(mean);
        }
        return meanData;
    }

    /**
     * Returns the textual representation of both TreeMaps that contain the mean and the standard deviation
     * of the delay
     * @return the textual representation of the treeMaps
     */
    public List<String> dataDelayToString() {
        List<String> delayData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : calculateMean(determineDelayPlatform()).entrySet()) {
            String mean = String.format( "\nDelay Mean for Freelancer %s: \n\tMean Delay: %.2f ", entry.getKey(), entry.getValue());
            delayData.add(mean);
        }
        for (Map.Entry<String, Double> entry : calculateDeviation(determineDelayPlatform(), calculateMean(determineDelayPlatform())).entrySet()) {
            String mean = String.format( "\nDelay Deviation for Freelancer %s: \n\tDeviation Delay: %.2f ", entry.getKey(), entry.getValue());
            delayData.add(mean);
        }
        return delayData;
    }

    /**
     * Returns the textual representation of several Transaction characteristics.
     * @return textual representation of several Transaction characteristics
     */
    public List<String> performanceStatisticsDataToString(){
        List<String> list = new ArrayList<>();
        for (Organization o : m_lstOrganizations){
            list.addAll(o.getListTransactions().performanceStatisticsDataToString());
        }
        Comparator<String> stringComparator = (o1, o2) -> o1.compareToIgnoreCase(o2);
        list.sort(stringComparator);
        return list;
    }
    // </editor-fold>

    /**
     * Determine the delay of the platform of the current year
     * @return returns the {@link TreeMap} with the id of the freelancers and the delays
     */
    public TreeMap<String, List<Double>> determineDelayPlatformThatYear() {
        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        for (Organization o : m_lstOrganizations) {
            o.determinateDelayOrganizationThatYear(mapTotalDelays);
        }
        return mapTotalDelays;
    }
}

