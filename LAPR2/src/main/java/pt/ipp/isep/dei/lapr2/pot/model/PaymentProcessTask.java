/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.model;

import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.pot.controller.ApplicationPOT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RodrigoRodrigues
 */
public class PaymentProcessTask extends TimerTask {

    private Platform m_oPlatform;
    private RegistryOrganizations m_oRO;
    private Organization m_oOrg;
    private List<Organization> m_lstOrg;
    private Bill m_oBill;
    private RegistryBill m_oRB;

    public PaymentProcessTask() throws IOException {
        run();
    }


    public void run() {

        m_oRO = m_oPlatform.getRegistryOrganizations();
        m_oRB = m_oPlatform.getRegistryBill();
        m_lstOrg = m_oRO.getOrganizationsToPay();

        for(Organization org: m_lstOrg){
            try {
                m_oBill = org.create(org.getPendingTransactionList());
                m_oRB.addBill(m_oBill);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PaymentProcessTask.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException a) {
            a.printStackTrace();
        }
        }

    }
}
