package servlets;

import controller.LoginServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import repository.ValidateCokkie;
import service.DatabaseService;
import service.DatabaseServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deletealltasks")
public class DeleteAllServlet extends HttpServlet {
    private DatabaseService dbservices;
    private ValidateCokkie validateCokkie;
    private LoginServlet loginServlet;

    @Override
    public void init() {
        dbservices = new DatabaseServiceImpl();
        validateCokkie = new ValidateCokkie();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = req.getSession(false);
            int userid = (int) session.getAttribute("USER_ID");
            boolean deleted = dbservices.DeleteAll(userid);

            if (deleted) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Task deleted successfully");
                try (PrintWriter out = res.getWriter()) {
                    out.print(jsonResponse.toString());
                    out.flush();
                }
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JSONObject errorResponse = new JSONObject();
                errorResponse.put("status", "error");
                errorResponse.put("message", "Task not found");
                try (PrintWriter out = res.getWriter()) {
                    out.print(errorResponse.toString());
                    out.flush();
                }
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Database error");
            try (PrintWriter out = res.getWriter()) {
                out.print(errorResponse.toString());
                out.flush();
            }
        }

    }
}
