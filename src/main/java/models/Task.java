package models;

public class Task {
     int TaskId;
     String TaskName;
     String Priority;
     String Date;

    public Task(int taskId, String taskName, String priority, String date) {
        this.TaskId = taskId;
        this.TaskName = taskName;
        this.Priority = priority;
        this.Date = date;
    }
    public  int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "TaskId=" + TaskId +
                ", TaskName='" + TaskName + '\'' +
                ", Priority='" + Priority + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
