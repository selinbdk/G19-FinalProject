package application;

import javafx.event.ActionEvent;

public abstract class User {
	
	protected int id;
	protected String username;
	protected String name;
	protected String surname;
	protected String role;
	
	

	/**
	 * Constructs User object
	 * @param id user id
	 * @param username account username
	 * @param name name of the user
	 * @param surname surname of the user
	 * @param role role of the user, Cashier/Admin/Manager
	 */
	User(int id,String username,String name, String surname, String role){
		this.id=id;
		this.username=username;
		this.name=name;
		this.surname=surname;
		this.role=role;
		
	}
	
	abstract void displayInterface(ActionEvent event);
	
	
	
	
	
	
	
	

}
