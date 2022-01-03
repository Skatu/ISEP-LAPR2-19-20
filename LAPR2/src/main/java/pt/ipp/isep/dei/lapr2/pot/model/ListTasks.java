package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListTasks implements Serializable {

    private List<Task> listTasks;
    private static final long serialVersionUID = -1791834354940477418L;
    public ListTasks() {
        listTasks =new ArrayList<> ();
    }

    public Task newTask(String id, String briefDescription, double duration, double cost, String category)
    {
        return new Task(id,briefDescription,duration,cost,category);
    }

    public Task getTaskByID(String id) {
        for (Task task : listTasks){
            if (task.getId().equals(id)){
                return task;
            }
        }
        return null;
    }

    public List<Task> getListTasks() {
        return listTasks;
    }

    public boolean registerTask(Task task)
    {
        if (this.validateTask(task))
        {
            return addTask(task);
        }
        return false;
    }

    private boolean addTask(Task task)
    {
        return listTasks.add(task);
    }

    public boolean validateTask(Task task)
    {
        if(task==null) return false;
        boolean bRet = true;

        for (Task task1 : listTasks){
            if (task.getId().equals(task1.getId())){
                bRet=false;
            }
        }
        return bRet;
    }

    /**
     * Method that verifies if the task ID received by parameter is assigned to
     * any existent task.
     * @param taskId task ID
     * @return true if the task exists, otherwise, returns false
     */
    public boolean existsTaskById(String taskId){
        for (Task task : listTasks){
            if (task.getId().equals(taskId)){
                return true;
            }
        }
        return false;
    }

    public static Task newTask(String id, String[] s){
        String taskDescription;
        double taskDuration;
        double taskCost;
        String taskCategory;
        try{
            taskDescription=s[2];
            if( !(Parse.tryParseDouble ( s[3] ) || Parse.tryParseDouble ( s[4] ))){
                return null;
            }
            taskDuration=Double.parseDouble ( s[3] );
            taskCost=Double.parseDouble ( s[4] );
            taskCategory=s[5];
            return new Task ( id,  taskDescription,  taskDuration,  taskCost,  taskCategory);
        }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
            e.getStackTrace ();
            return null;
        }
    }
}
