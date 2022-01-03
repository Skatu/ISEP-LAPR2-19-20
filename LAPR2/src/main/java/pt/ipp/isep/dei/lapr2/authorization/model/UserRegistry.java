/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.authorization.model;

import pt.ipp.isep.dei.lapr2.pot.model.Constants;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author paulomaio
 * @translated Rafael Moreira
 *
 */
public class UserRegistry implements Serializable {
    private static final long serialVersionUID = -8574130480401557369L;
    private Set<User> m_lstUsers;

    public UserRegistry() {
        if(userFileExists ()) deserializeUserRegistry ();
        else m_lstUsers = new HashSet<>();
    }

    public User newUser(String strEmail, String strPassword)
    {
        return new User (strEmail,strPassword);
    }
    
    public boolean addUser(User oUser)
    {
        if (oUser != null)
            if( this.m_lstUsers.add(oUser)){
                return true;
            }
        return false;
    }
    
    public boolean removeUser(User oUser)
    {
        if (oUser != null)
            return this.m_lstUsers.remove(oUser);
        return false;
    }
    
    public User searchUser(String strId)
    {
        for(User oUser: this.m_lstUsers)
        {
            if(oUser.hasId(strId))
                return oUser;
        }
        return null;
    }
    
    public boolean hasUser(String strId)
    {
        User oUser = searchUser (strId);
        if (oUser != null)
            return this.m_lstUsers.contains(oUser);
        return false;
    }
    
    public boolean hasUser(User oUser)
    {
        return this.m_lstUsers.contains(oUser);
    }

    private boolean userFileExists(){
        return new File ( Constants.BINARY_USER_REGISTRY ).exists ();
    }

    private boolean deserializeUserRegistry(){
        try {
            ObjectInputStream in=new ObjectInputStream ( new FileInputStream ( Constants.BINARY_USER_REGISTRY ) );
            this.m_lstUsers=(HashSet<User> )in.readObject ();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace ();
            return false;
        }
    }

    public boolean serializeUserRegistry(){
        try {
            ObjectOutputStream out= new ObjectOutputStream ( new FileOutputStream ( Constants.BINARY_USER_REGISTRY ) );
            out.writeObject ( this.m_lstUsers );
            out.close ();
            return true;
        } catch (IOException e) {
            e.printStackTrace ();
            return false;
        }
    }
}
