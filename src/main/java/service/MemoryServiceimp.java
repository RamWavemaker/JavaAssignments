package service;

import Models.Employee;
import Models.Employeeimp;
import Repository.implementations.MemoryRepositoryimp;

import java.util.ArrayList;

public class MemoryServiceimp implements MemoryService {
    public MemoryRepositoryimp memoryrepo;

    public MemoryServiceimp() {
        this.memoryrepo = new MemoryRepositoryimp();
    }

    @Override
    public Employeeimp getEmployeeById(int empID) {
        return memoryrepo.getEmployeeById(empID);
    }

    @Override
    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) {
        memoryrepo.addEmployee(empid, empName, depid, depName, location, pin);
    }

    @Override
    public ArrayList<Employee> getAllEmployees() {
        return memoryrepo.getAllEmployees();
    }

    @Override
    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) {
        memoryrepo.updateEmployee(empid, empName, depid, depName, location, pin);
    }

    @Override
    public void deleteEmployee(int empID) {
        memoryrepo.deleteEmployee(empID);
    }

}
