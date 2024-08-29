package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DatabaseService;
import service.DatabaseServiceImpl;

import java.io.IOException;

@WebServlet("/taskhandling")
public class TaskHandlingServlet extends HttpServlet {
    private DatabaseService dbservices;
    private static final Logger logger = LoggerFactory.getLogger(TaskHandlingServlet.class);

    @Override
    public void init() {
        dbservices = new DatabaseServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Retrieve the cookies from the request
        Cookie[] cookies = req.getCookies();
        String sessionIdFromCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    sessionIdFromCookie = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionIdFromCookie == null) {
            logger.info(req.getContextPath());
            // If no session ID is found in the cookie, redirect to login page
            res.sendRedirect(req.getContextPath()+"/");
            return;
        }

        // Retrieve the current session based on the session ID from the cookie
        HttpSession session = req.getSession(false);
        if (session == null || !sessionIdFromCookie.equals(session.getId())) {
            res.sendRedirect(req.getContextPath() + "/");
            return;
        }

        // Retrieve user ID from the session
        Integer userid = (Integer) session.getAttribute("USER_ID");
        if (userid == null) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User ID not found in session.");
            return;
        }

        // Proceed to handle the task
        logger.debug("User ID is {}", userid);
        String tasksJson = dbservices.getTasks(userid);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(tasksJson);
    }
}
