package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class LoginController {

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;
    
    @FXML
    private Button loginButton;

    @FXML
    void loginButtonPressed(ActionEvent event) {
    	
    	Authentication authentication = new Authentication();
    	String enteredUsername= usernameTextField.getText();
    	String enteredPassword= passwordTextField.getText();
    	
    	if(authentication.checkValidLogin(enteredUsername, enteredPassword)) 
    	{
    		
    		showSuccessAlert();
    		
    		
    		User user = authentication.createUserObject(enteredUsername,enteredPassword);
    		
    		if(user!=null) {
    			user.displayInterface(event);
    		}
    		
    	
    	}else {
    		showErrorAlert();
    	}
    	
    }
    
    void showErrorAlert() {
    	Alert alert= new Alert(AlertType.ERROR);
    	alert.setTitle("Error Message");
    	alert.setHeaderText(null);
    	alert.setContentText("You entered wrong username or password!");
    	alert.showAndWait();
    	
    }
    
    void showSuccessAlert() {
    	Alert alert= new Alert(AlertType.INFORMATION);
    	alert.setTitle("Welcome Group19 Cinema Center");
    	alert.setHeaderText(null);
    	alert.setContentText("You have successfully logged in.");
    	alert.showAndWait();
    	
    	
    }

    
    
    
    
    
    
    
    
}
