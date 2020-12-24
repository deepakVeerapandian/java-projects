/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uncc.edu.nbad;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import uncc.edu.nbad.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UserTable {
	
    static String url = "jdbc:mysql://localhost:3306/shop";
    static String username = "shop_user";
    static String password = "123";
    static Connection connection = null;
    static PreparedStatement preparedQuery = null;
    static Statement query = null;
    static ResultSet resultset = null;
    static User user = null;
    
	//Static initializer, it runs when the class is intialized (it is executed once)
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username,password);
            query = connection.createStatement();
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (SQLException ex) {
            Logger.getLogger(UserTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void addRecord(User user) throws IOException, SQLException 
    {
	String Query = "INSERT INTO users (firstName, lastName, email, password, userName) VALUES (?,?,?,?,?)";
        preparedQuery = connection.prepareStatement(Query);
        preparedQuery.setString(1, user.getFirstName());
        preparedQuery.setString(2, user.getLastName());
        preparedQuery.setString(3, user.getEmail());
        preparedQuery.setString(4, user.getPassword());
        preparedQuery.setString(5, user.getUserName());
        preparedQuery.executeUpdate();
    }

    public static User getUser(String userName) throws IOException, SQLException 
    {
        user = null;
        String Query = "SELECT * FROM users WHERE userName='"+ userName + "'";
        resultset = query.executeQuery(Query);
        while(resultset.next())
        {
            String Password = resultset.getString("password");
            String FirstName = resultset.getString("firstName");
            String LastName = resultset.getString("lastName");
            String Email = resultset.getString("email");
            user = new User (FirstName, LastName, Email, userName, Password);
        }
        return user;
    }

    public static ArrayList<User> getUsers() throws IOException, SQLException 
    {
	ArrayList<User> userList = new ArrayList<User>();
        String Query = "SELECT * FROM users";
        resultset = query.executeQuery(Query);
        
        while(resultset.next())
        {
            String Username = resultset.getString("password");
            String Password = resultset.getString("password");
            String FirstName = resultset.getString("firstName");
            String LastName = resultset.getString("lastName");
            String Email = resultset.getString("email");
            user = new User (FirstName, LastName, Email, Username, Password);
            userList.add(user);
        }
        return userList;
    }

    public static HashMap<String, User> getUsersMap() throws IOException, SQLException 
    {
        HashMap<String, User> userHashMap = new HashMap<String, User>();
        String Query = "SELECT * FROM users";
        resultset = query.executeQuery(Query);
        
        while(resultset.next())
        {
            String Username = resultset.getString("userName");
            String Password = resultset.getString("password");
            String FirstName = resultset.getString("firstName");
            String LastName = resultset.getString("lastName");
            String Email = resultset.getString("email");
            user = new User (FirstName, LastName, Email, Username, Password);
            userHashMap.put(Username, user);
        }
        return userHashMap;
    }
}
