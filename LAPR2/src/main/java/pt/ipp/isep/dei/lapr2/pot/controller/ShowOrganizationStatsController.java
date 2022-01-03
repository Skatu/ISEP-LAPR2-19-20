
package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.List;
import java.util.TreeMap;


public class ShowOrganizationStatsController {

    private Platform m_oPlatform;
    private RegistryOrganizations registryOrganizations;
    private RegistryFreelancers registryFreelancers;
    private Organization organization;
    private Transaction transaction;
    private static String freelancerID = null;

    public ShowOrganizationStatsController() {
        if(!ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_COLLABORATOR_ORGANIZATION) &&
                !ApplicationPOT.getInstance().getCurrentSession().isLoggedInWithFunction(Constants.FUNCTION_MANAGER_ORGANIZATION))
            throw new IllegalStateException("Non authorized User.");
        m_oPlatform = ApplicationPOT.getInstance().getPlatform();
        ApplicationPOT app = ApplicationPOT.getInstance();
        UserSession user = app.getCurrentSession();
        String emailUser = user.getUserEmail();
        registryOrganizations = m_oPlatform.getRegistryOrganizations();
        registryFreelancers = m_oPlatform.getRegistryFreelancers();
        if (user.isLoggedInWithFunction(Constants.FUNCTION_MANAGER_ORGANIZATION)){
            organization = registryOrganizations.getOrgByManagerEmail(emailUser);
        }else if (user.isLoggedInWithFunction(Constants.FUNCTION_COLLABORATOR_ORGANIZATION)){
            organization = registryOrganizations.getOrganizationByCollaboratorEmail(emailUser);
        }
    }

    public RegistryOrganizations getRegistryOrganizations() {
        return registryOrganizations;
    }

    public Platform getM_oPlatform() {
        return m_oPlatform;
    }

    public List<String> getFreelancersID(){
        return registryFreelancers.getFreelancersID();
    }

    public List<Freelancer> getFreelancers(){
        return registryFreelancers.getM_lstFreelancers();
    }

    public TreeMap<String, List<Double>> determinePaymentOrganization(){
        return organization.determinePaymentOrganization();
    }
    public TreeMap<String, List<Double>> determineDelayOrganization(){
        return organization.determineDelayOrganization();
    }

    public TreeMap<String, Double> calculateMeanPayment(){
        return organization.calculateMeanPayment(determinePaymentOrganization());
    }

    public TreeMap<String, Double> calculateDeviationPayment(){
        return organization.calculateDeviationPayment(determinePaymentOrganization(), calculateMeanPayment());
    }

    public TreeMap<String, Double> calculateMeanDelay() {
        return organization.calculateMeanDelay(determineDelayOrganization());
    }

    public TreeMap<String, Double> calculateDeviationDelay(){
        return organization.calculateDeviationDelay(determineDelayOrganization(), calculateMeanDelay());
    }

    public double determineIntervalsMean(TreeMap<String, List<Double>> map){
        return organization.determineIntervalsMean(map);
    }

    public double determineIntervalsDeviation(TreeMap<String, List<Double>> map){
        return organization.determineIntervalsDeviation(map);
    }

    public static void setFreelancerID(String freelancerID){
        ShowOrganizationStatsController.freelancerID = freelancerID;
    }

    public static String getFreelancerID(){
        return freelancerID;
    }

    public List<String> dataPaymentToString() {
        return organization.dataPaymentToString();
    }

    public List<String> dataDelayToString() {
        return organization.dataDelayToString();
    }

    public List<String> organizationStatsDataToString(){
        return organization.organizationStatsDataToString();
    }

    public boolean isManager(){
        return ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_MANAGER_ORGANIZATION);
    }

    public boolean isCollaborator(){
        return ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_COLLABORATOR_ORGANIZATION);
    }

}

