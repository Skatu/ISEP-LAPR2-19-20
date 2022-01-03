package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.List;

public class RegisterOrganizationController{

    private Platform m_oPlatform;
    private RegistryOrganizations m_oRegistryOrganizations;
    private Organization m_oOrg;

    public RegisterOrganizationController() {
        if(!ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_ADMINISTRATOR))
            throw new IllegalStateException("Non authorized User.");
        m_oPlatform =ApplicationPOT.getInstance ().getPlatform ();
        m_oRegistryOrganizations=m_oPlatform.getRegistryOrganizations ();
    }

    public boolean newOrganization(String orgName, String orgNIF, String managerName, String managerEmail,
                                        String collabName, String collabEmail){

        if(m_oRegistryOrganizations.managerAndCollaboratorAlreadyExist ( managerEmail, collabEmail)){
            return false;
        }
        m_oOrg=m_oRegistryOrganizations.newOrganization(orgName, orgNIF, managerName, managerEmail, collabName, collabEmail);
        if(m_oOrg==null){
            return false;
        }
        return m_oRegistryOrganizations.validateOrganization(m_oOrg);
    }

    public boolean registerOrganization(){
        return m_oRegistryOrganizations.registerOrganization ( m_oPlatform,m_oOrg );
    }

    public List<Organization> getListOrganizations(){
        return m_oRegistryOrganizations.getM_lstOrganizations ();
    }
}
