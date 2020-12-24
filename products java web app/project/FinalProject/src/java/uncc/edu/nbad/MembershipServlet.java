/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uncc.edu.nbad;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uncc.edu.nbad.ProductTable;
import uncc.edu.nbad.UserTable;

/**
 *
 * @author deepa
 */
public class MembershipServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MembershipServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MembershipServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String url = "/login.jsp";
        String action = request.getParameter("action");
        
        HttpSession session = request.getSession();
        if((action != null) && action.equals("signup"))
            url = "/signup.jsp";
        else if((action != null) && action.equals("login"))
            url = "/login.jsp";
        else if((action != null) && action.equals("logout"))        //logout action - clear the session
        {
            session.invalidate();
            url="/index.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        String url = "";
        String action = request.getParameter("action");
        
        if(action.equals("signup"))     //creating new user
        {
            ArrayList<User> userList = new ArrayList<>();
            String Username = request.getParameter("UserName");
            String Password = request.getParameter("Password");
            String FirstName = request.getParameter("FirstName");
            String LastName = request.getParameter("LastName");
            String Email = request.getParameter("Email");
            
            User newUser = new User(FirstName, LastName, Email, Username, Password);
            try 
            {
                UserTable.addRecord(newUser);
                session.setAttribute("userDetails", userList);
                session.setAttribute("UserName", Username);
                session.setAttribute("isValidUser", true);
                url = "/products.jsp";
                getProductsDetails(request);
            } 
            catch (SQLException ex) {
                Logger.getLogger(MembershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*
                ADDING NEW USER - PREVIOUS CODE
            */
            
//            ArrayList<User> tempList = (ArrayList<User>) session.getAttribute("userDetails");
//            if(tempList != null)        //fetching the previous user list before adding new user
//                userList = tempList;
//
//            User newUser = new User(FirstName, LastName, Email, Username, Password);
//            userList.add(newUser);
//            session.setAttribute("userDetails", userList);
//            session.setAttribute("UserName", Username);
//            session.setAttribute("isValidUser", true);
//            url = "/products.jsp";
        }
        else if(action.equals("login"))
        {
            String Username = request.getParameter("Username");
            String Password = request.getParameter("Password");
            
            try 
            {
                User user = UserTable.getUser(Username);
                if(user!= null && user.getPassword().equals(Password))
                {
                    session.setAttribute("isValidUser", true);
                    session.setAttribute("UserName", Username);
                    url = "/products.jsp";
                    getProductsDetails(request);
                }
                else
                {
                    session.setAttribute("isValidUser", false);
                    url = "/login.jsp";
                }
//                UserTable.getUsers();
//                UserTable.getUsersMap();
            } 
            catch (SQLException ex) {
                Logger.getLogger(MembershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                /*
                CHECKING FOR A VALID USER - PREVIOUS CODE
                */
//            ArrayList<User> tempList = (ArrayList<User>) session.getAttribute("userDetails");
//            String Username = request.getParameter("Username");
//            String Password = request.getParameter("Password");
//            if(tempList != null)
//            {
//                for(User x : tempList)          //validating the username and password
//                {
//                    if(x.getUserName().equals(Username) && x.getPassword().equals(Password))
//                    {
//                        session.setAttribute("isValidUser", true);
//                        session.setAttribute("UserName", x.getUserName());
//                        url = "/products.jsp";
//                        break;
//                    } 
//                    else
//                    {
//                        session.setAttribute("isValidUser", false);
//                        url = "/login.jsp";
//                    }
//                }
//            }
//            else
//                url = "/login.jsp";
           
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public void init() throws ServletException
    {
//        System.out.println("init method");
       
    }
    
    public void getProductsDetails(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        try                     //fetching the list of all existing products from DB
        {
            List<Product> productList = ProductTable.selectProducts();
            session.setAttribute("products", productList);
        } 
        catch (SQLException ex) {
            Logger.getLogger(MembershipServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
