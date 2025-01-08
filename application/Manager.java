package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Manager extends User {

	Manager(int id, String username, String name, String surname, String role) 
	{
		super(id, username, name, surname, role);
		
	}

	

	@Override
	void displayInterface(ActionEvent event) {
		
		try {
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerView.fxml"));
	        Parent root = loader.load();
	        ManagerController managerController = loader.getController();
	        managerController.displayName(username);
	    
	 

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}


	

}
