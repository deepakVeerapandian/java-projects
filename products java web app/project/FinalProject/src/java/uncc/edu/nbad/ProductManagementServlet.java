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


/**
 *
 * @author deepa
 */
public class ProductManagementServlet extends HttpServlet {
    static boolean isProductEdited = false;
    static String editCode = "";

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
            out.println("<title>Servlet ProductManagementServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductManagementServlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        ArrayList<Product> tempList = (ArrayList<Product>) session.getAttribute("products");        
        String url = "";
        
        if(action.equals("deleteProduct") || action.equals("displayProduct"))       //navigation onClick of edit/delete in products table
        {
            url = (action.equals("deleteProduct")) ? "/confirmDelete.jsp" : "/product.jsp";
            
            //fetching the selected product from DB - VIEW of product.jsp
            try
            {
                boolean exist = ProductTable.exists(code);
                if(exist)
                {
                    Product selectedProduct = ProductTable.selectProduct(code);
                    String description = selectedProduct.getDescription();
                    double price = selectedProduct.getPrice();
                    request.setAttribute("code", code);
                    session.setAttribute("currentCode", code);
                    request.setAttribute("description", description);
                    request.setAttribute("price", price);
                    if(action.equals("displayProduct"))
                    {
                        request.setAttribute("isEdited", true);
                        isProductEdited = true;
                        editCode = code;
                    }    
                }
                
            }
            catch (SQLException ex) {
                Logger.getLogger(MembershipServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProductManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*
                DISPLAYING DETAILS IN PRODUCT.JSP (PREVIOUS CODE)
            */            
//            if(tempList != null)
//            {
//                for(Product x : tempList)
//                {
//                    if(x.getCode().equals(code))
//                    {
//                        String description = x.getDescription();
//                        double price = x.getPrice();
//                        request.setAttribute("code", code);
//                        request.setAttribute("description", description);
//                        request.setAttribute("price", price);
//                        if(action.equals("displayProduct"))
//                        {
//                            request.setAttribute("isEdited", true);
//                            isProductEdited = true;
//                            editCode = code;
//                        }                        
//                        break;
//                    } 
//                }
//            }                
        }
        else if(action.equals("delete"))            //delete action onClick of "yes" in confirmDelete page
        {
            url = "/products.jsp";
            try {
                ProductTable.deleteProduct(new Product(code,"",0));
                getProductsDetails(request);
            } catch (SQLException ex) {
                 Logger.getLogger(ProductManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*
                DELETING PRODUCT  (PREVIOUS CODE)
            */   
//            if(tempList != null)
//            {
//                int index = 0;
//                for(Product x : tempList)
//                {
//                    if(x.getCode().equals(code))
//                        break;
//                    index++;
//                }
//                tempList.remove(index);
//                session.setAttribute("products", tempList);
//            }             
            
        }
        else if(action.equals("displayProducts"))           
        {
            url = "/products.jsp";
        }
        else if(action.equals("addProduct"))         
        {
            url = "/product.jsp";
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
        String action = request.getParameter("action");
        String url = "";
        if(action.equals("addProduct"))                 //showing product page onClick of addProduct button
        {
           url = "/product.jsp";
        }
        else if(action.equals("saveProduct"))           //saving product details in product.jsp
        {
            HttpSession session = request.getSession();
            ArrayList<Product> productList = new ArrayList<>();
            ArrayList<Product> tempList = (ArrayList<Product>) session.getAttribute("products");
            String code = request.getParameter("code");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            
            //editing existing product details - UPDATE IN DB
            if(isProductEdited)
            {  
                String oldCode = (String) session.getAttribute("currentCode");
                try {
                    ProductTable.updateProduct(new Product(code,description,price),oldCode );
                    getProductsDetails(request);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            /*
                UPDATING DETAILS IN PRODUCT.JSP (PREVIOUS CODE)
            */   
//            if(isProductEdited)         //editing existing product details
//            {
//                if(tempList != null)            //fetching the previous product list before saving the product changes
//                {
//                    int index = 0;
//                    for(Product x : tempList)
//                    {
//                        if(x.getCode().equals(editCode))
//                            break;
//                        index++;
//                    }
//                    tempList.get(index).setCode(code);
//                    tempList.get(index).setDescription(description);
//                    tempList.get(index).setPrice(price);
//                    session.setAttribute("products", tempList);
//                    editCode = "";
//                    isProductEdited = false;
//                }
//            }
            else                //saving new product details - INSERT in DB
            {
                Product newProduct = new Product(code, description, price);
                try 
                {
                    ProductTable.insertProduct(newProduct);
                    getProductsDetails(request);
                } 
                catch (SQLException ex) {
                    Logger.getLogger(ProductManagementServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                /*
                    INSERTING NEW PRODUCT (PREVIOUS CODE)
                */   
//                if(tempList != null)            //fetching the previous product list before adding new product
//                productList = tempList;
//            
//                Product newProduct = new Product(code, description, price);
//                productList.add(newProduct);
//                session.setAttribute("products", productList);
            }
            
            url = "/products.jsp";
        }        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    public void getProductsDetails(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        try                     //fetching the list of all existing products from DB
        {
            List<Product> productList = ProductTable.selectProducts();
            session.setAttribute("products", productList);
//            ProductTable.saveProducts(productList);
        } 
        catch (SQLException ex) {
            Logger.getLogger(MembershipServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
