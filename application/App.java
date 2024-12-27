package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		
			Parent root= FXMLLoader.load(getClass().getResource("LoginView.fxml"));
			Scene scene =new Scene(root);
			stage.setTitle("Group19 CinemaCenter");
			stage.setScene(scene);
			stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
