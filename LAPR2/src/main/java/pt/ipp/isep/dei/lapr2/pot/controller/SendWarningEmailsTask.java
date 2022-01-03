package pt.ipp.isep.dei.lapr2.pot.controller;

import com.thoughtworks.qdox.model.expression.Or;
import pt.ipp.isep.dei.lapr2.emailService.EmailToTextFile;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.*;

public class SendWarningEmailsTask extends TimerTask {

    /**
     * The platform
     */
    private Platform platform;

    /**
     * Builds an instance of SendWarningEmailsTask
     * @param platform the platform
     */
    public SendWarningEmailsTask(Platform platform){
        this.platform=platform;
    }

    public SendWarningEmailsTask() {
        this.platform=ApplicationPOT.getInstance ().getPlatform ();
    }

    /**
     * Execution of the task
     */
    @Override
    public void run() {
        sendWarningEmails();
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
        List<Organization> listOrg=platform.getRegistryOrganizations ().getM_lstOrganizations();
        for (Organization organization : listOrg){
            for (Transaction transaction : organization.getListTransactions().getM_lstTransactions()){
                if (transaction.getFreelancer().getFreelancerID().equals(freelancerID)){
                    //if (transaction.getExecution().getEndDate().getTime().getYear()== Calendar.getInstance().getTime().getYear()) {
                    int executionYear=transaction.getExecution().getEndDate().get (Calendar.YEAR );
                    int currentYear=Calendar.getInstance().get ( Calendar.YEAR );
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
        double sumFreelancers=0;
        RegistryFreelancers registryFreelancers=this.platform.getRegistryFreelancers();
        for (Freelancer freelancer : registryFreelancers.getM_lstFreelancers()){
            sumTotalPercentages=sumTotalPercentages+calculateFreelancerPercentage(freelancer.getFreelancerID());
            sumFreelancers++;
        }
        return sumTotalPercentages/sumFreelancers;
    }
}
