package Repository.implementations;

import Models.Addressimp;
import Models.Departmentimp;
import Models.Employee;
import Models.Employeeimp;
import Repository.MemoryRepository;

import java.util.*;

public class MemoryRepositoryimp implements MemoryRepository {
    private Map<Integer, Employee> map = new HashMap<Integer, Employee>();
    private Scanner sc = new Scanner(System.in);

    //    int empid = Integer.MIN_VALUE;
    @Override
    public Employeeimp getEmployeeById(int empID) {  //O(1)
        return (Employeeimp) map.get(empID);
    }

    @Override
    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) { //O(1)
        Departmentimp department = new Departmentimp(depid, depName);
        Addressimp address = new Addressimp(location, pin);
        Employeeimp e = new Employeeimp(empid, empName, department, address);
        map.put(empid, e);
        System.out.println("Models.Employee Added");
    }

    @Override
    public ArrayList<Employee> getAllEmployees() { //O(n)
        return new ArrayList<>(map.values());
    }

    @Override
    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) { //O(1)
        if (map.containsKey(empid)) {
            Employeeimp updatedEmployee = new Employeeimp(empid, empName, new Departmentimp(depid, depName), new Addressimp(location, pin));
            map.put(empid, updatedEmployee);
            System.out.println(empid + " is Updated");
        } else {
            System.out.println("Employee not found");
        }
    }

    @Override
    public void deleteEmployee(int empID) { //O(1)
        if (map.remove(empID) != null) {
            System.out.println("Employee with ID " + empID + " is Deleted");
        } else {
            System.out.println("Employee with ID " + empID + " not found");
        }
    }
}

