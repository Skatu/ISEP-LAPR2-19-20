package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.List;
import java.util.TreeMap;


public class ShowFreelancerPerformanceStatisticsController {
    /**
     * The platform
     */
    private Platform m_oPlatform;

    /**
     * The registry of organizations
     */
    private RegistryOrganizations registryOrganizations;

    /**
     * The registry of freelancers
     */
    private RegistryFreelancers registryFreelancers;

    /**
     * The Organization
     */
    private Organization organization;

    /**
     * The Transaction
     */
    private Transaction transaction;

    /**
     * The freelancer ID
     */
    private static String freelancerID = null;

    /**
     * Builds an instance of ShowFreelancerPerformanceStatisticsController()
     */
    public ShowFreelancerPerformanceStatisticsController() {
        if(!ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_ADMINISTRATOR))
            throw new IllegalStateException("Non authorized User.");
        m_oPlatform = ApplicationPOT.getInstance().getPlatform();
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession user = app.getCurrentSession();
        String emailUser = user.getUserEmail();
        registryOrganizations = m_oPlatform.getRegistryOrganizations();
        registryFreelancers = m_oPlatform.getRegistryFreelancers();
        organization = registryOrganizations.getOrganizationByCollaboratorEmail(emailUser);
    }

    /**
     * Returns the RegistryOrganizations
     * @return RegistryOrganizations
     */
    public RegistryOrganizations getRegistryOrganizations() {
        return registryOrganizations;
    }

    /**
     * Returns the FreelancerID
     * @return FreelancerID
     */
    public List<String> getFreelancersID(){
        return registryFreelancers.getFreelancersID();
    }

    /**
     * Returns the list of freelancers
     * @return list of freelancers
     */
    public List<Freelancer> getFreelancers(){
        return registryFreelancers.getM_lstFreelancers();
    }

    /**
     * Calls the method determinePaymentPlatform from the RegistryOrganizations
     *
     * @return a treeMap containing all freelancers ID and all payments related to them
     */
    public TreeMap<String, List<Double>> determinePaymentPlatform(){
        return registryOrganizations.determinePaymentPlatform();
    }

    /**
     * Calls the method determineDelayPlatform from RegistryOrganizations
     * @return a treeMap containing all freelancers ID and all delays related to them
     */
    public TreeMap<String, List<Double>> determineDelayPlatform(){
        return registryOrganizations.determineDelayPlatform();
    }

    /**
     * Calls the method calculateMeanPayment from RegistryOrganizations
     * @return a treeMap containing all freelancers ID and the mean of payments related to them
     */
    public TreeMap<String, Double> calculateMeanPayment(){
        return registryOrganizations.calculateMean(determinePaymentPlatform());
    }

    /**
     * Calls the method calculateDeviationPayment from RegistryOrganizations
     * @return a treeMap containing all freelancers ID and the standard deviation of payments related to them
     */
    public TreeMap<String, Double> calculateDeviationPayment(){
        return registryOrganizations.calculateDeviation(determinePaymentPlatform(), calculateMeanPayment());
    }

    /**
     * Calls the method calculateMeanDelay from RegistryOrganizations
     * @return a treeMap containing all freelancers ID and the mean of delays related to them
     */
    public TreeMap<String, Double> calculateMeanDelay() {
        return registryOrganizations.calculateMean(determineDelayPlatform());
    }

    /**
     * Calls the method calculateDeviationDelay from RegistryOrganizations
     * @return a treeMap containing all freelancers ID and the standard deviation of delays related to them
     */
    public TreeMap<String, Double> calculateDeviationDelay(){
        return registryOrganizations.calculateDeviation(determineDelayPlatform(), calculateMeanDelay());
    }

    /**
     * Calls the method calculateNormalDistribution from RegistryOrganizations
     * @return the value of the probability
     */
    public double calculateNormalDistribution(){
        return registryOrganizations.calculateNormalDistribution();
    }

    /**
     * Calls the method determineIntervalsMean from RegistryOrganizations
     * @param map treeMap containing the freelancer ID and a list of delays or payments
     * @return overall mean
     */
    public double determineIntervalsMean(TreeMap<String, List<Double>> map){
        return registryOrganizations.determineIntervalsMean(map);
    }

    /**
     * Calls the method determineIntervalsDeviation from RegistryOrganizations
     * @param map treeMap containing the freelancer ID and a list of delays or payments
     * @return overall standard deviation
     */
    public double determineIntervalsDeviation(TreeMap<String, List<Double>> map){
        return registryOrganizations.determineIntervalsDeviation(map);
    }

    /**
     * Modifies freelancerID
     * @param freelancerID new value of freelancerID
     */
    public static void setFreelancerID(String freelancerID){
        ShowFreelancerPerformanceStatisticsController.freelancerID = freelancerID;
    }

    /**
     * Returns the freelancer ID
     * @return the freelancer ID
     */
    public static String getFreelancerID(){
        return freelancerID;
    }

    /**
     * Calls the method dataPaymentToString from RegistryOrganizations
     * @return the textual representation of the treeMaps
     */
    public List<String> dataPaymentToString() {
        return registryOrganizations.dataPaymentToString();
    }

    /**
     * Calls the method dataDelayToString from RegistryOrganizations
     * @return the textual representation of the treeMaps
     */
    public List<String> dataDelayToString() {
        return registryOrganizations.dataDelayToString();
    }

    /**
     * Calls the method performanceStatisticsDataToString from RegistryOrganizations
     * @return textual representation of several Transaction characteristics
     */
    public List<String> performanceStatisticsDataToString(){
        return registryOrganizations.performanceStatisticsDataToString();
    }

}
