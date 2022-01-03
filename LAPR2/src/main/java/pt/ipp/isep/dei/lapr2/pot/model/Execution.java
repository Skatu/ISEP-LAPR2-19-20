package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.Calendar;

public class Execution implements Serializable {

    /**
     * The serial version of te class
     */
    private static final long serialVersionUID = -1415137402472224411L;

    /**
     * The finish date
     */
    private Calendar endDate;

    /**
     * The delay
     */
    private double delay;

    /**
     * The execution brief description
     */
    private String briefDescription;

    /**
     * Builds an instance of execution receiving the finish date, the delay and the brief description
     * by parameter.
     *
     * @param endDate               execution finish date
     * @param delay                 execution delay
     * @param briefDescription      execution brief description
     */
    public Execution(Calendar endDate,double delay, String briefDescription){
        if ( (endDate == null) ||  (briefDescription == null) || (briefDescription.isEmpty()))
            throw new IllegalArgumentException("Any arguments can be empty.");
        this.endDate=endDate;
        this.delay=delay;
        this.briefDescription=briefDescription;
    }

    /**
     * Returns the execution delay
     * @return delay
     */
    public double getDelay() {
        return delay;
    }

    /**
     * Returns the finish date
     * @return finish date
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Returns the textual representation of Execution
     * @return Execution characteristics
     */
    @Override
    public String toString()
    {
        return String.format("Finish Date: %s \nDelay: %s hours \nBrief Description: %s \n", Parse.parseDateIntoDDMMYYYYFormat ( this.endDate ), this.delay, this.briefDescription);
    }
}
