/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.model;

/**
 * Stores user functions and directories for file writing and file reading.
 * @author paulomaio
 * @translated Rafael Moreira
 */
public class Constants
{
    //Functions
    /**
        Administrator function.
     */
    public static final String FUNCTION_ADMINISTRATOR = "ADMINISTRATOR";
    /**
     Collaborator of an organization function.
     */
    public static final String FUNCTION_COLLABORATOR_ORGANIZATION = "COLLABORATOR_ORGANIZATION";
    /**
     Manager of an organization function.
     */
    public static final String FUNCTION_MANAGER_ORGANIZATION = "MANAGER_ORGANIZATION";

    //Params files
    /**
     * Config file directory.
     */
    public static final String PARAMS_FILE = "config.properties";
    /**
     * Params file directory.
     */
    public static final String PARAMS_PLATFORM_DESIGNATION = "Platform.name";

    //Export and import binary files
    /**
     * The basepath for reading and storing binary files.
     */
    private static final String BINARY_BASEPATH ="binaries\\";
    /**
     * Directory for the organizations binary file.
     */
    public static final String BINARY_ORGANIZATIONS = BINARY_BASEPATH +"organizations.dat";
    /**
     * Directory for the freelancers binary file.
     */
    public static final String BINARY_FREELANCERS = BINARY_BASEPATH +"freelancers.dat";
    /**
     * Directory for the users binary file.
     */
    public static final String BINARY_USER_REGISTRY = BINARY_BASEPATH +"users.dat";
    /**
     * The basepath for storing text files.
     */
    //Write text files
    public static final String TEXT_FILE_BASEPATH ="TextFiles\\";
}
