package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.DatabaseService;
import service.DatabaseServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deletesubtask")
public class DeleteSubtaskServlet extends HttpServlet {
    private DatabaseService dbservices;

    @Override
    public void init() {
        dbservices = new DatabaseServiceImpl();
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");


        // Reading JSON data from request
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String jsonString = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonString);

        int subtaskId = jsonObject.optInt("subtaskId", -1);

        try {
            boolean deleted = dbservices.DeleteSubTask(subtaskId);
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
