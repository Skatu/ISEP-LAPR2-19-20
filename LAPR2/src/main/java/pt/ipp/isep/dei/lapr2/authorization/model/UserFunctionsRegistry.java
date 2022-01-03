/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.authorization.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author paulomaio
 */
public class UserFunctionsRegistry implements Serializable {
    private static final long serialVersionUID = -3315141607149129657L;
    private Set<UserFunction> m_lstFunctions = new HashSet<UserFunction>();
    
    public UserFunction newUserFunction(String strFunction)
    {
        return new UserFunction (strFunction);
    }
    
    public UserFunction newUserFunction(String strFunction, String strDescription)
    {
        return new UserFunction (strFunction,strDescription);
    }
    
    public boolean addFunction(UserFunction oFunction)
    {
        if (oFunction != null)
            return this.m_lstFunctions.add(oFunction);
        return false;
    }
    
    public boolean removeFunction(UserFunction oFunction)
    {
        if (oFunction != null)
            return this.m_lstFunctions.remove(oFunction);
        return false;
    }
    
    public UserFunction searchFunction(String strFunction)
    {
        for(UserFunction p: this.m_lstFunctions)
        {
            if(p.hasId(strFunction))
                return p;
        }
        return null;
    }
    
    public boolean hasFunction(String strFunction)
    {
        UserFunction oFunction = searchFunction (strFunction);
        if (oFunction != null)
            return this.m_lstFunctions.contains(oFunction);
        return false;
    }
    
    public boolean hasFunction(UserFunction oFunction)
    {
        return this.m_lstFunctions.contains(oFunction);
    }

}
