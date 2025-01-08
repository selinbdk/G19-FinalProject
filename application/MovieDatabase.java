package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase extends Database{
	
	
	public List<Movie> getAllMovies(){
		
		List<Movie>movieList = new ArrayList<>();
		
		try {           
			
			 String queryForMovies="SELECT movie_id,title,genre,posterUrl,summaryPath FROM movies";
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForMovies);
	       
	         ResultSet resultSet = statement.executeQuery();
		
	         
	         while(resultSet.next()) {
	        	 int movieId= resultSet.getInt("movie_id");
	        	 String title= resultSet.getString("title").toUpperCase();
	        	 String genre= resultSet.getString("genre").toUpperCase();
	        	 String posterUrl= resultSet.getString("posterUrl");
	        	 String summaryFilePath= resultSet.getString("summaryPath");
	        	 
	        	 Movie movie = new Movie(movieId,title,genre,posterUrl,summaryFilePath);
	        	 movieList.add(movie);
	        
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			
			
			}
		
		return movieList;
		
	}
	
	
	public String getNameOfMovie(int movieID){
		
		String title="";
		
		try {           
			
			 String queryForMovies="SELECT title FROM movies WHERE movie_id=?";
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForMovies);
	         
	         statement.setInt(1, movieID);
	       
	         ResultSet resultSet = statement.executeQuery();
		
	         
	         while(resultSet.next()) {
	  
	        	 title= resultSet.getString("title").toUpperCase();
	        	 
	        	 
	        	
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			
			
			}
		
		return title;
			
			
		
			
		}
		
		

	
	

	public List<String> getDateOfChosenMovie(int movieID){
		
		List<String>dateList = new ArrayList<>();
		
		try {           
		
			 String queryForDates="SELECT DISTINCT showingDate FROM sessions WHERE movie_id=?";
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForDates);
	         statement.setInt(1,movieID);
	       
	         ResultSet resultSet = statement.executeQuery();
		
	         
	         while(resultSet.next()) {
	        
	        	 dateList.add(resultSet.getDate("showingDate").toString());
	        
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			
			
			}
		
		return dateList;
		
	}
	
	
	
public List<String> getSessionsOfChosenMovie(int movieID, String enteredDate){
		
		List<String>sessionList = new ArrayList<>();
		
		try {           
		
			 String queryForMovies="SELECT halls.hallName,sessions.showingTime,halls.capacity - COUNT(seats.seat_id) AS VacantSeats "
			 		+ "FROM sessions JOIN halls ON sessions.hall_id = halls.hall_id LEFT JOIN seats ON seats.session_id= sessions.session_id AND seats.is_reserved = TRUE "
			 		+ "WHERE sessions.movie_id=? AND sessions.showingDate=? GROUP BY halls.hallName,sessions.showingTime,halls.capacity "
			 		+ "ORDER BY sessions.showingTime";
					 
					 
					
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForMovies);
	         statement.setInt(1,movieID);
	         statement.setDate(2,Date.valueOf(enteredDate));
	         ResultSet resultSet = statement.executeQuery();
		
	      
	         while(resultSet.next()) {
	        	 String hallInfo= resultSet.getString("hallName");
	        	 String timeInfo= resultSet.getTime("showingTime").toString();
	        	 int seatInfo= resultSet.getInt("VacantSeats");
	        
	        	 String sessionInfo = String.format("%s , %s, %d ",hallInfo,timeInfo,seatInfo);
	        	 sessionList.add(sessionInfo);
	        
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			
			
			}
		
		return sessionList;
		
	
		
	}
	
	

public List<Seat> getSeatsForSession(int sessionId){
	
	List<Seat>seatList = new ArrayList<>();
	
	try {           
		
		 String queryForSeats="SELECT seat_id,seatNumber FROM seats WHERE session_id=?";
         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
         PreparedStatement statement =connection.prepareStatement(queryForSeats);
         statement.setInt(1, sessionId);
       
         ResultSet resultSet = statement.executeQuery();
	
         
         while(resultSet.next()) {
        	 int seatId= resultSet.getInt("seat_id");
        	 String seatName= resultSet.getString("seatNumber");
        	 
        	 
        	 Seat seat = new Seat(seatId,seatName);
        	 seatList.add(seat);
        
         }
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		
		
		}
	
	return seatList;
	
}


public Seat getSeatForSeatId(int seatId){
	
	Seat seat = null;
	
	try {           
		
		 String queryForSeats="SELECT seatNumber FROM seats WHERE seat_id=?";
         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
         PreparedStatement statement =connection.prepareStatement(queryForSeats);
         statement.setInt(1, seatId);
       
         ResultSet resultSet = statement.executeQuery();
	
         
         while(resultSet.next()) {
        	
        	 String seatName= resultSet.getString("seatNumber");
        	 
        	 
        	  seat = new Seat(seatId,seatName);
        	 
        
         }
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		
		
		}
	
	return seat;
	
}



public void reserveSeat(List<Integer>seatIdList) {

	try {  
		
			for(int seatId: seatIdList){
				String queryForSeats="UPDATE seats SET is_reserved=TRUE WHERE seat_id=?";
		         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
		         PreparedStatement statement =connection.prepareStatement(queryForSeats);
		         statement.setInt(1, seatId);
		       
		          statement.executeUpdate();
			}
			 
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
	
		}

	
}

	
	



public List<Integer> getReservedSeatId(int sessionId){

List<Integer>reservedSeatIdList = new ArrayList<>();

try {           
	
	 String queryForSeats="SELECT seat_id FROM seats WHERE session_id=? AND is_reserved=TRUE";
     Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
     PreparedStatement statement =connection.prepareStatement(queryForSeats);
     statement.setInt(1, sessionId);
   
     ResultSet resultSet = statement.executeQuery();

     
     while(resultSet.next()) {
    	 int seatId= resultSet.getInt("seat_id");
    	
    	 
    	 
    	 
    	 reservedSeatIdList.add(seatId);
    
     }
     
	}catch(SQLException sqlException)
	{
		sqlException.printStackTrace();
	
	
	}

return reservedSeatIdList;

}


public int getSessionId(int movieId, String selectedSession){
	
	int sessionId=0;
		
		
		try {           
		
			 String queryForMovies="SELECT session_id FROM sessions WHERE movie_id=? AND showingTime=? AND hall_id=?";
			 String[] extractedInfo =selectedSession.split(",");
			 
			 int hallId;
			 String hallName= extractedInfo[0].trim();
			 String showingTime=extractedInfo[1].trim();
			 if(hallName.equals("Hall_A")) {
				 hallId=1;
			 }else {
				 hallId=2;
			 }
				
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForMovies);
	         statement.setInt(1,movieId);
	         statement.setString(2,showingTime);
	         statement.setInt(3,hallId);
	         ResultSet resultSet = statement.executeQuery();
		
	      
	         while(resultSet.next()) {
	        	
	        	  sessionId= resultSet.getInt("session_id");
	     
	        
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			
			
			}
		
		return sessionId;
		
	
		
	}
	
	
	


public Prices getAllPrices(){
	
	Prices prices=null;
	
	try {           
		
		 String queryForPrice="SELECT ticket_price, age_based_discount, ticket_tax_rate, product_tax_rate FROM prices";
         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
         PreparedStatement statement =connection.prepareStatement(queryForPrice);
        
       
         ResultSet resultSet = statement.executeQuery();
	
         
         while(resultSet.next()) {
        	
        	 double ticketPrice= resultSet.getDouble("ticket_price");
        	 double ageBasedDiscount = resultSet.getDouble("age_based_discount");
        	 double ticketTaxRate= resultSet.getDouble("ticket_tax_rate");
        	 double productTaxRate= resultSet.getDouble("product_tax_rate");
        	 
        	 
        	 prices = new Prices(ticketPrice,ageBasedDiscount,ticketTaxRate,productTaxRate);
        	 
        
         }
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		
		
		}
	
	return prices;
	
}



public List<Product> getAllProducts(){
	
	List<Product>productList= new ArrayList<>();
	
	try {           
		
		 String queryForProducts="SELECT product_name, price, stock_amount, image_url FROM products";
         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
         PreparedStatement statement =connection.prepareStatement(queryForProducts);
        
       
         ResultSet resultSet = statement.executeQuery();
	
         
         while(resultSet.next()) {
        	
        	 String productName= resultSet.getString("product_name");
        	 double productPrice = resultSet.getDouble("price");
        	 int stockAmount= resultSet.getInt("stock_amount");
        	 String productImage= resultSet.getString("image_url");
        	 
        	 Product product = new Product(productName,productPrice,stockAmount,productImage);
        	 productList.add(product); 
        	 
         }
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		
		
		}
	
	return productList;
	
}


	

	public void updateStockQuantity(String productName, int newStock) {
		
		try {  
				String queryForUpdate="UPDATE products SET stock_amount=? WHERE product_name=?";
		         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
		         PreparedStatement statement =connection.prepareStatement(queryForUpdate);
		         statement.setInt(1, newStock);
		         statement.setString(2, productName);
		       
		         statement.executeUpdate();
			 
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
	
		}
		
		
		
		
	}
	
	
	

	public List<Personnel> getAllPersonnel(){
		
		List<Personnel>personnelList = new ArrayList<>();
		
		try {           
			
			 String queryForMovies="SELECT user_id,firstname,lastname,username,user_password,user_role FROM users";
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForMovies);
	       
	         ResultSet resultSet = statement.executeQuery();
		
	         
	         while(resultSet.next()) {
	        	 int userId=resultSet.getInt("user_id");
	        	 String name= resultSet.getString("firstname");
	        	 String lastname= resultSet.getString("lastname");
	        	 String username= resultSet.getString("username");
	        	 String password= resultSet.getString("user_password");
	        	 String role= resultSet.getString("user_role");
	        	 
	        	 Personnel personnel = new Personnel(userId,name,lastname,username,password,role);
	        	 personnelList.add(personnel);
	        
	         }
	         
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			
			
			}
		
		return personnelList;
		
	}
	

	public void addPersonnel(Personnel personnel) {
		
		try {  
				String queryForAdd="INSERT INTO users (firstname,lastname,username,user_password,user_role) VALUES (?,?,?,?,?)";
		         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
		         PreparedStatement statement =connection.prepareStatement(queryForAdd);
		         statement.setString(1, personnel.getName());
		         statement.setString(2, personnel.getSurname());
		         statement.setString(3, personnel.getUsername());
		         statement.setString(4, personnel.getPassword());
		         statement.setString(5, personnel.getRole());
		      
		       
		         statement.executeUpdate();
			 
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
	
		}
		
		
		
		
	}
	
	

	public void deletePersonnel(Personnel personnel) {
		
		try {  
				String queryForDelete="DELETE FROM users WHERE user_id=?";
		         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
		         PreparedStatement statement =connection.prepareStatement(queryForDelete);
		         statement.setInt(1, personnel.getId());
		        
		         
		       
		         statement.executeUpdate();
			 
         
		}catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
	
		}
		
		
		
		
	}
	
	
    public int getMaxIdForUser() {
    
        int id=0;
        
        try {  
        	
			 String queryForMaxId="SELECT MAX(user_id) AS maxId FROM users";
	         Connection connection = DriverManager.getConnection(DATABASE_URL, ROOT, ROOT_PASSWORD);                     
	         PreparedStatement statement =connection.prepareStatement(queryForMaxId);
	         
	         ResultSet resultSet = statement.executeQuery();
	            
		         if (resultSet.next()) {
		                id = resultSet.getInt("maxId");
		            }
	        
	     
     
			}catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
		
			}
    	
    	
    	return id;
   
    	
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
