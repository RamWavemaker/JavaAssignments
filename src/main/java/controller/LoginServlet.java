package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DatabaseService;
import service.DatabaseServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {
    private DatabaseService dbservices;
    int globaluserid;
    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    public int getUserid(){
        return globaluserid;
    }

    @Override
    public void init() {
        dbservices = new DatabaseServiceImpl();
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        boolean Authenticate = dbservices.AuthenticateUser(email,password);
        if(Authenticate){
            int userid = dbservices.getuserid(email);
            logger.debug("User ID is: {}", userid);
            globaluserid = userid;

            logger.debug("globaluser ID is: {}", globaluserid);
            HttpSession session = req.getSession();
            session.setAttribute("USER_ID",userid);

            // Create a cookie to store session ID
            Cookie sessionCookie = new Cookie("SESSIONID", session.getId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(true);
            res.addCookie(sessionCookie);
            String sessionid = session.getId();

            res.getWriter().write("Login successful!"+sessionid);
            String path = req.getContextPath() + "/index.html";
            res.sendRedirect(path);
        }else{
            String path = req.getContextPath() + "/";
            res.sendRedirect(path);
        }
    }
}
