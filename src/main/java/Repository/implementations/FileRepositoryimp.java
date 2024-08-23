package Repository.implementations;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Addressimp;
import Models.Departmentimp;
import Models.Employeeimp;
import Repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepositoryimp implements FileRepository {
    private static final String FILE_NAME = "C:\\Users\\ramcharank_700056\\IdeaProjects\\Employee_Crud\\src\\test\\resources\\Note";
    private static final Logger logger = LoggerFactory.getLogger(FileRepositoryimp.class);

    public Employeeimp getEmployeeById(int empID) throws FileOperationException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Employeeimp employee = convertToEmployee(line);
                if (employee.getEmpID() == empID) {
                    return employee;
                }
            }
            logger.info("Fetched FileRepository getEmployeeById");

        } catch (FileNotFoundException e) {
            logger.error("File not found: " + FILE_NAME, e);
            throw new FileOperationException("File not found: " + FILE_NAME, e);
        } catch (IOException e) {
            logger.error("I/O Exception error reading file in FileRepoistoryimp.getEmployeeById" + FILE_NAME, e);
            throw new RuntimeException("Error reading file: " + FILE_NAME, e);
        }

        return null;
    }


    public void addEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws FileOperationException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = String.format("%d,%s,%d,%s,%s,%d", empid, empName, depid, depName, location, pin);
            writer.write(line);
            writer.newLine();

        } catch (FileNotFoundException e) {
            throw new FileOperationException("Error while writing to file :" + FILE_NAME, e);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to file: " + FILE_NAME, e);
        }
    }


    public List<Employeeimp> getAllEmployees() throws FileOperationException {
        List<Employeeimp> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                employees.add(convertToEmployee(line));
            }
        } catch (IOException e) {
            throw new FileOperationException("Error while writing to file: " + FILE_NAME, e);
        }
        return employees;
    }

    private Employeeimp convertToEmployee(String line) {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid employee data format");
        }
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int depid = Integer.parseInt(parts[2]);
        String depName = parts[3];
        String location = parts[4];
        int pin = Integer.parseInt(parts[5]);
        return new Employeeimp(id, name, new Departmentimp(depid, depName), new Addressimp(location, pin));
    }


    public void updateEmployee(int empid, String empName, int depid, String depName, String location, int pin) throws EmployeeNotFoundException, FileOperationException {
        List<Employeeimp> employees = getAllEmployees();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            boolean updated = false;
            for (Employeeimp employee : employees) {
                if (employee.getEmpID() == empid) {
                    writer.write(String.format("%d,%s,%d,%s,%s,%d%n", empid, empName, depid, depName, location, pin));
                    updated = true;
                } else {
                    writer.write(employee.toString() + System.lineSeparator());
                }
            }
            if (!updated) {
                throw new EmployeeNotFoundException("Employee with ID" + empid + "not found for update.");
            }
        } catch (IOException e) {
            throw new FileOperationException("Error writing to file: " + FILE_NAME, e);
        }
    }


    public void deleteEmployee(int empID) throws EmployeeNotFoundException, FileOperationException {
        List<Employeeimp> employees = getAllEmployees();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            Boolean deleted = false;
            for (Employeeimp employee : employees) {
                if (employee.getEmpID() != empID) {
                    writer.write(employee.toString() + System.lineSeparator());
                } else {
                    deleted = true;
                }
            }
            if (!deleted) {
                throw new EmployeeNotFoundException("Employee with ID " + empID + " not found for deletion.");
            }
        } catch (IOException e) {
            throw new FileOperationException("Error writing to file: " + FILE_NAME, e);
        }
    }
}

