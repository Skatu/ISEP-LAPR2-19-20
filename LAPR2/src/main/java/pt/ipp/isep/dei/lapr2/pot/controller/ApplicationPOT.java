/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.authorization.AuthorizationFacade;
import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.pot.model.Constants;
import pt.ipp.isep.dei.lapr2.pot.model.Platform;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author paulomaio
 * @Translated Rafael Moreira
 */
public class ApplicationPOT
{
       
    private final Platform m_oPlatform;
    private final AuthorizationFacade m_oAuthorization;
    
    private ApplicationPOT()
    {
        Properties props = getProperties();
        this.m_oPlatform = new Platform (props.getProperty( Constants.PARAMS_PLATFORM_DESIGNATION ));
        this.m_oAuthorization = this.m_oPlatform.getAuthorizationFacade ();
        bootstrap();
    }
    
    public Platform getPlatform()
    {
        return this.m_oPlatform;
    }
    

    public UserSession getCurrentSession()
    {
        return this.m_oAuthorization.getCurrentSession ();
    }
    
    public boolean doLogin(String strId, String strPwd)
    {
       return this.m_oAuthorization.doLogin(strId,strPwd) != null;
    }
    
    public void doLogout()
    {
        this.m_oAuthorization.doLogout();
    }
    
    private Properties getProperties()
    {
        Properties props = new Properties();

        // Adds properties and values by omission
        props.setProperty( Constants.PARAMS_PLATFORM_DESIGNATION, "Task for Joe");

        // Reads the properties and the defined values
        try
        {
            InputStream in = new FileInputStream( Constants.PARAMS_FILE );
            props.load(in);
            in.close();
        }
        catch(IOException ex)
        {
            ex.getStackTrace ();
        }
        return props;
    }
    
    private void bootstrap()
    {
        this.m_oAuthorization.registerUserFunction ( Constants.FUNCTION_ADMINISTRATOR );
        this.m_oAuthorization.registerUserFunction ( Constants.FUNCTION_MANAGER_ORGANIZATION );
        this.m_oAuthorization.registerUserFunction ( Constants.FUNCTION_COLLABORATOR_ORGANIZATION );
        
        this.m_oAuthorization.registerUserWithFunction ( "adm@t4j.com", "p123456", Constants.FUNCTION_ADMINISTRATOR );
    }
    
    // Inspired in https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static ApplicationPOT singleton = null;
    public static ApplicationPOT getInstance()
    {
        if(singleton == null) 
        {
            synchronized(ApplicationPOT.class)
            { 
                singleton = new ApplicationPOT ();
            }
        }
        return singleton;
    }
}
