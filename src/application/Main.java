package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	static Stage stage;
	static String fxml = "/fxml/";
	MainScene mainScene;

	@Override
	public void start(Stage st) throws Exception {

		// Initialize
		mainScene = new MainScene();

		// Stage
		stage = new Stage();
		stage.setTitle("XR3ImageViewer ~~>(Made by GOXR3PLUS.CO.NF)");
		stage.setScene(new Scene(mainScene));
		stage.getScene().getStylesheets().add(getClass().getResource("/style/style1.css").toString());
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
