/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laundrymanagementsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class DBConnection {
 private static final String cs = "jdbc:mysql://localhost:3306/laundry_db";
 private static final String user = "root";
 private static final String password = "";
 public static Connection getConnection(){
     Connection con = null;
     try{
         Class.forName("com.mysql.cj.jdbc.Driver");
         con = DriverManager.getConnection(cs,user,password);
         System.out.println("Database connected successfully!");
     }
     catch(ClassNotFoundException e){
         System.out.println("MySQL Driver not found");
     }
     catch(SQLException e){
         System.out.println("Database connection failed!");
         System.out.println(e.getMessage());
     }    
     return con;
     
 }
 
}
