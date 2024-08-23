package ServletImpl;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Repository.implementations.DatabaseRepositoryimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateEmployee")
public class UpdateEmployeeServlet extends HttpServlet {
    private DatabaseRepositoryimpl database;
    private static final Logger logger = LoggerFactory.getLogger(UpdateEmployeeServlet.class);

    @Override
    public void init() throws ServletException {
        try {
            database = new DatabaseRepositoryimpl();
        } catch (Exception e) {
            logger.error("Error initializing DatabaseRepository", e);
            throw new ServletException("Error initializing DatabaseRepository", e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("logintoken".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if ("password".equals(value)) {
                        continue;
                    } else {
                        res.getWriter().println("Cookie value is not valid.");
                        return;
                    }
                }
            }
        } else {
            res.getWriter().println("No cookies found.");
            return;
        }
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Display the form for adding an employee
        out.println("<html>" +
                "<head><title>Add Employee</title></head>" +
                "<body bgcolor='cyan'>" +
                "<h1>Add Employee Details</h1>" +
                "<h5>Remember it changes according to EMployeeId matching</h5>" +
                "<form action='updateEmployee' method='post'>" +
                "<div style='padding: 10px;'>" +
                "<label for='employeeid'>Enter Employee ID:</label>" +
                "<input type='text' id='employeeid' name='employeeid' required>" +
                "</div>" +
                "<div style='padding: 10px;'>" +
                "<label for='employeename'>Enter Employee Name:</label>" +
                "<input type='text' id='employeename' name='employeename' required>" +
                "</div>" +
                "<div style='padding: 10px;'>" +
                "<label for='deptid'>Enter Department ID:</label>" +
                "<input type='text' id='deptid' name='deptid' required>" +
                "</div>" +
                "<div style='padding: 10px;'>" +
                "<label for='deptname'>Enter Department Name:</label>" +
                "<input type='text' id='deptname' name='deptname' required>" +
                "</div>" +
                "<div style='padding: 10px;'>" +
                "<label for='location'>Enter Location:</label>" +
                "<input type='text' id='location' name='location' required>" +
                "</div>" +
                "<div style='padding: 10px;'>" +
                "<label for='pincode'>Enter Pincode:</label>" +
                "<input type='text' id='pincode' name='pincode' required>" +
                "</div>" +
                "<div style='padding: 10px;'>" +
                "<input type='submit' value='Submit'>" +
                "</div>" +
                "</form>" +
                "</body>" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String employeeidStr = req.getParameter("employeeid");
        String deptidStr = req.getParameter("deptid");
        String pincodeStr = req.getParameter("pincode");

        try {
            int employeeid = Integer.parseInt(employeeidStr);
            String employeename = req.getParameter("employeename");
            int deptid = Integer.parseInt(deptidStr);
            String deptname = req.getParameter("deptname");
            String location = req.getParameter("location");
            int pincode = Integer.parseInt(pincodeStr);


            database.updateEmployee(employeeid, employeename, deptid, deptname, location, pincode);  //added to database

            logger.info("Employee Updated successfully");

            out.println("<html><body bgcolor='cyan'><h1>Employee Updated Successfully</h1></body></html>");

        } catch (EmployeeNotFoundException e) {
            logger.error("Employee Not found" + e);
        } catch (NumberFormatException e) {
            out.println("<html><body bgcolor='cyan'><p>Invalid input. Please enter valid integers for Employee ID, Department ID, and Pincode.</p></body></html>");
            logger.error("Failed to parse Employee ID: {}", employeeidStr, e);
            logger.error("Failed to parse Dept ID: {}", deptidStr, e);
            logger.error("Failed to parse Pincode: {}", pincodeStr, e);
        } catch (FileOperationException e) {
            out.println("<html><body bgcolor='cyan'><p>An error occurred while adding the employee. Please try again later.</p></body></html>");
            logger.error("FileOperationException occurred", e);
        }
    }
}
