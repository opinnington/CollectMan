package collectMan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.swing.JOptionPane;

public class CollectMan {
	
    public static void delete(String c){
    	
    	
    	try {
    		Connection connection = sqliteConnection.getDbConnection();
			String id = c;
			String myQuery = "DELETE FROM Item WHERE [ID] =" + id + "";
			PreparedStatement pst = connection.prepareStatement(myQuery);
			pst.execute();
			pst.close();
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
    }
    public static void insert(String i, String o, String n, String y, String v, String s){
    	try{
			Connection connection = sqliteConnection.getDbConnection();
			
			
			String myQuery = "INSERT INTO Item (Item, Origin, Notes, Year, Value, Status) VALUES (?,?,?,?,?,?)";

			PreparedStatement pst = connection.prepareStatement(myQuery);
			
			pst.setString(1, i);
			pst.setString(2, o);
			pst.setString(3, n);
			pst.setString(4, y);
			pst.setString(5, v);
			pst.setString(6, s);
		
			pst.execute();
			pst.close();
		
		
			JOptionPane.showMessageDialog(null, "Success");
			}
			catch(Exception ex){
				ex.printStackTrace();
			}   	 
    }
    
    public static void update(String c,String i, String o, String n, String y, String v, String s){
    	try {
			Connection connection = sqliteConnection.getDbConnection();
			
			
			 String myQuery = "UPDATE Item SET Item='" + i +  
                     "', Origin ='" + o +
                     "', Notes ='" + n +
                     "', Year ='" + y +
                     "', Value ='" + v +
                     "', Status ='" + s +
                     

                     "' WHERE ID=" + c + "";
			PreparedStatement pst = connection.prepareStatement(myQuery);
			
			
			pst.execute();
			pst.close();
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
    }
}

