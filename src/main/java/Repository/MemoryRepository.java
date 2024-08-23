package Repository;

import Models.Employee;
import Models.Employeeimp;

import java.util.ArrayList;

public interface MemoryRepository {
    public Employeeimp getEmployeeById(int empID);

    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin);

    public ArrayList<Employee> getAllEmployees();

    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin);

    public void deleteEmployee(int empID);
}
