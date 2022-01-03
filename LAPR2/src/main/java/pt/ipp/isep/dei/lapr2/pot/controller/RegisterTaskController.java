package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.pot.model.*;
import pt.ipp.isep.dei.lapr2.pot.ui.utils.Utils;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

public class RegisterTaskController {

    /**
     * The platform
     */
    private Platform platform;

    /**
     * The organization
     */
    private Organization organization;

    /**
     * The task
     */
    private Task task;

    /**
     * The list of tasks
     */
    private ListTasks listTasks;

    /**
     * Builds an instance of RegisterTaskController
     */
    public RegisterTaskController(){
        if(!ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction( Constants.FUNCTION_COLLABORATOR_ORGANIZATION))
            throw new IllegalStateException("User not authorized!");
        this.platform= ApplicationPOT.getInstance().getPlatform ();
        this.organization=platform.getRegistryOrganizations().getOrganizationByCollaboratorEmail(ApplicationPOT.getInstance().getCurrentSession ().getUserEmail ());
        this.listTasks=organization.getListTasks();
    }

    /**
     * Gets the list of tasks
     * @return returns the list of tasks
     */
    public ListTasks getListTasks() {
        return listTasks;
    }

    /**
     * Gets the platform
     * @return returns the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the organization
     * @return returns the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    public String getTaskString()
    {
        return task.toString();
    }

    /**
     * Method that verifies if the task created is valid.
     * @param id id of the task
     * @param briefDescription brief description of the task
     * @param duration duration of the task
     * @param cost  cost of the task
     * @param category category of the task
     * @return returns true or false
     */
    public boolean newTask(String id, String briefDescription, double duration, double cost, String category)
    {
        try
        {
            this.task = this.organization.getListTasks().newTask(id,briefDescription,duration,cost,category);
            return this.organization.getListTasks().validateTask(this.task);
        }
        catch(RuntimeException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            this.task = null;
            return false;
        }
    }

    /**
     * Verifies if the task is registered
     * @return returns true or false
     */
    public boolean registerTask()
    {
        return this.organization.getListTasks().registerTask(this.task);
    }

    private Calendar parseDate(String date) {
        Calendar cal=Calendar.getInstance ();
        try {
            String[] s = date.split ( "-" );
            int year, month, day;
            if (tryParseInt ( s[0] ) && tryParseInt ( s[1] ) && tryParseInt ( s[2] )) {
                year = Integer.parseInt ( s[0] );
                month = Integer.parseInt ( s[1] );
                day = Integer.parseInt ( s[2] );
                cal.set ( year, month - 1, day  );
                return cal;
            }
        } catch (PatternSyntaxException e) {
            e.printStackTrace ();
        }
        return null;
    }

    //Inspired in https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
    private boolean tryParseInt(String num){
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
