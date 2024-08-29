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
import java.sql.SQLException;

import static repository.DatabaseRepositoryImpl.logger;

@WebServlet("/addsubtask")
public class AddSubtaskServlet extends HttpServlet {
    private DatabaseService dbservices;

    @Override
    public void init() throws ServletException {
        dbservices = new DatabaseServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        String jsonString = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonString);

        String subtaskName = jsonObject.getString("subtask");
        String priority = jsonObject.getString("priority");
        String dateTime = jsonObject.getString("dateTime");
        int taskId = jsonObject.getInt("taskId");

        try {
            int subtaskId = dbservices.AddSubTask(subtaskName, priority, dateTime, taskId);
            JSONObject responseJson = new JSONObject();
            responseJson.put("subtaskId", subtaskId);
            res.getWriter().write(responseJson.toString());
        } catch (Exception e) {
            logger.error("",e); // Log the stack trace
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "Unexpected error while adding subtask");
            res.getWriter().write(errorJson.toString());
        }
    }
}
