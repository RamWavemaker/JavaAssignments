package repository;

import java.sql.SQLException;

public interface DatabaseRepository {
    Boolean AuthenticateUser(String email, String password);
    int AddTask(String task,String priority,String datetime,int userid);
    Boolean DeleteTask(int taskid);
    Boolean updateTask(int taskId,String task,String priority,String dateTime);
    Boolean DeleteAll(int userid);
    int AddSubTask(String subtask, String priority, String datetime,int taskid) throws SQLException;
    Boolean DeleteSubTask(int subtaskid);
    public Boolean UpdateSubTask(int SubtaskId,String task, String priority, String datetime);
    public String getTasks(int userid);
    public int getuserid(String email);




}
