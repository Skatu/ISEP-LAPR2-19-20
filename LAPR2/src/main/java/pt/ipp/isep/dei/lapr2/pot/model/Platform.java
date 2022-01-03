/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ipp.isep.dei.lapr2.pot.model;

import pt.ipp.isep.dei.lapr2.emailService.EmailToTextFile;
import pt.ipp.isep.dei.lapr2.authorization.AuthorizationFacade;
import pt.ipp.isep.dei.lapr2.pot.controller.SendWarningEmailsTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 * @translated Rafael Moreira <1181055@isep.ipp.pt>
 */
public class Platform {
    private String m_strName;
    private final AuthorizationFacade m_oAuthorization;
    private RegistryFreelancers m_oRegistryFreelancers;
    private RegistryOrganizations m_oRegistryOrganizations;
    private RegistryBill m_oRegistryBill;
    private PasswordGeneratorAlgorithm pwdGeneratorAlgorithm;
    private ExternalEmailService externalEmailService;
    private PaymentTimer m_oPaymentTimer;
    private Timer timerWarningEmail;
    private SendWarningEmailsTask sendWarningEmailsTask;

    public Platform(String strName) {
        if ((strName == null) ||
                (strName.isEmpty()))
            throw new IllegalArgumentException("Name cannot be null or empty.");

        this.m_strName = strName;
        this.m_oAuthorization = new AuthorizationFacade ();
        this.m_oRegistryFreelancers=new RegistryFreelancers ();
        this.m_oRegistryOrganizations=new RegistryOrganizations ();
        this.pwdGeneratorAlgorithm=new PasswordGeneratorAlgorithm ();
        this.externalEmailService=new EmailToTextFile();
        this.sendWarningEmailsTask=new SendWarningEmailsTask (this);
        this.timerWarningEmail=new Timer("TimerWarningEmail");
        this.timerWarningEmail.schedule(sendWarningEmailsTask,new Date(Calendar.getInstance().getTime().getYear(),Calendar.DECEMBER,31));
    }
    public RegistryBill getRegistryBill() { return m_oRegistryBill; }

    public RegistryFreelancers getRegistryFreelancers() {
        return m_oRegistryFreelancers;
    }

    public RegistryOrganizations getRegistryOrganizations() {
        return m_oRegistryOrganizations;
    }

    public AuthorizationFacade getAuthorizationFacade() {
        return this.m_oAuthorization;
    }

    public PasswordGeneratorAlgorithm getPwdGeneratorAlgorithm() {
        return pwdGeneratorAlgorithm;
    }

    public void serializeData(){
        serializeOrganizations ();
        serializeFreelancers ();
        serializeUsers ();
    }

    private void serializeOrganizations(){
        m_oRegistryOrganizations.serializeOrganizations ();
    }

    private void serializeFreelancers(){
        m_oRegistryFreelancers.serializeFreelancers ();
    }

    private void serializeUsers(){
        m_oAuthorization.serializeUsers ();
    }

/*
    public void calculateFreelancersStatistics() {
        for (Freelancer freelancer : this.getRegistryFreelancers().getM_lstFreelancers()) {
            freelancer.getStatistics().setMean(calculateFreelancerMean(freelancer));
            freelancer.getStatistics().setStandardDeviation(calculateFreelancerStandardDeviation(freelancer));
        }
    }

    public double calculateFreelancerMean(Freelancer freelancer){
        double sumDelay=0;
        double sumTasks=0;
        for (Organization organization : this.getRegistryOrganizations().getM_lstOrganizations()) {
            List<Transaction> m_lstTransactions = organization.getListTransactions().getM_lstTransactions();
            for (Transaction transaction : m_lstTransactions) {
                if (transaction.getFreelancer().getFreelancerID().equals(freelancer.getFreelancerID())) {
                    if (transaction.getTask().getExecution().getDelay() > 0) {
                        sumDelay = sumDelay + transaction.getTask().getExecution().getDelay();
                        sumTasks++;
                    }
                }
            }
        }
        return sumDelay/sumTasks;
    }

    public double calculateFreelancerStandardDeviation(Freelancer freelancer) {
        double totalMean=calculateFreelancersTotalMean();
        List<Freelancer> freelancerList = this.getRegistryFreelancers().getM_lstFreelancers();
            return Math.sqrt((freelancer.getStatistics().getMean() - totalMean) / freelancerList.size());
    }

    public double calculateFreelancersTotalMean(){
        double sumMean=0;
        List<Freelancer> freelancerList = this.getRegistryFreelancers().getM_lstFreelancers();
        for (Freelancer freelancer : freelancerList) {
            sumMean = sumMean + freelancer.getStatistics().getMean();
        }
        return sumMean / freelancerList.size();
    }
*/
}
    
    
