package application;

public class Personnel {
	protected int id;
	protected String name;
	protected String surname;
	protected String username;
	protected String password;
	protected String role;
	
	
	
	Personnel(int id,String name, String surname, String username,String password,String role){
		this.id=id;
		this.name=name;
		this.surname=surname;
		this.username=username;
		this.password=password;
		this.role=role;
		
	}
	
	

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }
    

	public String getUsername() {
        return this.username;
    }
	

	public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }
	
    public int getId() {
        return this.id;
    }
	
	
	
	
	
	
	
	
	
	
	

}
