package service;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employeeimp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    Employeeimp getEmployeeById(int empID) throws FileNotFoundException, FileOperationException;

    void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws FileOperationException;

    List<Employeeimp> getAllEmployees() throws FileNotFoundException, FileOperationException;

    void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws IOException, EmployeeNotFoundException, FileOperationException;

    void deleteEmployee(int empID) throws FileNotFoundException, FileOperationException, EmployeeNotFoundException;
}
