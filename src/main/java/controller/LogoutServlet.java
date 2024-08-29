package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Remove the session cookie
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath(req.getContextPath()); // Ensure the path matches
        res.addCookie(cookie);

        // Write a response message
        res.setContentType("text/plain");
        res.getWriter().write("Logged out successfully!");
//        res.sendRedirect(req.getContextPath()+"/");
    }
}
