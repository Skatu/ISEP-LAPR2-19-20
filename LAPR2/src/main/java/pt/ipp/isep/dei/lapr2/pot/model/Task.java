package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;

public class Task implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 8818913096397372084L;
    /**
     * Id of the task
     */
    private String id;
    /**
     * Brief Description of the task
     */
    private String briefDescription;
    /**
     * Duration of the task
     */
    private double duration;
    /**
     * Cost of the task
     */
    private double cost;
    /**
     * Category of the task
     */
    private String category;

    /**
     * Builds an instance of Task
     * @param id the id
     * @param briefDescription the brief description
     * @param duration the duration
     * @param cost the cost
     * @param category the category
     */
    public Task(String id, String briefDescription, double duration, double cost, String category){
        if ( (id == null) || (briefDescription == null) || (duration <= 0) || (cost <= 0) || (category == null) ||
                (id.isEmpty())|| (briefDescription.isEmpty())|| (category.isEmpty()))
            throw new IllegalArgumentException("Any arguments can be empty.");
        this.briefDescription=briefDescription;
        this.category=category;
        this.cost=cost;
        this.duration=duration;
        this.id=id;
    }

    /**
     * Gets the id of the task
     * @return returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the cost of the task
     * @return returns the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the duration of the task
     * @return returns the duration
     */
    public double getDuration() {
        return duration;
    }

    public boolean hasId(String taskId){
        return this.id.equalsIgnoreCase(taskId);
    }

    /**
     * Determines the payment for a freelancer whose expertise is Junior
     * @return the payment for a Junior Freelancer
     */
    public double paymentJuniorFreelancer(){
        return cost*duration;
    }

    @Override
    public String toString()
    {
        String str = String.format("Id: %s \nBrief Description: %s \nDuration: %s \nCost: %s € \nCategory: %s \n", this.id, this.briefDescription, this.duration, this.cost, this.category);
        return str;
    }
}
