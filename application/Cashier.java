package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Cashier extends User {

	
	Cashier(int id, String username, String name, String surname, String role) 
	{
		super(id, username, name, surname, role);
		
	}

	@Override
	void displayInterface(ActionEvent event) {
		try {
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("CashierView.fxml"));
	        Parent root = loader.load();
	        CashierController cashierController = loader.getController();
	        cashierController.displayName(username);
	    
	 

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
		
	}


	
	
	
	
	

}
