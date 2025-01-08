package application;

public class Seat {
	private int seatId;
	private String seatNumber;

	private double seatPrice= 200.0;
	
	public Seat(int seatId, String seatNumber) {
		this.seatId=seatId;
		this.seatNumber=seatNumber;
		

	}
	
	
	public int getSeatId() {
		
		return this.seatId;
	}
	
	public String getSeatNumber() {
		return this.seatNumber;
	}
	

	public double getSeatPrice() {
		return this.seatPrice;
	}
	
	
	
	
	
	
	
	
	
	

}
