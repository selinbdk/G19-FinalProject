package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventoryController implements Initializable {
	
	private List<Product>productList = new ArrayList<>();
	
	
	
		
	
	    @FXML
	    private Button backButton;
	
	 	@FXML
	    private TableColumn<Product, String> productNameColumn;

	    @FXML
	    private TableView<Product> productsTable;

	    @FXML
	    private TableColumn<Product, Integer> stockQuantityColumn;
	    
	    @FXML
	    private TextField stockAmountField;


	    @FXML
	    private Button updateButton;

	    
	    @FXML
	    void backButtonPressed(ActionEvent event) {
	    	try {
				
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            currentStage.close();
				
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerView.fxml"));
		        Parent root = loader.load();
		        ManagerController managerController = loader.getController();
		      

		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.show();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    	
	    	
	    	

	    }
	    
	    
	    
	    
	    
	    @FXML
	    void updateButtonPressed(ActionEvent event) {
	    	
	    	MovieDatabase movieDatabase = new MovieDatabase();
	    	Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

	        if (selectedProduct == null) {
	        	
	        	Alert alert= new Alert(AlertType.ERROR);
			    alert.setTitle("Error Message");
			    alert.setHeaderText(null);
			    alert.setContentText("Please select a product that you want to update!");
			    alert.showAndWait();
			    	
			    return;
	    
	        }

	        try {
	            
	            int amount = Integer.parseInt(stockAmountField.getText());
	            if (amount <= 0) {

		        	Alert alert= new Alert(AlertType.ERROR);
				    alert.setTitle("Error Message");
				    alert.setHeaderText(null);
				    alert.setContentText("Please enter valid number!");
				    alert.showAndWait();
				    	
				    return;
	      
	            }

	         
	            movieDatabase.updateStockQuantity(selectedProduct.getName(), amount);
	            loadProducts();

	            Alert alert= new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Success Message");
	        	alert.setHeaderText(null);
	        	alert.setContentText("You updated the stock amount successfully!");
	        	alert.showAndWait();
	           
	            stockAmountField.clear(); 
	            
	        } catch (NumberFormatException e) {
	        	
	        	Alert alert= new Alert(AlertType.ERROR);
			    alert.setTitle("Error Message");
			    alert.setHeaderText(null);
			    alert.setContentText("Please enter valid number!");
			    alert.showAndWait();
	
	            
	        }
	    	
	    	

	    }
	    
	
	    
		    
		    
		 
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		    stockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stockAmount"));
		    
		    loadProducts();
		    
			
		}
			
			
		private void loadProducts() {
			MovieDatabase movieDatabase = new MovieDatabase();
			this.productList= movieDatabase.getAllProducts();
		
		    ObservableList<Product> observableProductList = FXCollections.observableArrayList(productList);
		    productsTable.setItems(observableProductList);
			
		}	
		
		
		

		
			
			
			
		
	
	

}
