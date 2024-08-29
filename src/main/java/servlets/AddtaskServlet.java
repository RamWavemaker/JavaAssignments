package servlets;

import controller.LoginServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ValidateCokkie;
import service.DatabaseService;
import service.DatabaseServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/addtask")
public class AddtaskServlet extends HttpServlet {
    private DatabaseService dbservices;
    private ValidateCokkie validateCokkie;
    private LoginServlet loginServlet;
    private static Logger logger = LoggerFactory.getLogger(AddtaskServlet.class);

    @Override
    public void init() {
        dbservices = new DatabaseServiceImpl();
        validateCokkie = new ValidateCokkie();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        validateCokkie.validateSessionCookie(req, res);
//
//        if (res.isCommitted()) {
//            String path = req.getContextPath();
//            res.sendRedirect(path);
//        }

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // Read JSON data from request
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        // Convert the stringBuilder content to a JSON object
        String jsonString = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonString);

        // Extract data from the JSON object
        String task = jsonObject.optString("task", "");
        String priority = jsonObject.optString("priority", "");
        String datetime = jsonObject.optString("dateTime", "");


        // Call your service to add the task
        HttpSession session = req.getSession(false);
        int userid = (int) session.getAttribute("USER_ID");
        int taskId;

        logger.debug("the userid is {}",userid);
        taskId = dbservices.AddTask(task, priority, datetime,userid);

        // Create JSON response
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "success");
        jsonResponse.put("message", "Task added successfully");
        jsonResponse.put("taskId", taskId);

        // Write JSON response
        try (PrintWriter out = res.getWriter()) {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }
}
