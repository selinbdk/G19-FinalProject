package application;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCard {
	
	private double totalPrice;
	private List<Integer>enteredSeatsId;
	
	
	
	public ShoppingCard() {
		this.enteredSeatsId= new ArrayList<>();
		this.totalPrice=0.0;
		
		
	}
	
	
	public void addSeat(Seat seat) {
		enteredSeatsId.add(seat.getSeatId());
		totalPrice+=seat.getSeatPrice();
		
		
	}
	
	public void deleteSeat(Seat seat) {
		enteredSeatsId.remove(Integer.valueOf(seat.getSeatId()));
		totalPrice-=seat.getSeatPrice();
		
		
	}
	
	public List<Integer> getEnteredSeats() {
		return this.enteredSeatsId;
	}
	
	
	
	
	public double getTotalPrice() {
		return this.totalPrice;
	}
	
	
	
	
	
	
	
	

}
