/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;

/**
 *
 * @author Jorge
 */
public class CAccessUser  extends CConexion{
    
    private int idU;
    private String name;
    private ResultSet results;
    
    public CAccessUser() {
        Conectar();
    }
    
    //Consulta que borra todos los elementos (DELETE  FROM  public."User")
    public boolean insertRecord(int id, String name, int age, float latitude, float longitude) throws Exception {
    
        try{
            getStmt();
            results = stmt.executeQuery("INSERT INTO \"public\".\"User\" VALUES (" + id + ", '" + name + "', " + age + ", " + latitude + ", " + longitude + ")");
            return true;
        } catch (Exception ex){
           System.err.println("SQLException: " + ex.getMessage());
           return false;
        }
    }
    
    public ResultSet Listing()throws Exception {
        
        try{
            getStmt();
	    results = stmt.executeQuery("SELECT * FROM \"public\".\"User\"");
            return results;
        } catch (Exception ex){
        
            System.err.println("SQLException: " + ex.getMessage());
            return null;
        }
    }
   
    public ResultSet FindExisting(int id) throws Exception {
        
        try{
            getStmt();
	    results = stmt.executeQuery("SELECT * FROM \"public\".\"User\" WHERE idU LIKE'" + id + "%'");
            return results;
        } catch (Exception ex){
            System.err.println("SQLException: " + ex.getMessage());
            return null;
        }
    }

    public ResultSet SearchByName(String name) throws Exception {
        try{
            getStmt();
	    results = stmt.executeQuery("SELECT * FROM \"public\".\"User\" WHERE (name LIKE '" + name + "%')");
            return results;
        } catch (Exception ex){
           System.err.println("SQLException: " + ex.getMessage());
           return null;
        }
    }
        
    
       
}
