package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SessionSelectionController {
	
	private int movieId;
	
	@FXML
    private ComboBox<String> dateComboBox;
	
	@FXML
	private Button backButton;

	@FXML
	private Label movieNameLabel;

	@FXML
	private ListView<String> sessionListView;

	@FXML
	void backButtonPressed(ActionEvent event) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SearchView.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	    }
	
	
	@FXML
    void dateSelected(ActionEvent event) 
	{
		String enteredDate = dateComboBox.getValue();
		if(enteredDate!=null) {
			setSessions(enteredDate);
		}
		
		
		
    }
	
	
	 @FXML
	   void sessionSelected(MouseEvent event) {
		 
		 String selectedSession = sessionListView.getSelectionModel().getSelectedItem();
		 
		 if(selectedSession != null) {
			 
				try {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("SeatSelectionView.fxml"));
			        Parent root = loader.load();
			        
			        SeatSelectionController seatSelectionController = loader.getController();
			     
			        seatSelectionController.setSessionInfo(movieId, selectedSession);
			        
			        backButton.getScene().getWindow().hide();

	
			        Stage stage = new Stage();
			        stage.setScene(new Scene(root));
			        stage.show();

			    } catch (IOException e) {
			        e.printStackTrace();
			    }
				
			 
			 
			 
			 
			 
			 
			 
		 }
		 

	 }

	
	
	public void setMovieId(int movieId) 
	{
		this.movieId=movieId;
		MovieDatabase movieDatabase = new MovieDatabase();
		String title= movieDatabase.getNameOfMovie(movieId);
		movieNameLabel.setText(title);
		
		setDateComboBox();
		
	}

	public void setDateComboBox() {
		sessionListView.getItems().clear();
		MovieDatabase movieDatabase = new MovieDatabase();
		List<String> dates =movieDatabase.getDateOfChosenMovie(movieId);
		dateComboBox.getItems().addAll(dates);
	
		
	}
	
	public void setSessions(String enteredDate) {
		
		sessionListView.getItems().clear();
		MovieDatabase movieDatabase = new MovieDatabase();
		List<String>sessionInfoList = movieDatabase.getSessionsOfChosenMovie(movieId, enteredDate);
		
		sessionListView.getItems().addAll(sessionInfoList);
		
	
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
}
