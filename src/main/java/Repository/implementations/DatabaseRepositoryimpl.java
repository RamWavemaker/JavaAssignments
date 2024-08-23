package Repository.implementations;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employeeimp;
import Repository.DatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepositoryimpl implements DatabaseRepository {
    private static Logger logger = LoggerFactory.getLogger(DatabaseRepositoryimpl.class);
    DatabaseConnector db = null;
    Connection conn;

    public DatabaseRepositoryimpl() throws SQLException, ClassNotFoundException {
        db = new DatabaseConnector();
        conn = db.getConnection();
    }



    @Override
    public Employeeimp getEmployeeById(int empID) throws EmployeeNotFoundException, FileOperationException {
        String query = "SELECT * FROM employees WHERE EMPLOYEEID = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, empID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Employeeimp(
                            rs.getInt("EMPLOYEEID"),
                            rs.getString("EMPLOYEENAME"),
                            rs.getInt("DEPARTMENTID"),
                            rs.getString("DEPARTMENTNAME"),
                            rs.getString("ADDRESSLOCATION"),
                            rs.getInt("ADDRESSPIN")
                    );
                } else {
                    logger.error("Employee not found with ID: "+empID);
                    System.out.println("Employee not found with ID: "+empID);
                    return null;
//                    throw new EmployeeNotFoundException("Employee not found with ID: " + empID);
                }
            }
        } catch (SQLException e) {
            logger.error("Error while retrieving employee", e);
            throw new FileOperationException("Error while retrieving employee", e);
        }
    }

    @Override
    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws FileOperationException {
        // Queries for checking existence and inserting records
        String queryDepartmentCheck = "SELECT COUNT(*) FROM DEPARTMENT WHERE DEPARTMENTID = ?";
        String queryDepartmentInsert = "INSERT INTO DEPARTMENT (DEPARTMENTID, DEPARTMENTNAME) VALUES (?, ?)";
        String queryEmployeeInsert = "INSERT INTO employees (EMPLOYEEID, EMPLOYEENAME, DEPARTMENTID, DEPARTMENTNAME, ADDRESSLOCATION, ADDRESSPIN) VALUES (?, ?, ?, ?, ?, ?)";

            // Check if the department already exists
            boolean departmentExists = false;
            try (PreparedStatement pstCheck = conn.prepareStatement(queryDepartmentCheck)) {
                pstCheck.setInt(1, depid);
                try (ResultSet rs = pstCheck.executeQuery()) {
                    if (rs.next()) {
                        departmentExists = rs.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                logger.error("Error while checking department existence", e);
                throw new FileOperationException("Error while checking department existence", e);
            }

            // insert into DEPARTMENT table if it does not exist
            if (!departmentExists) {
                try (PreparedStatement pstInsert = conn.prepareStatement(queryDepartmentInsert)) {
                    pstInsert.setInt(1, depid);
                    pstInsert.setString(2, depName);
                    pstInsert.executeUpdate();
                } catch (SQLException e) {
                    logger.error("Error while inserting department", e);
                    throw new FileOperationException("Error while adding department", e);
                }
            }

            // Insert into EMPLOYEES table
            try (PreparedStatement pstEmployee = conn.prepareStatement(queryEmployeeInsert)) {
                pstEmployee.setInt(1, empid);
                pstEmployee.setString(2, empName);
                pstEmployee.setInt(3, depid);
                pstEmployee.setString(4, depName);
                pstEmployee.setString(5, location);
                pstEmployee.setInt(6, pin);
                pstEmployee.executeUpdate();
            } catch (SQLException e) {
                logger.error("Error while inserting employee", e);
                throw new FileOperationException("Error while adding employee", e);
            }

    }


    @Override
    public List<Employeeimp> getAllEmployees() throws FileOperationException {
        String query = "SELECT * FROM employees";
        List<Employeeimp> employees = new ArrayList<>();
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employees.add(new Employeeimp(
                        rs.getInt("EMPLOYEEID"),
                        rs.getString("EMPLOYEENAME"),
                        rs.getInt("DEPARTMENTID"),
                        rs.getString("DEPARTMENTNAME"),
                        rs.getString("ADDRESSLOCATION"),
                        rs.getInt("ADDRESSPIN")
                ));
            }
        } catch (SQLException e) {
            throw new FileOperationException("Error while retrieving employees", e);
        }
        return employees;
    }

    @Override
    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws EmployeeNotFoundException, FileOperationException {
        String queryDepartmentCheck = "SELECT COUNT(*) FROM DEPARTMENT WHERE DEPARTMENTID = ?";
        String queryDepartmentInsert = "INSERT INTO DEPARTMENT (DEPARTMENTID, DEPARTMENTNAME) VALUES (?, ?)";
        String query = "UPDATE employees SET EMPLOYEENAME = ?, DEPARTMENTID = ?, DEPARTMENTNAME = ?, ADDRESSLOCATION = ?, ADDRESSPIN = ? WHERE EMPLOYEEID = ?";
            boolean departmentExists = false;
            try (PreparedStatement pstCheck = conn.prepareStatement(queryDepartmentCheck)) {
                pstCheck.setInt(1, depid);
                try (ResultSet rs = pstCheck.executeQuery()) {
                    if (rs.next()) {
                        departmentExists = rs.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                logger.error("Error while checking department existence", e);
                throw new FileOperationException("Error while checking department existence", e);
            }

            // insert into DEPARTMENT table if it does not exist
            if (!departmentExists) {
                try (PreparedStatement pstInsert = conn.prepareStatement(queryDepartmentInsert)) {
                    pstInsert.setInt(1, depid);
                    pstInsert.setString(2, depName);
                    pstInsert.executeUpdate();
                } catch (SQLException e) {
                    logger.error("Error while inserting department", e);
                    throw new FileOperationException("Error while adding department", e);
                }
            }

            try(PreparedStatement pstmt = conn.prepareStatement(query)){
                pstmt.setString(1, empName);
                pstmt.setInt(2, depid);
                pstmt.setString(3, depName);
                pstmt.setString(4, location);
                pstmt.setInt(5, pin);
                pstmt.setInt(6, empid);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new EmployeeNotFoundException("Employee not found with ID: " + empid);
                }
            }catch (SQLException e) {
                logger.error("Error while Updating employee", e);
                throw new FileOperationException("Error while updating employee", e);
            }

    }

    @Override
    public void deleteEmployee(int empID) throws EmployeeNotFoundException, FileOperationException {
        String query = "DELETE FROM employees WHERE EMPLOYEEID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, empID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + empID);
            }
        } catch (SQLException e) {
            throw new FileOperationException("Error while deleting employee", e);
        }
    }

    private boolean AuthenticateUser(String email,String password) throws SQLException {
        String query = "SELECT * FROM CREDENTIALS WHERE EMAIL = ? AND PASSWORD = ?";
        try(PreparedStatement pst = conn.prepareStatement(query)){
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Returns true if a record is found
            }
        } catch (SQLException e) {
            logger.error("Error while authenticating user", e);
            return false;
        }
    }

    public boolean getAuthentication(String email,String password) throws SQLException {
        return AuthenticateUser(email,password);
    }


}
