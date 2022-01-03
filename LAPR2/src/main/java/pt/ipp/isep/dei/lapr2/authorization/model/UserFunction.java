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
public class UserFunction implements Serializable {
    private static final long serialVersionUID = -5684006205907637214L;
    private String function;
    private String description;
    
    public UserFunction(String function)
    {
        if ( (function == null) || (function.isEmpty()))
            throw new IllegalArgumentException("Function cannot be null or empty.");

        this.function = function;
        this.description = function;
    }
    
    public UserFunction(String function, String description)
    {
        if ( (function == null) || (function.isEmpty()))
            throw new IllegalArgumentException("Function cannot be null or empty.");

        if ( (description == null) || (description.isEmpty()))
            throw new IllegalArgumentException("Description cannot be null or empty.");
        
        this.function = function;
        this.description = description;
    }
    
    public String getFunction()
    {
        return this.function;
    }
    
    public String getDescription()
    {
        return this.description;
    }

    public boolean hasId(String strId)
    {
        return this.function.equals(strId);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.function );
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
        UserFunction obj = (UserFunction) o;
        return Objects.equals( function, obj.function );
    }
    
    @Override
    public String toString()
    {
        return String.format("%s - %s", this.function, this.description );
    }
}
