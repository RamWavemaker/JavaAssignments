package repository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ValidateCokkie {
    public void validateSessionCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie sessionCookie = getCookie(request, "SESSIONID");

        if (sessionCookie != null) {
            String sessionId = sessionCookie.getValue();

            // Retrieve the session from the session ID
            HttpSession session = request.getSession(false); // Do not create a new session if none exists

            if (session != null && sessionId.equals(session.getId())) {
                // Session ID matches, session is valid
                response.getWriter().write("Session is valid.");
            } else {
                // Session ID does not match or session does not exist
                response.getWriter().write("Invalid session.");
            }
        } else {
            // No SESSIONID cookie found
            response.getWriter().write("No session cookie found.");
        }
    }

    private Cookie getCookie(HttpServletRequest request, String cookieName) {
        // Retrieve all cookies from the request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}




