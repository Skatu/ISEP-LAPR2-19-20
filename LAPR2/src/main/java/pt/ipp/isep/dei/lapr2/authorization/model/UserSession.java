/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.authorization.model;

/**
 *
 * @author paulomaio
 * @translated Rafael Moreira
 */
public class UserSession
{
    private User oUser = null;
    
    private UserSession()
    {
    }
    
    public UserSession(User oUser)
    {
        if (oUser == null)
            throw new IllegalArgumentException("User cannot be null.");
        this.oUser = oUser;
    }
    
    public void doLogout()
    {
        this.oUser = null;
    }
    
    public boolean isLoggedIn()
    {
        return this.oUser != null;
    }
    
    public boolean isLoggedInWithFunction(String strFunction)
    {
        if (isLoggedIn())
        {
            return this.oUser.hasFunction (strFunction);
        }
        return false;
    }

    public String getUserId()
    {
        if (isLoggedIn())
            return this.oUser.getId();
        return null;
    }

    public UserFunction getUserFunction()
    {
        return this.oUser.getFunction ();
    }

    public String getUserEmail(){
        if (isLoggedIn())
            return this.oUser.getEmail();
        return null;
    }
}
