package repository;

import com.google.gson.Gson;
import models.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryImpl implements DatabaseRepository{
    public static final Logger logger = LoggerFactory.getLogger(DatabaseRepositoryImpl.class);
    DatabaseConnector db = null;
    Connection conn;
    public DatabaseRepositoryImpl() throws SQLException, ClassNotFoundException {
        db = new DatabaseConnector();
        conn = db.getConnection();
    }


    @Override
    public Boolean AuthenticateUser(String email, String password) {
        String query = "SELECT * FROM LOGIN_CREDENTIALS WHERE EMAIL = ? AND PASSWORD = ?";
        try(PreparedStatement pst = conn.prepareStatement(query)){
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            logger.error("Error while authenticating user", e);
            return false;
        }
    }

    @Override
    public int AddTask(String task, String priority, String datetime,int userid) {
        String query = "INSERT INTO TASK (TASK_NAME, PRIORITY, DATE,USER_ID) VALUES (?, ?, ?,?)";
        try (PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, task);
            pst.setString(2, priority);
            pst.setString(3, datetime);
            pst.setInt(4,userid);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated ID
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in databaserepository",e);
        }
        return 0;
    }

    @Override
    public Boolean DeleteTask(int taskid) {
        String query = "DELETE FROM TASK WHERE ID = ?";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, taskid);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error while deleting task", e);
            return false;
        }
    }

    @Override
    public Boolean updateTask(int taskId, String task, String priority, String dateTime) {
        String query = "UPDATE TASK SET TASK_NAME = ?, PRIORITY = ?, DATE = ? WHERE ID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, task);
            stmt.setString(2, priority);
            stmt.setString(3, dateTime);
            stmt.setInt(4, taskId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean DeleteAll(int userid) {
        String TasksQuery = "DELETE FROM TASK WHERE USER_ID = ?";
        String SubTasksQuery = "DELETE FROM SUB_TASK"; // if needed
        logger.debug("USER ID TO DELETE ALL IS {}",userid);
        try (PreparedStatement taskStmt = conn.prepareStatement(TasksQuery);
             PreparedStatement subtaskStmt = conn.prepareStatement(SubTasksQuery)) {
            taskStmt.setInt(1,userid);
            int tasksRowsAffected = taskStmt.executeUpdate();
            int subTasksRowsAffected = subtaskStmt.executeUpdate();

            // Ensure both operations were successful
            return tasksRowsAffected >= 0 && subTasksRowsAffected >= 0;
        } catch (SQLException e) {
            logger.error("Error executing delete all in dbrepo", e);
            return false;
        }
    }

    @Override
    public int AddSubTask(String subtask, String priority, String datetime, int taskId) throws SQLException {
        String query = "INSERT INTO SUB_TASK (SUBTASK_NAME, PRIORITY, DATE, TASK_ID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, subtask);
            pst.setString(2, priority);
            pst.setString(3, datetime);
            pst.setInt(4, taskId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating subtask failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated ID
                } else {
                    throw new SQLException("Creating subtask failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in AddSubTask", e);
            throw new SQLException("Database error while adding subtask", e);
        }
    }
    public Boolean DeleteSubTask(int subtaskId) {
        String query = "DELETE FROM SUB_TASK WHERE ID = ?";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, subtaskId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.error("Error while deleting task", e);
            return false;
        }
    }

    public Boolean UpdateSubTask(int SubtaskId,String subtaskname, String priority, String datetime){
        String query = "UPDATE SUB_TASK SET SUBTASK_NAME = ?, PRIORITY = ?, DATE = ? WHERE ID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, subtaskname);
            preparedStatement.setString(2, priority);
            preparedStatement.setString(3, datetime);
            preparedStatement.setInt(4, SubtaskId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getTasks(int userid) {
        String query = "SELECT * FROM TASK WHERE USER_ID = ?";
        List<Task> taskList = new ArrayList<>();

        logger.debug("Entered getTasks with userId: {}", userid);

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            // Set the parameter for the PreparedStatement
            preparedStatement.setInt(1, userid);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String taskName = resultSet.getString("TASK_NAME");
                    String priority = resultSet.getString("PRIORITY");
                    Timestamp dateTimestamp = resultSet.getTimestamp("DATE");

                    // Convert Timestamp to a formatted String
                    String date = new java.util.Date(dateTimestamp.getTime()).toString();

                    Task task = new Task(id, taskName, priority, date);
                    taskList.add(task);
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving tasks for user ID: {}", userid, e);
        }

        // Convert the list of tasks to JSON using Gson
        Gson gson = new Gson();
        return gson.toJson(taskList);
    }



    public int getuserid(String email) {
        logger.debug("Entered getUserId method with email: {}", email);

        String query = "SELECT ID FROM LOGIN_CREDENTIALS WHERE EMAIL = ?";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("ID");
                    // Log the retrieved user ID with proper formatting
                    logger.debug("The user ID is: {}", userId);
                    return userId;
                } else {
                    return -1; // Email not found
                }
            }
        } catch (SQLException e) {
            logger.error("Error while retrieving user ID for email: {}", email, e);
            return -1; // Handle SQL exception
        }
    }



}
