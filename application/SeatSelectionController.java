package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class SeatSelectionController {
	
	private int movieId;
	private String sessionDetails;
	private String hallName;
	private int sessionId;
	private List<Integer>selectedSeatIdList = new ArrayList<>();
	private ShoppingCard card = new ShoppingCard();
	
	
	@FXML
	private Button backButton;
	
	@FXML
    private Button buyButton;


	@FXML
	private Label movieNameLabel;

	@FXML
	private GridPane seatGridPane;
	
	@FXML
    private TextArea textAreaForCard;


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
    void buyButtonPressed(ActionEvent event) {
		if (selectedSeatIdList.isEmpty()) {
			
			Alert alert= new Alert(AlertType.ERROR);
	    	alert.setTitle("Error Message");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Please choose your seat.!");
	    	alert.showAndWait();
	
	    } else {
	    	
			try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculatePricesView.fxml"));
		        Parent root = loader.load();
		        CalculatePricesController calculatePricesController = loader.getController();
		        calculatePricesController.setShoppingCard(this.card);
		        
		        backButton.getScene().getWindow().hide();
	

		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.show();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    	
	    	
	    	
		
	    }

    }

	
	
	public void updateCard() {
		
		String selectedSeats="";
		
		for(int seatId: card.getEnteredSeats()) {
			MovieDatabase movieDatabase = new MovieDatabase();
			selectedSeats+=(movieDatabase.getSeatForSeatId(seatId).getSeatNumber()+",");
			
		}
		
		textAreaForCard.setEditable(false);
		textAreaForCard.setFont(Font.font(20));
		textAreaForCard.setText("Selected seats:\n"+ selectedSeats +"\n" + "\nTotal Price: "+ card.getTotalPrice()+" TL");
		
	
		
	}
	
	
	
	
	
	
	public void setSessionInfo(int movieId, String sessionDetails) {
		this.movieId= movieId;
		this.sessionDetails=sessionDetails;
		this.hallName=extractHallName(sessionDetails);
		textAreaForCard.setEditable(false);
		MovieDatabase movieDatabase = new MovieDatabase();
		this.sessionId=movieDatabase.getSessionId(movieId,sessionDetails);
		
		
		displaySeats();
		
		
	}
	
	
	public String extractHallName(String sessionDetails) {
		if(sessionDetails.toLowerCase().contains("hall_a")) {
			return "Hall_A";
			
		}else {
			return "Hall_B";
		}
		
		
	}
	
	
	public void displaySeats() {
		
		seatGridPane.getChildren().clear();
		seatGridPane.setHgap(10);
		seatGridPane.setVgap(10);
		MovieDatabase movieDatabase = new MovieDatabase();
		List<Seat> seatList = movieDatabase.getSeatsForSession(sessionId);
		List<Integer>reservedSeats=movieDatabase.getReservedSeatId(sessionId);
		
		
		
		
		
		int columnNumber =hallName.equals("Hall_A") ? 4 : 8;
		
		int rowIndex=0;
		int columnIndex=0;
		
		for (Seat seat :seatList) {
			String seatName = seat.getSeatNumber();
			Button seatButton = new Button(seatName);
			seatButton.setPrefSize(50, 50);
			
			if(reservedSeats.contains(seat.getSeatId())) {
				seatButton.setStyle("-fx-background-color :red");
				seatButton.setDisable(true);
			}else {
				seatButton.setStyle("-fx-background-color :green");
				seatButton.setOnAction(e -> selectSeat(seatButton,seat.getSeatId()));
			}
			
			seatGridPane.add(seatButton, columnIndex, rowIndex);
			
			columnIndex++;
			if(columnIndex >= columnNumber) {
				columnIndex=0;
				rowIndex++;
				
			}
			
		}
		
		
		
	}
	
	
	public void selectSeat(Button seatButton, int seatId) {
		MovieDatabase movieDatabase = new MovieDatabase();
		Seat seat =movieDatabase.getSeatForSeatId(seatId);
	
		if (selectedSeatIdList.contains(seatId)){
			selectedSeatIdList.remove(Integer.valueOf(seatId));
			seatButton.setStyle("-fx-background-color :green");
			card.deleteSeat(seat);
			
			
		}else {
			selectedSeatIdList.add(seatId);
			seatButton.setStyle("-fx-background-color :blue");
			card.addSeat(seat);
			
		}
		
		updateCard();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
