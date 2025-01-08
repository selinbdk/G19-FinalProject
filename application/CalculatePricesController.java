package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class CalculatePricesController {
	
	private ShoppingCard shoppingCard;
	private String cardContent;
	private List<Customer>customerList = new ArrayList<>();
	private double totalPrice;
	
		@FXML
		private Button addCustomerButton;

		@FXML
		private TextField ageTextField;
		
		@FXML
	    private Label checkPersonLabel;

	    @FXML
	    private TextField nameTextField;
	    
	    @FXML
	    private TextField surnameTextField;

	    @FXML
	    private Button nextButton;

	    @FXML
	    private TextArea textAreaForCard;

	    
	    
	    
	    @FXML
	    void addCustomerButtonPressed(ActionEvent event) {
	    	String enteredName= nameTextField.getText().trim();
	    	String enteredSurname= surnameTextField.getText().trim();
	    	String enteredStringAge= ageTextField.getText().trim();
	    	
	    	int totalSeatNumber= shoppingCard.getEnteredSeats().size();
	    	
	    	
	    	if(enteredName.isEmpty() || enteredSurname.isEmpty() || enteredStringAge.isEmpty()) {
	    		
	    		Alert alert= new Alert(AlertType.ERROR);
		    	alert.setTitle("Error Message");
		    	alert.setHeaderText(null);
		    	alert.setContentText("Please fill in all information!");
		    	alert.showAndWait();
		    	
		    	return;
	    		
	    	}
	    	
	    	
	        if (!enteredName.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$") || !enteredSurname.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$")){
	            	
	            Alert alert= new Alert(AlertType.ERROR);
			    alert.setTitle("Error Message");
			    alert.setHeaderText(null);
			    alert.setContentText("Name and Surname shouldn't contain number or special characters!");
			    alert.showAndWait();
			    	
			    return;
		    		
	           
	        }
	    	
	    	
	    	if(customerList.size()==totalSeatNumber) {
	    		
	    		Alert alert= new Alert(AlertType.ERROR);
		    	alert.setTitle("Error Message");
		    	alert.setHeaderText(null);
		    	alert.setContentText("You added all person already!");
		    	alert.showAndWait();
		    	
		    	return;
	    		
	    		
	    	}

	    	
	    	
	    	try {
	    		
	    			int enteredAge =Integer.parseInt(enteredStringAge);
	    			if(enteredAge< 0 || enteredAge >125) {
	    				
	    				Alert alert= new Alert(AlertType.ERROR);
	    		    	alert.setTitle("Error Message");
	    		    	alert.setHeaderText(null);
	    		    	alert.setContentText("Please enter valid age!");
	    		    	alert.showAndWait();
	    		    	return;
	    				
	    			}
	    		
	    		
	    		customerList.add(new Customer(enteredName,enteredSurname,enteredAge));
	    		nameTextField.clear();
	    		surnameTextField.clear();
	    		ageTextField.clear();
	    		
	    		checkPersonLabel.setText(customerList.size()+"/"+ totalSeatNumber);
	    		
	    		updateShoppingCard();
	    		
	    		if(customerList.size()==totalSeatNumber) {
	    			nextButton.setDisable(false);
	    		}
	    		
	    		
	    	}catch(NumberFormatException e){
	    		
	    		Alert alert= new Alert(AlertType.ERROR);
		    	alert.setTitle("Error Message");
		    	alert.setHeaderText(null);
		    	alert.setContentText("Please enter valid age!");
		    	alert.showAndWait();
		    	
	    		
	    		
	    	}
	    	
	    	
	    
	    	
	    }
	    

	    
	    @FXML
	    void nextButtonPressed(ActionEvent event) {
	    	
	    	if(customerList.size()<shoppingCard.getEnteredSeats().size()) {
	    		
	    		Alert alert= new Alert(AlertType.ERROR);
		    	alert.setTitle("Error Message");
		    	alert.setHeaderText(null);
		    	alert.setContentText("Please enter information for all person!");
		    	alert.showAndWait();
		    	
		    	return;
	    	
    			
    		}
	    	
	    	try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductSelectionView.fxml"));
		        Parent root = loader.load();
		        ProductSelectionController productSelectionController = loader.getController();
		        productSelectionController.setCardContent(cardContent);
		        productSelectionController.setTotalPrice(totalPrice);
		        productSelectionController.setReservedSeats(shoppingCard);
		        
		        
		        
		        nextButton.getScene().getWindow().hide();
	

		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.show();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    	
    		

	    }

	public void updateShoppingCard() {
		textAreaForCard.setEditable(false);
		textAreaForCard.setFont(Font.font(20));
		textAreaForCard.clear();
		
		
		double totalTicketTax= 0.0;
		double totalProductTax= 0.0;
		double totalTicketPrice=0.0;
		double totalDiscount=0.0;
		double finalTotalPrice=0.0;
		
		
		MovieDatabase movieDatabase = new MovieDatabase();
		double oneTicketPrice= movieDatabase.getAllPrices().getTicketPrice();
		double ticketTaxRate= movieDatabase.getAllPrices().getTicketTaxRate();
		double ageBasedDiscount= movieDatabase.getAllPrices().getAgeBasedDiscount();
		
		
		
		for(int i=0; i<customerList.size();i++) {
			
			Customer customer= customerList.get(i);
			
			double currentTicketPrice=oneTicketPrice;
			
			double discountAmount=0.0;
		
			
			if(customer.getAge()<18 || customer.getAge()>60) {
				
				discountAmount= currentTicketPrice * ageBasedDiscount;
				totalDiscount+=discountAmount;
				currentTicketPrice-=discountAmount;
				
			}
			
			double oneTicketTax =currentTicketPrice*ticketTaxRate;
			totalTicketTax+=oneTicketTax;
			totalTicketPrice+=currentTicketPrice;
			
			currentTicketPrice+=oneTicketTax;
			
			finalTotalPrice+=currentTicketPrice;
			
			
			String customerContent = "Seat " + (i + 1) + ": \n"
	                + "Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n"
	                + "AGE: " + customer.getAge() + "\n"
	                + "ORIGINAL TICKET PRICE: " + oneTicketPrice + "\n"
	                + "DISCOUNT: " + discountAmount + "\n"
	                + "TAX: " + oneTicketTax + "\n"
	                + "CUSTOMER'S TICKET PRICE: " + currentTicketPrice + " TL\n\n";
	             
	        textAreaForCard.appendText(customerContent);

	
			
			
		}
		
		
		String finalContent = "**************************"+"\n"
				+ "Total Ticket Price: " + totalTicketPrice + " TL\n"
	            + "Total Discount: " + totalDiscount + " TL\n"
	            + "Total Tax: " + totalTicketTax + " TL\n"
	            + "Final Total Price: " + finalTotalPrice + " TL\n\n";

	    textAreaForCard.appendText(finalContent);
	    
	    this.cardContent= textAreaForCard.getText();
	    this.totalPrice=finalTotalPrice;
		
		
		
		
		
	}
	    
	    
	    
	    
	public void setShoppingCard(ShoppingCard shoppingCard) {
		this.shoppingCard=shoppingCard;
		textAreaForCard.setEditable(false);
		
	
	}

}
