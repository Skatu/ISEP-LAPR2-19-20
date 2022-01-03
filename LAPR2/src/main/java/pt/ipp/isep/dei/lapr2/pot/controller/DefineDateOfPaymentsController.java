/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.controller;

import java.util.Date;
import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.pot.model.Organization;
import pt.ipp.isep.dei.lapr2.pot.model.Platform;
import pt.ipp.isep.dei.lapr2.pot.model.RegistryOrganizations;

/**
 *
 * @author RodrigoRodrigues
 */
public class DefineDateOfPaymentsController {

    private Platform m_oPlatform;
    private RegistryOrganizations m_oRegistryOrganizations;
    private Organization m_oOrganization;
    private ApplicationPOT m_oApp;
    private UserSession m_oUserSession;
    private String m_oEmail;
    
    public void newPaymentTimer(Date PayDayDate) {
        m_oApp = ApplicationPOT.getInstance();
        m_oUserSession = m_oApp.getCurrentSession();
        m_oEmail = m_oUserSession.getUserEmail();
        m_oPlatform = ApplicationPOT.getInstance().getPlatform ();
        m_oRegistryOrganizations = m_oPlatform.getRegistryOrganizations();
        m_oOrganization = m_oRegistryOrganizations.getOrgByManagerEmail(m_oEmail);
        m_oOrganization.RegisterDate(PayDayDate);
    }
}
