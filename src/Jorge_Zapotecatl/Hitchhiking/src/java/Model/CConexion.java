/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jorge
 */
public class CConexion {
    
    protected Connection con;
    protected Statement stmt;
    private String serverName= "localhost";
    private String portNumber = "5432";
    private String databaseName= "Hitchhiking";
    private String url = "jdbc:postgresql://localhost:5432/" + databaseName;
    private String userName = "postgres";
    private String password = "a";
    private String errString;
         
    
    public CConexion() {

    }
    
    private String getConnectionUrl() {
         return url;
    }

    public Connection Conectar(){
        con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(getConnectionUrl(), userName, password);
            stmt = con.createStatement();
            System.out.println("Conectado");
        }catch(Exception e){ 
            errString= "Error Mientras se conectaba a la Base de Datos";
            System.out.println(errString);
            return null;
        }
        
        return con;
    }

    public void Desconectar() {
        
        try{
            stmt.close();
            con.close();
        }catch(SQLException e){
             errString= "Error Mientras se Cerraba la Conexion a la Base de Datos";
        }
    }
    
    public Statement getStmt() {
          return this.stmt;
    }

}
