/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.migration.clases;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
        
/**
 *
 * @author Jassi
 */
public class Conection {
    static Connection conn = null;
    
    static{
        try {
             String url = "jdbc:mysql://localhost:3306"; 
             String dbName = "/veterinariadb";
             String user = "root";
             String password = "";
             conn = DriverManager.getConnection(url+dbName,user,password);
             System.out.println("Conectado a " + dbName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Connection conexion(){
        return conn;
    }
}