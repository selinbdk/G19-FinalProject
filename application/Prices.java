package application;

public class Prices {
	private double ticketPrice;
	private double ageBasedDiscount;
	private double ticketTaxRate;;
	private double productTaxRate;
	
	
	
	public Prices(double ticketPrice,double ageBasedDiscount,double ticketTaxRate, double productTaxRate) {
		this.ticketPrice=ticketPrice;
		this.ageBasedDiscount=ageBasedDiscount;
		this.ticketTaxRate=ticketTaxRate;
		this.productTaxRate=productTaxRate;
		
	}
	
	
	
	public double getTicketPrice() {
		return this.ticketPrice;
		
	}
	
	
	public double getAgeBasedDiscount() {
		return this.ageBasedDiscount;
		
	}
	
	public double getTicketTaxRate() {
		return this.ticketTaxRate;
		
	}
	
	public double getProductTaxRate() {
		return this.productTaxRate;
		
	}
	
	
	
	
	
	
	
	
	
	

}
