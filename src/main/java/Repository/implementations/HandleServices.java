package Repository.implementations;

import Controller.EmployeeController;
import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employee;
import Models.Employeeimp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FileService;
import service.FileServiceimp;
import service.MemoryService;
import service.MemoryServiceimp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleServices {
    private static final Logger logger = LoggerFactory.getLogger(HandleServices.class);
    static MemoryService memoryService = new MemoryServiceimp();
    static FileService file = new FileServiceimp();
    static DatabaseRepositoryimpl db;

    static {
        try {
            db = new DatabaseRepositoryimpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private static void handleInMemoryServices(Scanner sc) throws FileOperationException, IOException, EmployeeNotFoundException {
        System.out.println("In-Memory Services Menu:");
        System.out.println("1: Add Employee");
        System.out.println("2: View Employee");
        System.out.println("3: View All Employees");
        System.out.println("4: Update Employee");
        System.out.println("5: Delete Employee");
        System.out.println("6: Exit");

        System.out.print("Enter Here : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter Employee ID: ");
                int empid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Employee Name: ");
                String empName = sc.nextLine();
                System.out.print("Enter Department ID: ");
                int depid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Department Name: ");
                String depName = sc.nextLine();
                System.out.println("Dont use comma's it takes as separator");
                System.out.print("Enter Address Location: ");
                String location = sc.nextLine();
                System.out.print("Enter Address PIN: ");
                int pin = sc.nextInt();
                memoryService.addEmployee(empid, empName, depid, depName, location, pin);
                break;
            case 2:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                Employeeimp emp = memoryService.getEmployeeById(empid);
                System.out.println(emp != null ? emp : "Employee not found");
                break;
            case 3:
                ArrayList<Employee> employees = memoryService.getAllEmployees();
                employees.forEach(System.out::println);
                System.out.println("All employees from Memory displayed.");
                break;
            case 4:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter New Employee Name: ");
                empName = sc.nextLine();
                System.out.print("Enter New Department ID: ");
                depid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter New Department Name: ");
                depName = sc.nextLine();
                System.out.println("Dont use comma's it takes as separator");
                System.out.print("Enter New Address Location: ");
                location = sc.nextLine();
                System.out.print("Enter New Address PIN: ");
                pin = sc.nextInt();
                memoryService.updateEmployee(empid, empName, depid, depName, location, pin);
                break;
            case 5:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                memoryService.deleteEmployee(empid);
                break;
            case 6:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void gethandleInMemoryServices(Scanner sc) throws FileOperationException, IOException, EmployeeNotFoundException {
        handleInMemoryServices(sc);
    }



    private static void handleFileSystemServices(Scanner sc) throws EmployeeNotFoundException, FileOperationException, IOException {
        System.out.println("File System Services Menu:");
        System.out.println("1: Add Employee to File");
        System.out.println("2: View Employee from File");
        System.out.println("3: View All Employees from File");
        System.out.println("4: Update Employee in File");
        System.out.println("5: Delete Employee from File");
        System.out.println("6: Exit");

        System.out.print("Enter Here : ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                System.out.print("Enter Employee ID: ");
                int empid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Employee Name: ");
                String empName = sc.nextLine();
                System.out.print("Enter Department ID: ");
                int depid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Department Name: ");
                String depName = sc.nextLine();
                System.out.println("Dont use comma's it takes as separator");
                System.out.print("Enter Address Location: ");
                String location = sc.nextLine();
                System.out.print("Enter Address PIN: ");
                int pin = sc.nextInt();
                file.addEmployee(empid, empName, depid, depName, location, pin);
                System.out.println("Employee added to file successfully.");
                break;
            case 2:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                Employeeimp emp = file.getEmployeeById(empid);
                if (emp != null) {
                    System.out.println(emp);
                } else {
                    System.out.println("Employee not found in file.");
                }
                break;
            case 3:
                List<Employeeimp> employees = file.getAllEmployees();
                employees.forEach(System.out::println);
                System.out.println("All employees from file displayed.");
                break;
            case 4:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter New Employee Name: ");
                empName = sc.nextLine();
                System.out.print("Enter New Department ID: ");
                depid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter New Department Name: ");
                depName = sc.nextLine();
                System.out.println("Dont use comma's it takes as separator");
                System.out.print("Enter New Address Location: ");
                location = sc.nextLine();
                System.out.print("Enter New Address PIN: ");
                pin = sc.nextInt();
                file.updateEmployee(empid, empName, depid, depName, location, pin);
                System.out.println("Employee updated in file successfully.");
                break;
            case 5:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                file.deleteEmployee(empid);
                System.out.println("Employee deleted from file successfully.");
                break;
            case 6:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void gethandleFileSystemServices(Scanner sc) throws FileOperationException, IOException, EmployeeNotFoundException {
        handleFileSystemServices(sc);
    }

    private static void handleDatabaseServices(Scanner sc) throws FileOperationException, EmployeeNotFoundException {
        System.out.println("File System Services Menu:");
        System.out.println("1: Add Employee to Database");
        System.out.println("2: View Employee from Database");
        System.out.println("3: View All Employees from Database");
        System.out.println("4: Update Employee in Database");
        System.out.println("5: Delete Employee from Database");
        System.out.println("6: Exit");
        System.out.print("Enter Here : ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter Employee ID: ");
                int empid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Employee Name: ");
                String empName = sc.nextLine();
                System.out.print("Enter Department ID: ");
                int depid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter Department Name: ");
                String depName = sc.nextLine();
                System.out.println("Dont use comma's it takes as separator");
                System.out.print("Enter Address Location: ");
                String location = sc.nextLine();
                System.out.print("Enter Address PIN: ");
                int pin = sc.nextInt();
                db.addEmployee(empid, empName, depid, depName, location, pin);
                System.out.println("Employee added to Database successfully.");
                break;
            case 2:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                Employeeimp emp = db.getEmployeeById(empid);
                if (emp != null) {
                    System.out.println(emp);
                } else {
                    System.out.println("Employee not found in DataBase.");
                }
                break;
            case 3:
                List<Employeeimp> employees = db.getAllEmployees();
                employees.forEach(System.out::println);
                System.out.println("All employees from Database displayed.");
                break;
            case 4:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter New Employee Name: ");
                empName = sc.nextLine();
                System.out.print("Enter New Department ID: ");
                depid = sc.nextInt();
                sc.nextLine(); // Consume newline
                System.out.print("Enter New Department Name: ");
                depName = sc.nextLine();
                System.out.println("Dont use comma's it takes as separator");
                System.out.print("Enter New Address Location: ");
                location = sc.nextLine();
                System.out.print("Enter New Address PIN: ");
                pin = sc.nextInt();
                db.updateEmployee(empid, empName, depid, depName, location, pin);
                System.out.println("Employee updated in Database successfully.");
                break;
            case 5:
                System.out.print("Enter Employee ID: ");
                empid = sc.nextInt();
                db.deleteEmployee(empid);
                System.out.println("Employee deleted from Database successfully.");
                break;
            case 6:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    public static void gethandleDatabaseServices(Scanner sc) throws FileOperationException, IOException, EmployeeNotFoundException {
        handleDatabaseServices(sc);
    }
}
