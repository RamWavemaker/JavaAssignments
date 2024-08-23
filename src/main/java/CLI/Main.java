package CLI;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Repository.implementations.HandleServices;

import java.io.IOException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws FileOperationException, EmployeeNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("                                 Menu                        ");
            System.out.println("                Enter 1 : for Accessing the In-Memory Services                   ");
            System.out.println("                Enter 2 : for Accessing the File System Services                 ");
            System.out.println("                Enter 3 : for Accessing the DataBase System Services             ");
            System.out.println("                Enter 4 : for Exiting          ");
            System.out.print("Enter Here : ");
            int a = sc.nextInt();

            switch (a) {
                case 1:
                    HandleServices.gethandleInMemoryServices(sc);
                    break;
                case 2:
                    HandleServices.gethandleFileSystemServices(sc);
                    break;
                case 3:
                    HandleServices.gethandleDatabaseServices(sc);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        sc.close();
    }

}
