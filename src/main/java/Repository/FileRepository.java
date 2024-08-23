package Repository;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employeeimp;

import java.util.List;

public interface FileRepository {
    public Employeeimp getEmployeeById(int empID) throws EmployeeNotFoundException, FileOperationException;

    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws FileOperationException;

    public List<Employeeimp> getAllEmployees() throws FileOperationException;

    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws EmployeeNotFoundException, FileOperationException;

    public void deleteEmployee(int empID) throws EmployeeNotFoundException, FileOperationException;
}
