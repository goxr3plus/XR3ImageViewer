package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The Main Scene of the Application
 * 
 * @author GOXR3PLUS
 *
 */
public class MainScene extends BorderPane implements Initializable {

	@FXML
	private Label informationLabel;

	@FXML
	private MenuItem openFile;

	@FXML
	private Menu websiteButton;

	@FXML
	private StackPane centerStackPane;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label dragAndDropLabel;

	// FileChooser
	FileChooser fileChooser = new FileChooser();
	ImageView imageView = new ImageView();

	/**
	 * Constructor
	 */
	public MainScene() {

		// FXMLLOADER
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Main.fxml + "MainWindow.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", "*.jpg", "*.png"));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Open
		openFile.setOnAction(a -> chooseFile());

		// WebSite
//		websiteButton.setOnAction(a -> {
//			try {
//				Runtime.getRuntime().exec("cmd /c start http://goxr3plus.co.nf");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		});

		// centerStackPane
		centerStackPane.setOnDragOver(drag -> {
			if (drag.getDragboard().hasFiles())
				drag.acceptTransferModes(TransferMode.LINK);
		});

		centerStackPane.setOnDragDropped(drag -> {
			for (File file : drag.getDragboard().getFiles()) {
				// supported?
				if (fileIsSupported(file)) {

					Image image = new Image("FILE:" + file.getAbsolutePath());
					imageView.setFitWidth(image.getWidth());
					imageView.setFitHeight(image.getHeight());
					imageView.setImage(image);
					updateInfoLabel(image);
					break;
				}

				drag.setDropCompleted(true);
			}

			drag.consume();
		});

		// ImageView
		scrollPane.setContent(imageView);

		// Drag And Drop Label
		dragAndDropLabel.visibleProperty().bind(imageView.imageProperty().isNull());
	}

	/**
	 * Check if the given file extension is supported by the application
	 * 
	 * @param file
	 * @return
	 */
	public boolean fileIsSupported(File file) {
		String extension = null;
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');

		// if name is not empty and it has an extension
		if (i > 0 && i < fileName.length() - 1)
			extension = fileName.substring(i + 1).toLowerCase();

		System.out.println(extension);

		// Check the extension
		if ("png".equals(extension) || "jpg".equals(extension) || "jpeg".equals(extension) || "bmp".equals(extension))
			return true;

		return false;
	}

	/**
	 * Opens a ChooseDialog so the user can choose a file from the System
	 * 
	 * @return
	 */
	public File chooseFile() {
		File file = fileChooser.showOpenDialog(Main.stage);
		if (file != null) {
			Image image = new Image("FILE:" + file.getAbsolutePath());
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
			imageView.setImage(image);
			updateInfoLabel(image);
			return file;
		} else
			return null;

	}

	/**
	 * Updating the information label with the given image
	 * 
	 * @param image
	 */
	public void updateInfoLabel(Image image) {
		informationLabel.setText(
				"Width:" + image.getWidth() + " ||  Height:" + image.getHeight() + " ||  Smooth:" + image.isSmooth());
	}

}
