package ServletImpl;

import Exceptions.EmployeeNotFoundException;
import Exceptions.FileOperationException;
import Models.Employeeimp;
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

@WebServlet(urlPatterns = "/deleteEmployee")
public class DeleteEmployeeServlet extends HttpServlet {
    private DatabaseRepositoryimpl database;
    private static Logger logger = LoggerFactory.getLogger(DeleteEmployeeServlet.class);

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
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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
        PrintWriter out = res.getWriter();
        out.println("<html>" +
                "<head><title>Get Employee</title></head>" +
                "<body>" +
                "<form action='deleteEmployee' method='post'>" +
                "<label for='employeeid'>Enter EmployeeId : </label>" +
                "<input type='text' name='employeeid' required>" +
                "<input type='submit' value='submit'>" +
                "</form>" +
                "</body>" +
                "</html>");
    }

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        String employeeidstr = req.getParameter("employeeid");
        try{
            int employeeid = Integer.parseInt(employeeidstr);
            database.deleteEmployee(employeeid);

            out.println("<html>" +
                    "<head><title>Employee Deleted Successfully</title></head>" +
                    "<body>" +
                    "<h1>Employee Deleted Successfully</h1>" +
                    "</body>" +
                    "</html>");
        } catch (NumberFormatException e) {
            logger.error("Not in NUmber Format"+e);
        } catch (FileOperationException | EmployeeNotFoundException e) {
            logger.error("Runtime Exception "+e);
        }
    }

}
