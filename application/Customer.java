package application;

public class Customer {
	private String firstName;
	private String lastName;
	private int age;
	
	//private LocalDate birthDate;
	
	Customer(String firstName,String lastName, int age){
		this.firstName=firstName;
		this.lastName=lastName;
		this.age=age;
		
	}
	
	
	/*public int getAge() {
		return Period.between(birthDate, LocalDate.now()).getYears();
		
	}
	*/
	
	
	
	public int getAge() {
		return this.age;
		
	}
	
	public double getDiscount() {
		int age= getAge();
		if(age<18 || age>60) {
			
			return 0.50;
		}
		return 0.00;
		
	}
	
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	/*public LocalDate getBirthDate() {
		return this.birthDate;
		
		
	}*/
	
	
	
	
	
	
	
	

}
