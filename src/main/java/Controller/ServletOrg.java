//package Controller;
//
//import Exceptions.EmployeeNotFoundException;
//import Exceptions.FileOperationException;
//import Models.Employeeimp;
//import Repository.implementations.DatabaseRepositoryimpl;
//import ServletImpl.DeleteEmployeeServlet;
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//@WebServlet("/servletOrg")
//public class ServletOrg extends HttpServlet {
//    private DatabaseRepositoryimpl database;
//    private static Logger logger = LoggerFactory.getLogger(DeleteEmployeeServlet.class);
//
//    @Override
//    public void init() throws ServletException {
//        try {
//            database = new DatabaseRepositoryimpl();
//        } catch (Exception e) {
//            logger.error("Error initializing DatabaseRepository", e);
//            throw new ServletException("Error initializing DatabaseRepository", e);
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            List<Employeeimp> emp = database.getAllEmployees();
//            Gson gson = new Gson();
//            String json = gson.toJson(emp);
//            resp.setContentType("application/json");
//            resp.getWriter().write(json);
//
//        } catch (FileOperationException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Cookie[] cookies = req.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("logintoken".equals(cookie.getName())) {
//                    String value = cookie.getValue();
//                    if ("password".equals(value)) {
//                        resp.getWriter().println("Cookie is valid.");
//                        continue;
//                    } else {
//                        resp.getWriter().println("Cookie value is not valid.");
//                        return;
//                    }
//                }
//            }
//        } else {
//            resp.getWriter().println("No cookies found.");
//            return;
//        }
//        Gson gson = new Gson();
//
//        StringBuilder jsonBuilder = new StringBuilder();
//        try (BufferedReader reader = req.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                jsonBuilder.append(line);
//            }
//        }
//
//        String json = jsonBuilder.toString();
//
//        try {
//            // Convert JSON to Employeeimp object
//            Employeeimp employee = gson.fromJson(json, Employeeimp.class);
//
//            // Use the database repository to add the employee
//            database.addEmployee(
//                    employee.getEmpID(),
//                    employee.getEmpName(),
//                    employee.getDepartment().getDeptId(),
//                    employee.getDepartment().getDeptName(),
//                    employee.getAddress().getLocation(),
//                    employee.getAddress().getPin()
//            );
//
//            resp.setStatus(HttpServletResponse.SC_OK);
//            resp.getWriter().write("Employee added successfully.");
//
//        } catch (JsonSyntaxException e) {
//            // Handle JSON parsing errors
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("Invalid JSON format.");
//        } catch (FileOperationException e) {
//            // Handle errors related to file/database operations
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("Error while adding employee.");
//        } catch (Exception e) {
//            // Handle any other unexpected exceptions
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("An unexpected error occurred.");
//        }
//
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Cookie[] cookies = req.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("logintoken".equals(cookie.getName())) {
//                    String value = cookie.getValue();
//                    if ("password".equals(value)) {
//                        resp.getWriter().println("Cookie is valid.");
//                        continue;
//                    } else {
//                        resp.getWriter().println("Cookie value is not valid.");
//                        return;
//                    }
//                }
//            }
//        } else {
//            resp.getWriter().println("No cookies found.");
//            return;
//        }
//        BufferedReader reader = req.getReader();
//        StringBuilder jsonbuilder = new StringBuilder();
//        String line;
//        while((line = reader.readLine())!=null){
//            jsonbuilder.append(line);
//        }
//        //converting to string
//        String json = jsonbuilder.toString();
//        Gson gson =  new Gson();
//        Employeeimp emp;
//        try{
//            emp = gson.fromJson(json,Employeeimp.class);
//        } catch (JsonSyntaxException e) {
//            logger.error("json Exception",e);
//            resp.getWriter().write("Invalid JSON format.");
//            return;
//        }
//
//
//        try{
//            database.updateEmployee(
//                    emp.getEmpID(),
//                    emp.getEmpName(),
//                    emp.getDepartment().getDeptId(),
//                    emp.getDepartment().getDeptName(),
//                    emp.getAddress().getLocation(),
//                    emp.getAddress().getPin()
//            );
//
//            resp.setStatus(HttpServletResponse.SC_OK);
//            resp.getWriter().write("Employee updated successfully.");
//        } catch (FileOperationException e) {
//            logger.error("FileOperation error in servletOrg class"+e);
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("Error while updating employee.");
//        } catch (EmployeeNotFoundException e) {
//            logger.error("EmployeenotFound"+e);
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            resp.getWriter().write("Employee not found.");
//        }catch (Exception e){
//            logger.error("Exception",e);
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("An unexpected error occurred.");
//        }
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Cookie[] cookies = req.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("logintoken".equals(cookie.getName())) {
//                    String value = cookie.getValue();
//                    if ("password".equals(value)) {
//                        resp.getWriter().println("Cookie is valid.");
//                        continue;
//                    } else {
//                        resp.getWriter().println("Cookie value is not valid.");
//                        return;
//                    }
//                }
//            }
//        } else {
//            resp.getWriter().println("No cookies found.");
//            return;
//        }
//        BufferedReader reader = req.getReader();
//        StringBuilder jsonBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            jsonBuilder.append(line);
//        }
//
//        String json = jsonBuilder.toString();
//
//        Gson gson = new Gson();
//        Employeeimp employee;
//
//        try {
//            employee = gson.fromJson(json, Employeeimp.class);
//        } catch (JsonSyntaxException e) {
//            logger.error("JSON syntax error", e);
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("Invalid JSON format.");
//            return;
//        }
//
//        try {
//            database.deleteEmployee(employee.getEmpID());
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            resp.getWriter().write("Employee deleted successfully.");
//        } catch (FileOperationException e) {
//            logger.error("File operation error", e);
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("Error while deleting employee.");
//        } catch (EmployeeNotFoundException e) {
//            logger.error("Employee not found", e);
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            resp.getWriter().write("Employee not found.");
//        } catch (Exception e) {
//            logger.error("Unexpected error", e);
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("An unexpected error occurred.");
//        }
//    }
//
//}
