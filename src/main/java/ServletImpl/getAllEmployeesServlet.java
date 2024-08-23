package ServletImpl;

import Exceptions.FileOperationException;
import Models.Employeeimp;
import Repository.DatabaseRepository;
import Repository.implementations.DatabaseRepositoryimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = "/getAllEmployees")
public class getAllEmployeesServlet extends HttpServlet {
    private DatabaseRepository dbRepository;
    private static final Logger logger = LoggerFactory.getLogger(getAllEmployeesServlet.class);

    @Override
    public void init() throws ServletException {
        try {
            dbRepository = new DatabaseRepositoryimpl();
        } catch (Exception e) {
            throw new ServletException("Error initializing DatabaseRepository", e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        try{
            List<Employeeimp> employees = dbRepository.getAllEmployees();
            if(employees.isEmpty()){
                out.println("<h1>No employees found</h1>");
            }else{
                out.println("<h1>Employee List</h1>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Name</th><th>Department ID</th><th>Department Name</th><th>Address</th><th>PIN</th></tr>");
                for(Employeeimp emp : employees){
                    out.println("<tr>");
                    out.println("<td>" + emp.getEmpID() + "</td>");
                    out.println("<td>" + emp.getEmpName() + "</td>");
                    out.println("<td>" + emp.getDepartment().getDeptId() + "</td>");
                    out.println("<td>" + emp.getDepartment().getDeptName() + "</td>");
                    out.println("<td>" + emp.getAddress().getLocation() + "</td>");
                    out.println("<td>" + emp.getAddress().getPin() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
        } catch (FileOperationException e) {
            out.println("<h1>Error retrieving employee data</h1>");
            logger.error("Error retrieving employee data"+e);
        }
    }

}
