package servlets;

import jakarta.servlet.ServletConfig;
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

@WebServlet("/updatesubtask")
public class EditSubtaskServlet extends HttpServlet {
    DatabaseService dbservice;

    @Override
    public void init(ServletConfig config) throws ServletException {
        dbservice = new DatabaseServiceImpl();
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");


        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject(sb.toString());
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Invalid JSON format");
            try (PrintWriter out = res.getWriter()) {
                out.print(errorResponse.toString());
                out.flush();
            }
            return;
        }

        int subtaskId = jsonRequest.getInt("subtaskId");
        JSONObject taskData = jsonRequest.getJSONObject("subtask");

        String task = taskData.getString("subtask");
        String priority = taskData.getString("priority");
        String dateTime = taskData.getString("dateTime");


        boolean updated;
        try {
            updated = dbservice.UpdateSubTask(subtaskId,task,priority,dateTime);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Database error");
            try (PrintWriter out = res.getWriter()) {
                out.print(errorResponse.toString());
                out.flush();
            }
            return;
        }

        JSONObject jsonResponse = new JSONObject();
        if (updated) {
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Edited Task Successfully");
        } else {
            jsonResponse.put("status", "failure");
            jsonResponse.put("message", "Task update failed or task not found");
        }

        try (PrintWriter out = res.getWriter()) {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
