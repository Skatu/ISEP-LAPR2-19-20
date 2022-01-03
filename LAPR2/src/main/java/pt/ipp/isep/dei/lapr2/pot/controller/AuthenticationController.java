/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.authorization.model.UserFunction;

/**
 *
 * @author paulomaio
 * @translated Rafael Moreira
 */
public class AuthenticationController
{
    private ApplicationPOT m_oApp;
    
    public AuthenticationController()
    {
        this.m_oApp = ApplicationPOT.getInstance();
    }
    
    public boolean doLogin(String strId, String strPwd)
    {
        return this.m_oApp.doLogin(strId, strPwd);
    }
    
    public UserFunction getUserFunction()
    {
        if (this.m_oApp.getCurrentSession ().isLoggedIn())
        {
            return this.m_oApp.getCurrentSession ().getUserFunction ();
        }
        return null;
    }

    public void doLogout()
    {
        this.m_oApp.doLogout();
    }
}
