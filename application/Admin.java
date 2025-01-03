package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Admin extends User {

	Admin(int id, String username, String name, String surname, String role) 
	{
		super(id, username, name, surname, role);
	}

	
	
	@Override
	void displayInterface(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	
	
	

}
