package Controller;

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
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private DatabaseRepositoryimpl database;
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        try {
            database = new DatabaseRepositoryimpl();
        } catch (Exception e) {
            logger.error("Error initializing DatabaseRepository", e);
            throw new ServletException("Error initializing DatabaseRepository", e);
        }
    }
    public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        out.println("<html>" +
                "<head>" +
                "<title>Login Page</title>" +
                "</head>" +
                "<body>" +
                "<h2>Login</h2>" +
                "<form action='loginServlet' method='post'>" +
                "<label for='email'>Email:</label>" +
                "<input type='email' id='email' name='email' required><br>" +
                "<label for='password'>Password:</label>" +
                "<input type='password' id='password' name='password' required><br>" +
                "<input type='submit' value='Login'>" +
                "</form>" +
                "</body>" +
                "</html>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            boolean isAuthenticated = Authenticate(email, password);
            if (isAuthenticated) { //

                Cookie cookie = new Cookie("logintoken","password");
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge(3600);

                res.addCookie(cookie);
                res.setContentType("application/json");
                res.setStatus(HttpServletResponse.SC_OK);
                String redirectURL = req.getContextPath() + "/Employee";
                res.sendRedirect(redirectURL);
            } else {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();
                out.print("{\"message\":\"Invalid credentials\"}");
                out.flush();
            }
        } catch (SQLException e) {
            logger.error("SQL Exception during authentication", e);
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.print("{'message':'An error occurred'}");
            out.flush();
        }
    }



    private Boolean Authenticate(String email, String password) throws SQLException {
       return database.getAuthentication(email,password);
    }


}
