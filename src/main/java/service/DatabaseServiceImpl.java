package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.DatabaseRepository;
import repository.DatabaseRepositoryImpl;

import java.sql.SQLException;

public class DatabaseServiceImpl implements DatabaseService {
    private static Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);
    private DatabaseRepository dbrepository;

    // Constructor to initialize the DatabaseRepository
    public DatabaseServiceImpl() {
        try {
            dbrepository = new DatabaseRepositoryImpl();
        } catch (SQLException | ClassNotFoundException e) {
             logger.error("sql exception in DatabaseServiceImpl",e);// Or use a proper logging mechanism
        }
    }

    @Override
    public Boolean AuthenticateUser(String email, String password) {
        if (dbrepository == null) {
            throw new IllegalStateException("DatabaseRepository is not initialized.");
        }
        return dbrepository.AuthenticateUser(email, password);
    }

    @Override
    public int AddTask(String task, String priority, String datetime,int userid) {
        if (dbrepository == null) {
            logger.error("DatabaseRepository is not initialized. error in DatabaseServiceImpl ");
            throw new IllegalStateException("DatabaseRepository is not initialized.");
        }
        return dbrepository.AddTask(task, priority, datetime,userid);
    }

    @Override
    public Boolean DeleteTask(int taskid) {
        return dbrepository.DeleteTask(taskid);
    }

    @Override
    public Boolean updateTask(int taskId, String task, String priority, String dateTime) {
        return dbrepository.updateTask(taskId,task,priority,dateTime);
    }

    @Override
    public Boolean DeleteAll(int userid) {
        return dbrepository.DeleteAll( userid);
    }

    @Override
    public int AddSubTask(String subtask, String priority, String datetime, int taskid) throws SQLException {
        return dbrepository.AddSubTask
                (subtask,priority,datetime,taskid);
    }

    @Override
    public Boolean DeleteSubTask(int subtaskid) {
        return dbrepository.DeleteSubTask(subtaskid);
    }

    @Override
    public Boolean UpdateSubTask(int subtaskid,String task, String priority, String datetime) {
        return dbrepository.UpdateSubTask(subtaskid,task,priority,datetime);
    }

    @Override
    public String getTasks(int userid) {
        return dbrepository.getTasks(userid);
    }

    @Override
    public int getuserid(String email) {
        return dbrepository.getuserid(email);
    }
}
