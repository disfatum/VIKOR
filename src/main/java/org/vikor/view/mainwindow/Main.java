package org.vikor.view.mainwindow;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class Main extends Application {
	static Stage primaryStage1;
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/VikorOverview.fxml"));
		primaryStage.setTitle("Vikor App");
		primaryStage.setScene(new Scene(root, 1477, 910));
		primaryStage.show();
		primaryStage1 = primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public static Stage getPrimaryStage() {
		return primaryStage1;
		
	}
}
