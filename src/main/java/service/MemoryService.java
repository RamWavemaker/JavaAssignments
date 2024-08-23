package service;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employee;
import Models.Employeeimp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface MemoryService {
    Employeeimp getEmployeeById(int empID) throws FileNotFoundException, FileOperationException;

    void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws FileOperationException;

    ArrayList<Employee> getAllEmployees() throws FileNotFoundException, FileOperationException;

    void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws IOException, EmployeeNotFoundException, FileOperationException;

    void deleteEmployee(int empID) throws FileNotFoundException, FileOperationException, EmployeeNotFoundException;
}
