package sait.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.User;
import service.UserService;


public class LoginServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {        
        String action = request.getParameter("action");
        if (action == null ||action.equals("login")) 
        {
            Cookie[] cookies = request.getCookies();
            String cookieName = "username";
            String cookieValue = "";        
            for(Cookie cookie: cookies) 
            {
                if(cookieName.equals(cookie.getName()))
                {
                    cookieValue = cookie.getValue();
                }
            }
            
            User user = new User(cookieValue,null);
            request.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);  
            return;
        }
        if(action.equals("logout"))
        {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            request.setAttribute("message", "You have successfuly logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }        
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("uName");
        String password = request.getParameter("pass");
      //  String url;
        
        User user = new User(username, password);
        UserService us = new UserService();
        request.setAttribute("user", user);
        
        if(username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) 
        {            
            request.setAttribute("message", "Both values are required!");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;            
        }
        if(us.login(username, password) == null)
        {
            request.setAttribute("message", "Invalid username or password!");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        
        Cookie c = new Cookie("username", username);
        c.setMaxAge(60*60*24);
        c.setPath("/");     
        
        String remeber = request.getParameter("remember");
        if(remeber != null)
        {
              response.addCookie(c);           
        }       
        else
        {
            c.setMaxAge(0);
        }        
        response.sendRedirect("home");
    }

}