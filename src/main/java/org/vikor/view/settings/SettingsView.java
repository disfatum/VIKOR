package org.vikor.view.settings;
	
import java.io.IOException;

import org.vikor.view.mainwindow.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class SettingsView extends Application {
	static Stage primaryStage1;
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/SettingsV.fxml"));
		primaryStage.setTitle("");
		primaryStage.initModality(Modality.WINDOW_MODAL);
		 
         // Specifies the owner Window (parent) for new window
		primaryStage.initOwner(Main.getPrimaryStage());
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.setScene(new Scene(root, 475, 616));
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