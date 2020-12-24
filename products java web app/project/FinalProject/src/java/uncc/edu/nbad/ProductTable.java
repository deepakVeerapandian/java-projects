/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uncc.edu.nbad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import uncc.edu.nbad.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProductTable {
    
    static String url = "jdbc:mysql://localhost:3306/shop";
    static String username = "shop_user";
    static String password = "123";
    static Connection connection = null;
    static PreparedStatement preparedQuery = null;
    static Statement query = null;
    static ResultSet resultset = null;
    static Product product = null;
	
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");          
            connection = DriverManager.getConnection(url, username, password);
            query = connection.createStatement();
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } 
        catch (SQLException ex) {
            Logger.getLogger(ProductTable.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static List<Product> selectProducts() throws SQLException 
    {
        List<Product> productsList = new ArrayList<Product>();
        String Query = "SELECT * FROM products";
        resultset = query.executeQuery(Query);
        String code = "";
        String description = "";
        double price = 0;
        
        while(resultset.next())
        {
            code = resultset.getString("code");
            description = resultset.getString("description");
            price = resultset.getDouble("price");
            product = new Product(code, description,price);
            productsList.add(product);
        }
        return productsList;
    }

    public static Product selectProduct(String productCode) throws SQLException, ClassNotFoundException 
    {
        resultset =  query.executeQuery("SELECT * FROM products WHERE code='"+ productCode +"'");
        String description = "";
        double price = 0;
        while(resultset.next())
        {
            description = resultset.getString("description");
            price = resultset.getDouble("price");
            product = new Product (productCode, description, price);
        }
        return(product);
    }

    public static boolean exists(String productCode) throws SQLException 
    {
        String Query = "SELECT * FROM products WHERE code='"+ productCode + "'";
        resultset  = query.executeQuery(Query);
        while(resultset.next())
            return true;
        return false;        
    }    
    
    public static void saveProducts(List<Product> products) throws SQLException 
    {
        int i =0;
        while(i < products.size())
        {           
            product = products.get(i);
            String Query = "INSERT INTO products (code,description,price) VALUES (?, ?, ?)";
            preparedQuery = connection.prepareStatement(Query);
            preparedQuery.setString(1, product.getCode());
            preparedQuery.setString(2, product.getDescription());
            preparedQuery.setDouble(3, product.getPrice());
            preparedQuery.executeUpdate();
             i++;
        }
        
    }

    public static void insertProduct(Product product) throws SQLException 
    {
        String Query = "INSERT INTO products (code,description,price) VALUES (?, ?, ?)";
        preparedQuery = connection.prepareStatement(Query);
        preparedQuery.setString(1, product.getCode());
        preparedQuery.setString(2, product.getDescription());
        preparedQuery.setDouble(3, product.getPrice());
        preparedQuery.executeUpdate();
    }

    public static void updateProduct(Product product, String originalCode) throws SQLException 
    {        
        String Query = "UPDATE products SET code=?, description=?, price=? WHERE code=?";
        preparedQuery = connection.prepareStatement(Query);
        preparedQuery.setString(1, product.getCode());
        preparedQuery.setString(2, product.getDescription());
        preparedQuery.setDouble(3, product.getPrice());
        preparedQuery.setString(4, originalCode);
        preparedQuery.executeUpdate();
    }

    public static void deleteProduct(Product product) throws SQLException 
    {
        String Query = "DELETE FROM products WHERE code='" + product.getCode() + "'";
	query.executeUpdate(Query);
    }    
    
}

