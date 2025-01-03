package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;




public class Authentication extends Database {
	

	/**
	 * This method checks the username and password to validate login.
	 * @param username user enters username for authentication
	 * @param password user enters password for authentication
	 * @return if username and password is matched with registered users
	 */

	public boolean checkValidLogin(String username, String password) {
		
		try {                                       
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         Statement statement = connection.createStatement();       
	         ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
		
	         while(resultSet.next()) {
	        	 
	        	 if(username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("user_password"))) {
	        		 
	        		 return true;
	        	 }
	        	 
	        	 
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
				return false;
			
			}
		
			return false;
		
	
		
	}
	
	
	
	public User createUserObject(String username,String password) {
			
			
			try {           
				 String queryForObject="SELECT user_id, username, firstname, lastname, user_role FROM users WHERE username =? AND user_password =?";
		         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
		         PreparedStatement statement =connection.prepareStatement(queryForObject);
		         statement.setString(1,username);
		         statement.setString(2,password);
		         ResultSet resultSet = statement.executeQuery();
			
		         
		         while(resultSet.next()) {
		        	
		        	int id=resultSet.getInt("user_id"); 
		        	String name= resultSet.getString("firstname");
		        	String surname= resultSet.getString("lastname");
		        	String role= resultSet.getString("user_role");
		        	
		        	
		        	User user = null;
		        	
		        	if(role.equalsIgnoreCase("manager")) {
		    			
		        		user = new Manager(id,username,name,surname,role);
		        	
		        	
		        	}
		        	else if(role.equalsIgnoreCase("admin")){
		        		user = new Admin(id,username,name, surname,role);
		        		
		        	}else {
		        		user = new Cashier(id,username,name, surname,role);
		        	}
		    		
		        	
		        	return user;
		        	
		        	
		        	 
		         }
		         
				}catch(SQLException sqlException)
				{
					sqlException.printStackTrace();
				
				
				}
			
			return null;
			
			
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
