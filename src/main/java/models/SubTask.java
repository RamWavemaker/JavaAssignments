package models;

public class SubTask {
    int SubTaskId;
    String SubTaskName;
    String Priority;
    String Date;
    int TaskId;

    public SubTask(int SubTaskId,String SubTaskName,String Priority,String Date,int TaskId){
        this.SubTaskId = SubTaskId;
        this.SubTaskName = SubTaskName;
        this.Priority = Priority;
        this.Date = Date;
        this.TaskId = TaskId;
    }

    public int getSubTaskId() {
        return SubTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        SubTaskId = subTaskId;
    }

    public String getSubTaskName() {
        return SubTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        SubTaskName = subTaskName;
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

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "SubTaskId=" + SubTaskId +
                ", SubTaskName='" + SubTaskName + '\'' +
                ", Priority='" + Priority + '\'' +
                ", Date='" + Date + '\'' +
                ", TaskId=" + TaskId +
                '}';
    }
}
