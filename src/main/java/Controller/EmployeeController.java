package Controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/Employee")
public class EmployeeController extends HttpServlet {
      final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
     public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Print HTML content
         out.println("<html>" +
                 "<body bgcolor='cyan'>" +
                 "<p>Hi My name is Ram</p>" +
                 "<form action='Employee' method='get'>" +
                 "<p>Option 1 : AddEmployee</p>" +
                 "<p>Option 2 : View Employee</p>" +
                 "<p>Option 3 : View AllEmployees</p>" +
                 "<p>Option 4 : Update Employee</p>" +
                 "<p>Option 5 : Delete Employee</p>" +
                 "Enter option: <input type='text' name='option'>" +
                 "<input type='submit' value='Submit'>" +
                 "</form>" +
                 "</body>" +
                 "</html>");


        String optioninput = req.getParameter("option");
        if(optioninput != null){
            try{
                int n = Integer.parseInt(optioninput);
                switch (n) {
                    case 1:
                        res.sendRedirect("AddEmployee");
                        break;
                    case 2:
                        res.sendRedirect("getEmployeeById");
                        break;
                    case 3:
                        res.sendRedirect("getAllEmployees");
                        break;
                    case 4:
                        res.sendRedirect("updateEmployee");
                        break;
                    case 5:
                        res.sendRedirect("deleteEmployee");
                        break;
                    default:
                        out.println("<p>Invalid Option</p>");
                        break;
                }
            }catch (NumberFormatException e) {
                out.println("<p>Invalid input format. Please enter a number.</p>");
                logger.error("Invalid input format: {}", optioninput, e);
            }
        }

     }

}

