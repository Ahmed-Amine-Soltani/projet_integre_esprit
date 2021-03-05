/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yassine
 */
public class DbConnection {
    
    Connection connection =  null;
    private static DbConnection instance;
    
    private DbConnection(){
        
        String url="jdbc:mysql://localhost/projetdev";
        String username="root";
        String password="";
        
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }			
        
    }
    
    public static DbConnection getInstance(){
        if(instance==null){
            instance=new DbConnection();
        }
     return instance;
    }
    
    
    public Connection getConnection(){
        return connection;
    }
    
}
