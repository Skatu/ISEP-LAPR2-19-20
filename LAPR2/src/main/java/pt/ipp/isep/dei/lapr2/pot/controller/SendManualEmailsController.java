package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.emailService.EmailToTextFile;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SendManualEmailsController {

    /**
     * The platform
     */
    private Platform platform;

    /**
     * Builds an instance of SendManualEmailsController
     */
    public SendManualEmailsController(){
        if (!ApplicationPOT.getInstance().getCurrentSession().isLoggedInWithFunction(Constants.FUNCTION_ADMINISTRATOR)) {
            throw new IllegalStateException("Non authorized User.");
        }
        this.platform=ApplicationPOT.getInstance().getPlatform();
    }

    /**
     * Sets the platform
     * @param platform the platform
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Sends the warning emails for all the freelancers that needs
     */
    public void sendWarningEmails(){
        ExternalEmailService eService;
        eService=new EmailToTextFile();
        /*platform.calculateFreelancersStatistics();
        double overallPercentage=calculateOverallPercentage();
        for (Freelancer freelancer : platform.getRegistryFreelancers().getM_lstFreelancers()){
            if (freelancer.getStatistics().getMean()>3 && calculateFreelancerPercentage(freelancer)>overallPercentage){
                eService.sendEmail(freelancer.getFreelancerEmail());
            }
        }*/
        RegistryOrganizations registryOrganizations=platform.getRegistryOrganizations();
        TreeMap<String, List<Double>> mapDelayPlatform=registryOrganizations.determineDelayPlatformThatYear();
        TreeMap<String, Double> mapFreelancersDelay=registryOrganizations.calculateMean(mapDelayPlatform);
        double overallPercentage=calculateOverallPercentage();
        for (Map.Entry<String, Double> entry : mapFreelancersDelay.entrySet()) {
            if (entry.getValue()>0 && calculateFreelancerPercentage(entry.getKey())>overallPercentage){
                Freelancer freelancer=platform.getRegistryFreelancers().getFreelancerByID(entry.getKey());
                eService.sendWarningEmail(freelancer.getFreelancerEmail());
            }
        }
    }

    /**
     * Calculates the percentage of delay the freelancer
     * @param freelancerID freelancers' id
     * @return returns the freelancers' percentage
     */
    public double calculateFreelancerPercentage(String freelancerID){
        double sumTasksWithDelay=0, sumTotalTasks=0;
        for (Organization organization : platform.getRegistryOrganizations().getM_lstOrganizations()){
            for (Transaction transaction : organization.getListTransactions().getM_lstTransactions()){
                if (transaction.getFreelancer().getFreelancerID().equals(freelancerID)){
                    //if (transaction.getExecution().getEndDate().getTime().getYear()== Calendar.getInstance().getTime().getYear()) {
                    int executionYear=transaction.getExecution().getEndDate().get (Calendar.YEAR );
                    int currentYear=Calendar.getInstance().get ( Calendar.YEAR )-2000;
                    if (executionYear==currentYear) {
                        if (transaction.getExecution().getDelay() > 0) {
                                sumTasksWithDelay++;
                        }
                            sumTotalTasks++;
                    }
                }
            }
        }
        return (sumTasksWithDelay/sumTotalTasks)*100;
    }

    /**
     * Calculates the overall percentage of delay
     * @return returns the overall percentage
     */
    public double calculateOverallPercentage(){
        double sumTotalPercentages=0;
        for (Freelancer freelancer : platform.getRegistryFreelancers().getM_lstFreelancers()){
            sumTotalPercentages=sumTotalPercentages+(calculateFreelancerPercentage(freelancer.getFreelancerID())/100);
        }
        return (sumTotalPercentages/platform.getRegistryFreelancers().getM_lstFreelancers().size())*100;
    }
}
