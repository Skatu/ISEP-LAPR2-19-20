/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.pot.model.Constants;
import pt.ipp.isep.dei.lapr2.pot.model.Freelancer;

import pt.ipp.isep.dei.lapr2.pot.model.Platform;
import pt.ipp.isep.dei.lapr2.pot.model.RegistryFreelancers;

/**
 *
 * @author Tiago Alves
 */
public class RegistryFreelancerController {

    private Platform m_oPlatform;
    private RegistryFreelancers m_oRegistryFreelancers;
    private Freelancer m_oFree;

    public RegistryFreelancerController() {
        if (!ApplicationPOT.getInstance().getCurrentSession().isLoggedInWithFunction(Constants.FUNCTION_COLLABORATOR_ORGANIZATION)) {
            throw new IllegalStateException("Non authorized User.");
        }
        m_oPlatform = ApplicationPOT.getInstance().getPlatform();
    }

    public boolean newFreelancer( String freelancerName, String freelancerExpertise,String freelancerEmail, 
            String freelancerNIF, String freelancerIBAN, String freelancerAddress, String freelancerCountry) {

        
        m_oRegistryFreelancers = m_oPlatform.getRegistryFreelancers();
        String freelancerID = m_oRegistryFreelancers.generateID(freelancerName);
        
        m_oFree = m_oRegistryFreelancers.newFreelancer(freelancerID, freelancerName, freelancerExpertise, freelancerEmail,
                freelancerNIF, freelancerIBAN, freelancerAddress, freelancerCountry);

        if (m_oFree == null) {
            return false;
        }
        return m_oRegistryFreelancers.validateFreelancer(m_oFree);
    }

    public boolean registerFreelancer() {
        return m_oRegistryFreelancers.addFreelancer(m_oFree);
    }

    public Freelancer getFreelancer() {
        return m_oFree;

    }
}
