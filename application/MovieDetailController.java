package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MovieDetailController {
	
	
	private int movieId;

    @FXML
    private Button backButton;

    @FXML
    private Button bookButton;
    
    @FXML
    private TextArea summaryField;

    @FXML
    private ImageView posterImage;

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
    void bookButtonPressed(ActionEvent event) {

		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessionSelectionView.fxml"));
	        Parent root = loader.load();
	        
	        SessionSelectionController sessionSelectionController = loader.getController();
	        sessionSelectionController.setMovieId(movieId);
	       
	        
	        backButton.getScene().getWindow().hide();

	      
	   

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
    	
    	
    	
    	
    	

    }
    
    
    public void displayMovieDetails(int movieId, String imagePath,String summaryPath) {
    	
    	this.movieId=movieId;
    	
    	summaryField.setEditable(false);
    	
    	summaryField.setWrapText(true);
    	
    	//String imagePath ="/application/images/pulpFiction.jpeg";
    	//String summaryPath= "/application/summaries/film1.txt";
    	
		String summaryContent;
		try {
			summaryContent = Files.readString(Paths.get(getClass().getResource(summaryPath).toURI()));
			summaryField.setText(summaryContent);
		
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
	
    	
		
		Image moviePoster= new Image(getClass().getResource(imagePath).toExternalForm());
		
		posterImage.setImage(moviePoster);
		posterImage.setFitHeight(253);
		posterImage.setFitWidth(200);
		posterImage.setPreserveRatio(true);
    	
    	
    
    	
    	
    	
    }
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
