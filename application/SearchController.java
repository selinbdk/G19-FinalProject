package application;



import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SearchController implements Initializable {

	
	private String username;
	@FXML
    private Button backButton;

	@FXML
    private TextField searchGenreTextField;

    @FXML
    private TextField searchTitleTextField;

    @FXML
    private GridPane movieGridPane;
    
    
    @FXML
    void backButtonPressed(ActionEvent event) {

    	try {
			
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
			
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("CashierView.fxml"));
	        Parent root = loader.load();
	        CashierController cashierController = loader.getController();
	        cashierController.displayName(this.username);
	    
	 

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		


    }

    
    
    public void setUsername(String username) {
    	this.username=username;
    	
    }
   
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		searchTitleTextField.textProperty().addListener((observable,oldvalue,enteredvalue) ->{searchMovie();});
		searchGenreTextField.textProperty().addListener((observable,oldvalue,enteredValue)->{searchMovie();});
		
		
		MovieDatabase movieDatabase = new MovieDatabase();
		
		

		
		
	
		displayMoviePosters(movieDatabase.getAllMovies());
		
		
		
		
		
		
		
		
	
	}
	
	
	public void searchMovie() {
		MovieDatabase movieDatabase = new MovieDatabase();
		
		String enteredGenre=searchGenreTextField.getText().toLowerCase();
    	String enteredTitle=searchTitleTextField.getText().toLowerCase();
    	
		List<Movie>filteredMovieList= new ArrayList<>();
		List<Movie>allMovies= new ArrayList<>();
		allMovies= movieDatabase.getAllMovies();
		
		for(Movie movie : allMovies) {
			
			boolean genreChecker= (enteredGenre.isEmpty() || movie.getMovieGenre().toLowerCase().contains(enteredGenre));
			boolean titleChecker = (enteredTitle.isEmpty() || movie.getMovieTitle().toLowerCase().contains(enteredTitle));
			
			if(genreChecker && titleChecker == true) {
				filteredMovieList.add(movie);
				
			}
			
			
			
		}
		
		displayMoviePosters(filteredMovieList);
		
		
	}
	
	
	
	
	
	
	public void displayMoviePosters(List<Movie>movieList) {
		
		movieGridPane.getChildren().clear();
		
		int column=0;
		int row=0;
		
		for(Movie movie : movieList) 
		{
			String posterPath = movie.getposterUrl();
			ImageView moviePoster = new ImageView(new Image(getClass().getResource(posterPath).toExternalForm()));
			
			
			movieGridPane.add(moviePoster, column, row);
			moviePoster.setFitHeight(250);
			moviePoster.setFitWidth(200);
			//moviePoster.setPreserveRatio(true);
			movieGridPane.setHgap(60);
			movieGridPane.setVgap(150);
			movieGridPane.setPadding(new Insets(20, 20, 20, 20));
			movieGridPane.setHalignment(moviePoster, HPos.CENTER); 
	        movieGridPane.setValignment(moviePoster, VPos.CENTER); 
			
			
			moviePoster.setOnMouseClicked(event->openMovieDetail(movie));
			
			
			
			column++;
			if(column==3) 
			{
				row++;
				column=0;
			}
		
		
		}
		
		
		
		
		if(movieList.isEmpty()) {
			Label notFoundMessage = new Label("The movie was not found");
			movieGridPane.add(notFoundMessage,1,1);
			
			
		}
		
		
		
		
	}

    
    
	public void openMovieDetail(Movie movie) {
		
		
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieDetailView.fxml"));
	        Parent root = loader.load();
	        
	        MovieDetailController movieDetailController = loader.getController();
	        
	        backButton.getScene().getWindow().hide();

	      
	        movieDetailController.displayMovieDetails(movie.getMovieId(),movie.getposterUrl(), movie.getSummaryFilePath());

	        Stage stage = new Stage();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
    	
    
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
