package service;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employeeimp;
import Repository.implementations.FileRepositoryimp;

import java.util.List;

public class FileServiceimp implements FileService {
    public FileRepositoryimp fileSystem;

    public FileServiceimp() {
        this.fileSystem = new FileRepositoryimp();
    }

    @Override
    public Employeeimp getEmployeeById(int empID) throws FileOperationException {
        return fileSystem.getEmployeeById(empID);
    }

    @Override
    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws FileOperationException {
        fileSystem.addEmployee(empid, empName, depid, depName, location, pin);
    }

    @Override
    public List<Employeeimp> getAllEmployees() throws FileOperationException {
        return fileSystem.getAllEmployees();
    }

    @Override
    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws EmployeeNotFoundException, FileOperationException {
        fileSystem.updateEmployee(empid, empName, depid, depName, location, pin);
    }

    @Override
    public void deleteEmployee(int empID) throws FileOperationException, EmployeeNotFoundException {
        fileSystem.deleteEmployee(empID);
    }

}
