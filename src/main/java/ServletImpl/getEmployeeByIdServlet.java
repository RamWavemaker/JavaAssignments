package ServletImpl;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employeeimp;
import Repository.implementations.DatabaseRepositoryimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getEmployeeById")
public class getEmployeeByIdServlet extends HttpServlet {
    private DatabaseRepositoryimpl database;
    private static final Logger logger = LoggerFactory.getLogger(getEmployeeByIdServlet.class);

    @Override
    public void init() throws ServletException {
        try {
            database = new DatabaseRepositoryimpl();
        } catch (Exception e) {
            logger.error("Error initializing DatabaseRepository", e);
            throw new ServletException("Error initializing DatabaseRepository", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html>" +
                "<head><title>Get Employee</title></head>" +
                "<body>" +
                "<form action='getEmployeeById' method='post'>" +
                "<label for='employeeid'>Enter EmployeeId : </label>" +
                "<input type='text' name='employeeid' required>" +
                "<input type='submit' value='Submit'>" +
                "</form>" +
                "</body>" +
                "</html>");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String employeeidstr = req.getParameter("employeeid");

        try {
            int employeeid = Integer.parseInt(employeeidstr);
            Employeeimp emp = database.getEmployeeById(employeeid);

            if (emp == null) {
                // if Employee is not found
                out.println("<html>" +
                        "<head><title>Employee Not Found</title></head>" +
                        "<body>" +
                        "<h1>Employee Not Found</h1>" +
                        "<p>No employee found with ID: " + employeeid + "</p>" +
                        "</body>" +
                        "</html>");
            } else {
                //if employeeid is found
                out.println("<html>" +
                        "<head><title>Employee Details</title></head>" +
                        "<body>" +
                        "<h1>Employee Details</h1>" +
                        "<p>Employee ID: " + emp.getEmpID() + "</p>" +
                        "<p>Employee Name: " + emp.getEmpName() + "</p>" +
                        "<p>Department ID: " + emp.getDepartment().getDeptId() + "</p>" +
                        "<p>Department Name: " + emp.getDepartment().getDeptName() + "</p>" +
                        "<p>Location: " + emp.getAddress().getLocation() + "</p>" +
                        "<p>Pincode: " + emp.getAddress().getPin() + "</p>" +
                        "</body>" +
                        "</html>");
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid number format for employee ID: {}", employeeidstr, e);
            out.println("<html>" +
                    "<head><title>Invalid Input</title></head>" +
                    "<body>" +
                    "<h1>Invalid Input</h1>" +
                    "<p>Please enter a valid integer for Employee ID.</p>" +
                    "</body>" +
                    "</html>");
        } catch (FileOperationException e) {
            logger.error("Error occurred during file operation", e);
            out.println("<html>" +
                    "<head><title>Server Error</title></head>" +
                    "<body>" +
                    "<h1>Server Error</h1>" +
                    "<p>An error occurred while retrieving employee data. Please try again later.</p>" +
                    "</body>" +
                    "</html>");
        } catch (EmployeeNotFoundException e) {
            logger.error("Employee Not found",e);
            out.println("<html>" +
                    "<head><title>Employee Not Found</title></head>" +
                    "<body>" +
                    "<h1>Employee Not found in database</h1>" +
                    "</body>" +
                    "</html>");
        }
    }
}
