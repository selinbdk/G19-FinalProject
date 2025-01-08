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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonnelsViewController implements Initializable {
	
	

		private List<Personnel>personnelList = new ArrayList<>();
		private String currentUsername;
		
		
	    @FXML
	    private Button backButton;

	    @FXML
	    private Button fireButton;

	    @FXML
	    private TableColumn<Personnel, String> firstNameColumn;

	    @FXML
	    private Button hireButton;

	    @FXML
	    private TableColumn<Personnel, String> lastNameColumn;

	    @FXML
	    private TextField nameTextField;

	    @FXML
	    private TableColumn<Personnel, String> passwordColumn;

	    @FXML
	    private TextField passwordTextField;

	    @FXML
	    private TableColumn<Personnel, String> roleColumn;
	    
	    @FXML
	    private TableView<Personnel> personnelsTable;

	    @FXML
	    private TextField roleTextField;

	    @FXML
	    private TextField surnameTextField;

	    @FXML
	    private TableColumn<Personnel, String> usernameColumn;

	    @FXML
	    private TextField usernameTextField;
	   

	    @FXML
	    void backButtonPressed(ActionEvent event) {
	    	
	    	
	    	try {
				
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            currentStage.close();
				
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerView.fxml"));
		        Parent root = loader.load();
		        ManagerController managerController = loader.getController();
		        managerController.displayName(currentUsername);
		      

		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.show();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    	

	    }

	    @FXML
	    void fireButtonPressed(ActionEvent event) {
	    	
	    	MovieDatabase movieDatabase = new MovieDatabase();
	    	
	    	Personnel selectedUser = personnelsTable.getSelectionModel().getSelectedItem();
	        if (selectedUser == null) {
	        	
	        	Alert alert= new Alert(AlertType.ERROR);
			    alert.setTitle("Error Message");
			    alert.setHeaderText(null);
			    alert.setContentText("You should select a personnel!");
			    alert.showAndWait();
			    	
	            return;
	        	
	 
	        } else {
	        	
	        	if(selectedUser.getUsername().equals(currentUsername)) {

		        	Alert alert= new Alert(AlertType.ERROR);
				    alert.setTitle("Error Message");
				    alert.setHeaderText(null);
				    alert.setContentText("You can't fire yourself!");
				    alert.showAndWait();
				    	
		            return;
	        		
	        	}else {
	        		personnelList.remove(selectedUser);
		            movieDatabase.deletePersonnel(selectedUser);
		            loadPersonnels();
	        
	        	}
	        	
	            
	            
	        }
	    

	    }

	    @FXML
	    void hireButtonPressed(ActionEvent event) {
	    	
	    	
	    	MovieDatabase movieDatabase = new MovieDatabase();
	    	
	    	
	    	
	    	String firstName = nameTextField.getText().trim();
	        String lastName = surnameTextField.getText().trim();
	        String username = usernameTextField.getText().trim();
	        String password = passwordTextField.getText().trim();
	        String role = roleTextField.getText().trim();
	    	
	        

	    	for(Personnel personnel : personnelList) {
	    		
	    		if(personnel.getUsername().equals(username)) {
	    			
	    			Alert alert= new Alert(AlertType.ERROR);
				    alert.setTitle("Error Message");
				    alert.setHeaderText(null);
				    alert.setContentText("Duplicate username!");
				    alert.showAndWait();
				    	
		            return;
	  
	    			
	    		}
	    		
	
	    	}
	        
	      

	        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()|| !isValidRole(role)) {
	            
	        	Alert alert= new Alert(AlertType.ERROR);
			    alert.setTitle("Error Message");
			    alert.setHeaderText(null);
			    alert.setContentText("You should fill in all the fields and enter the valid role!");
			    alert.showAndWait();
			    	
	            return;
	        }
	        
	        
	        
	        
	        if (!firstName.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$") || !lastName.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$") || !role.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$")){
            	
	            Alert alert= new Alert(AlertType.ERROR);
			    alert.setTitle("Error Message");
			    alert.setHeaderText(null);
			    alert.setContentText("Name,Surname or Role shouldn't contain number or special characters!");
			    alert.showAndWait();
			    	
			    return;
		    		
	           
	        }
	        
	        
	        int newId =getMaxId()+1;
	        Personnel newPersonnel = new Personnel(newId,firstName, lastName,username, password, role);
	        personnelList.add(newPersonnel);
	        
	        movieDatabase.addPersonnel(newPersonnel);
	        
	        
	        nameTextField.clear();
	        surnameTextField.clear();
	        usernameTextField.clear();
	        passwordTextField.clear();
	        roleTextField.clear();
	        
	       
	        loadPersonnels();
	    	
	

	    }
	    
	    
	    
	    public int getMaxId() {
	    	MovieDatabase movieDatabase = new MovieDatabase();
	    	return movieDatabase.getMaxIdForUser();
	    		
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

	        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
	        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
	        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
	        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
		    
		    loadPersonnels();
		   
			
		
		
			
		}
		
	
		private void loadPersonnels() {
			MovieDatabase movieDatabase = new MovieDatabase();
			this.personnelList= movieDatabase.getAllPersonnel();
		
		    ObservableList<Personnel> observablePersonnelList = FXCollections.observableArrayList(personnelList);
		    personnelsTable.setItems(observablePersonnelList);
			
		}	
		
		
		public void setCurrentUsername(String username) {
			
			this.currentUsername=username;
	
		}

	
	
		public boolean isValidRole(String role) {
	        if (role == null) {
	            return false; 
	        }
	        
	        String roleWithLowerCase = role.toLowerCase();
	        return (roleWithLowerCase.equals("admin") || roleWithLowerCase.equals("manager") || roleWithLowerCase.equals("cashier"));
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
