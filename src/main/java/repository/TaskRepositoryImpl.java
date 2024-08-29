package repository;

import models.Login;
import models.Task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskRepositoryImpl {
    ArrayList<Task> list = new ArrayList<>();
    public void AddTasks(int TaskId,String TaskName,String Priority,String Date){
        Task task = new Task(TaskId,TaskName,Priority,Date);
        list.add(task);
    }

    public ArrayList<Task> getAllTask() { //O(n)
        return new ArrayList<>(list);
    }

    public void deleteuser(int ID) { //O(1)
        Iterator<Task> iterator = list.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getTaskId() == ID) {
                iterator.remove();
                found = true;
                System.out.println("User Deleted");
                break;
            }
        }
        if (!found) {
            System.out.println("User with ID " + ID + " not found.");
        }
    }
}
