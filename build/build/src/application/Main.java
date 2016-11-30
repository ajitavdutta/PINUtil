package application;
	
import application.controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainScreen.fxml"));
			loader.setController(new MainScreenController());
			final Parent root = (Parent) loader.load();
			final Scene scene = new Scene(root);
			
			//scene.getStylesheets().add(getClass().getResource("skin/modena/modena.css").toExternalForm());
			
			primaryStage.setTitle("PIN Block/PIN Calculator");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
