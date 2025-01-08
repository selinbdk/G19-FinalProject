package application;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ManagerController implements Initializable {

    @FXML
    private Label appTitle;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    void logOutPressed(ActionEvent event) {
    	Alert alert= new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Bye bye!");
    	alert.setHeaderText(null);
    	alert.setContentText("You are logging out.");
    	
    	Optional<ButtonType>answer =alert.showAndWait();
    	
    	if(answer.isPresent() && answer.get()==ButtonType.OK) {
    		returnToLoginInterface(event);
    	}
    		

    }

    @FXML
    void pricesDiscountButtonPressed(ActionEvent event) {

    }

    @FXML
    void viewInventoryPressed(ActionEvent event) {
    	try {
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("InventoryView.fxml"));
	        Parent root = loader.load();
	        InventoryController inventoryController = loader.getController();
	      
	   

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
   
    	

    }

    @FXML
    void viewPersonnelsPressed(ActionEvent event) {
    	
		try {
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonnelsView.fxml"));
	        Parent root = loader.load();
	        PersonnelsViewController personnelsViewController = loader.getController();
	        personnelsViewController.setCurrentUsername(usernameLabel.getText());
	      

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
    	
    	
    	
    	
    	
    	

    }

    @FXML
    void viewRevenueTaxPressed(ActionEvent event) {

    }
    
    
    
    public void displayName(String username) {
        usernameLabel.setText(username); 
    }

	void returnToLoginInterface(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
    
    
    
    

}
