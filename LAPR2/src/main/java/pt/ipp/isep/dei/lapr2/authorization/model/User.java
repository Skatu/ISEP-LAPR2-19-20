/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.authorization.model;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author paulomaio
 * @translated Rafael Moreira
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3382125957262629158L;
    private String strEmail;
    private String strPassword;
    private UserFunction oFunction;
    
    public User(String strEmail, String strPassword) {
        if ( strEmail == null || strEmail.isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty.");
        if(strPassword == null || strPassword.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty.");
        this.strEmail = strEmail;
        this.strPassword = strPassword;
    }
    
    public String getId()
    {
        return this.strEmail;
    }
    
    public String getEmail()
    {
        return this.strEmail;
    }
    
    public boolean hasId(String strId)
    {
        return this.strEmail.equals(strId);
    }
    
    public boolean hasPassword(String strPwd)
    {
        return this.strPassword.equals(strPwd);
    }

    public void setFunction(UserFunction oUserFunction){
        if(oUserFunction==null){
            throw new IllegalArgumentException ("User function cannot be null");
        }
        this.oFunction=oUserFunction;
    }

    public boolean hasFunction(UserFunction oFunction)
    {
        return this.oFunction.equals ( oFunction );
    }
    
    public boolean hasFunction(String strFunction)
    {
        return strFunction.equalsIgnoreCase ( this.oFunction.getFunction () );
    }
    
    public UserFunction getFunction(){
        return this.oFunction;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.strEmail );
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        // Inspired in https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        // field comparison
        User obj = (User) o;
        return Objects.equals( strEmail, obj.strEmail );
    }
    
    @Override
    public String toString()
    {
        return String.format("%s - %s", this.strEmail, this.oFunction );
    }
}
