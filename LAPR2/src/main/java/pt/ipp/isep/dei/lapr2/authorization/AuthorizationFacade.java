/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.authorization;

import pt.ipp.isep.dei.lapr2.authorization.model.UserFunction;
import pt.ipp.isep.dei.lapr2.authorization.model.UserFunctionsRegistry;
import pt.ipp.isep.dei.lapr2.authorization.model.UserRegistry;
import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.authorization.model.User;

import java.io.Serializable;

/**
 *
 * @author paulomaio
 * @translated Rafael Moreira
 */
public class AuthorizationFacade implements Serializable {
    private static final long serialVersionUID = 5855025517100249100L;
    private UserSession m_oSession = null;
    
    private final UserFunctionsRegistry m_oFunctions = new UserFunctionsRegistry ();
    private final UserRegistry m_oUsers = new UserRegistry ();
    
    public boolean registerUserFunction(String strFunction)
    {
        UserFunction oFunction = this.m_oFunctions.newUserFunction (strFunction);
        return this.m_oFunctions.addFunction (oFunction);
    }
    
    public boolean registerUserWithFunction(String strEmail, String strPassword, String strFunction)
    {
        UserFunction oFunction = this.m_oFunctions.searchFunction (strFunction);
        User oUser = this.m_oUsers.newUser (strEmail,strPassword);
        oUser.setFunction (oFunction);
        return this.m_oUsers.addUser (oUser);
    }

    public void serializeUsers(){
        m_oUsers.serializeUserRegistry ();
    }

    public boolean existsUser(String strId)
    {
        return this.m_oUsers.hasUser (strId);
    }

    public UserSession doLogin(String strId, String strPwd)
    {
        User oUser = this.m_oUsers.searchUser (strId);
        if (oUser != null)
        {
            if (oUser.hasPassword(strPwd)){
                this.m_oSession = new UserSession (oUser);
            }
        }
        return getCurrentSession ();
    }
    
    public UserSession getCurrentSession()
    {
        return this.m_oSession;
    }
    
    public void doLogout()
    {
        if (this.m_oSession != null)
            this.m_oSession.doLogout();
        this.m_oSession = null;
    }
}
