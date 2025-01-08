package application;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProductSelectionController implements Initializable {
	
	private String finalContent;
	private ShoppingCard shoppingCard;
	private List<Product>productList=new ArrayList<>();
	private List<Product> receivedProductList=new ArrayList<>();
	
	private double productTaxRate;
	private double finalTotalPrice;

	

	
	@FXML
    private Button completeButton;

    @FXML
    private GridPane productGridPane;

    @FXML
    private TextArea textAreaForCard;

   
    
    @FXML
    void completeButtonPressed(ActionEvent event) {
    	
    	MovieDatabase movieDatabase = new MovieDatabase();
    	movieDatabase.reserveSeat(this.shoppingCard.getEnteredSeats());
    	
    	Alert alert= new Alert(AlertType.INFORMATION);
    	alert.setTitle("Success");
    	alert.setHeaderText(null);
    	alert.setContentText("You reserved seats successfully!");
    	alert.showAndWait();
  
    	
    	try {
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchView.fxml"));
	        Parent root = loader.load();
	        SearchController searchController = loader.getController();
	       
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	

    }
    
    
    public void setReservedSeats(ShoppingCard shoppingCard) {
    	this.shoppingCard=shoppingCard;
    	
    	
    }
    
    
    
    
   
    
    
    public void setCardContent(String finalContent) {
    	this.finalContent=finalContent;
    	textAreaForCard.setText(finalContent);
    	
    	
    }
    
    public void setTotalPrice(double totalPrice) {
    	this.finalTotalPrice=totalPrice;
    	
    	
    }



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		MovieDatabase movieDatabase = new MovieDatabase();
		this.productList=movieDatabase.getAllProducts();
		
		this.productTaxRate= movieDatabase.getAllPrices().getProductTaxRate();
		
		textAreaForCard.setEditable(false);
		textAreaForCard.setFont(Font.font(20));
		displayProducts();
	
		
		
	}
	
	
	public void displayProducts() {
		productGridPane.getChildren().clear();
		productGridPane.setVgap(10);
		int row=0;
		for(Product product :productList) {
			Label nameLabel = new Label(product.getName());
			nameLabel.setFont(Font.font(24));
			ImageView productImage=new ImageView(new Image(product.getImageUrl()));
			productImage.setFitHeight(100);
			productImage.setFitWidth(200);
			productImage.setPreserveRatio(true);
			
			Label priceLabel=new Label(String.format("%.2f TL",product.getPrice()));
			priceLabel.setFont(Font.font(28));
			
			Spinner<Integer> productAmountSpinner= new Spinner<>(0,product.getStockAmount(),getProductAmountInCard(product));
			productAmountSpinner.setPrefWidth(80);  
			productAmountSpinner.setPrefHeight(30);
			
			Button addProductButton = new Button("Add");
			addProductButton.setFont(Font.font(14));
			addProductButton.setOnAction(event -> addProductForCard(product,productAmountSpinner.getValue()));
			
			Button removeProductButton = new Button("Reset");
			removeProductButton.setFont(Font.font(14));
			removeProductButton.setOnAction(event -> removeProductFromCard(product));
			
			HBox operationsRow = new HBox(10,productAmountSpinner,addProductButton,removeProductButton);
			operationsRow.setAlignment(Pos.CENTER);
			
			productGridPane.addRow(row, nameLabel,productImage,priceLabel,operationsRow);
			row++;
			
		
		}
		
		
		
		
	}

	
    public void addProductForCard(Product product,int productAmount){
    	if(productAmount==0) {
    		
    		Alert alert= new Alert(AlertType.ERROR);
		    alert.setTitle("Error Message");
		    alert.setHeaderText(null);
		    alert.setContentText("Amount should be greater than zero!");
		    alert.showAndWait();
		    	
		    return;
    		
    	}
    	
    	if(!product.checkStock(productAmount)) {
    		
    		Alert alert= new Alert(AlertType.ERROR);
		    alert.setTitle("Error Message");
		    alert.setHeaderText(null);
		    alert.setContentText("Sorry, stock is not enough!");
		    alert.showAndWait();
		    	
		    return;
    		
    	}
    	
    	double totalProductPrice =product.getPrice()*productAmount;
    	double productTaxPrice = totalProductPrice * productTaxRate;
    	double finalProductPrice = totalProductPrice + productTaxPrice;
    	product.reduceStock(productAmount);
    	
    	for(int i =0;i<productAmount;i++) {
    		receivedProductList.add(product);
    		
    	}
    	
    	
    	String productContent= productAmount +" " +product.getName() +"  " + productAmount*product.getPrice() + " TL\n"
    			+ "TAX: " + productTaxPrice + " TL\n\n";
              
             
    	
        textAreaForCard.appendText(productContent);
     
        finalTotalPrice+=finalProductPrice;
        textAreaForCard.appendText("FINAL TOTAL: "+ finalTotalPrice +"\n\n");
        
        displayProducts();
    	
    	
    
    	
    }
    
    
    public void removeProductFromCard(Product product) {
    	if(!receivedProductList.contains(product)) {
    		
    		Alert alert= new Alert(AlertType.ERROR);
		    alert.setTitle("Error Message");
		    alert.setHeaderText(null);
		    alert.setContentText("This product is not in your shopping card!");
		    alert.showAndWait();
		    	
		    return;
    	
    	}
    	
    	int countInReceivedList = 0;
        for (Product p : receivedProductList) {
            if (p.equals(product)) {
            	countInReceivedList++;
            }
        }

        
        while (receivedProductList.contains(product)) {
            receivedProductList.remove(product);
        }

        
        product.setStock(product.getStockAmount() + countInReceivedList);

        double productPrice = product.getPrice() * countInReceivedList;
        double productTax = productPrice * productTaxRate;
        finalTotalPrice -= (productPrice + productTax);

      
        textAreaForCard.clear();
        textAreaForCard.appendText(finalContent + "\n");
        
        boolean hasProduct=false;
        for (Product p : productList) {
            int quantity = 0;
            for (Product oneProduct : receivedProductList) {
                if (oneProduct.equals(p)) {
                    quantity++;
                }
            }
            if (quantity > 0) {
            	hasProduct=true;
                double itemTotalPrice = p.getPrice() * quantity;
                double itemTax = itemTotalPrice * productTaxRate;
                
                String updatedContent = quantity +" "+p.getName()+" " +itemTotalPrice+ " TL\n"
                + "TAX: " + itemTax + " TL\n\n";
                
                
                textAreaForCard.appendText(updatedContent);
            }
        }
        
       
        if(hasProduct==true) {
        	 textAreaForCard.appendText("FINAL TOTAL: " + finalTotalPrice + " TL\n");
 
        }
       
        displayProducts();
    	
    	
    	
    	
    }
    
    
    
    private int getProductAmountInCard(Product product) {
        int count = 0;
        for (Product p : receivedProductList) {
            if (p.equals(product)) {
                count++;
            }
        }
        return count;
    }
    
    
    
	
	
	
	
	
	
	
	
}
