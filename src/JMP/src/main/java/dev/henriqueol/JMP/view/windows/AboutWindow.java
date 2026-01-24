package dev.henriqueol.JMP.view.windows;

import java.net.URI;

import dev.henriqueol.JMP.controller.ConfigController;
import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.model.UIDefaults;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AboutWindow extends Stage {
	ConfigController configController;
	private Scene scene;
	private IconController iconController = new IconController();
	private VBox mainBox = new VBox(UIDefaults.UI_ITEM_GAP);
	private HBox bottomBtnBox = new HBox(UIDefaults.UI_ITEM_GAP);
	private ImageView favIcon;
	private Label appNameLabel = new Label();
	private Label appDescriptionLabel = new Label();
	private Label libsLabel = new Label();
	private Button githubBtn = new Button("Github");
	private Button helpButton = new Button("Help");
	
	public AboutWindow(ConfigController configController) {
		this.configController = configController;
		appNameLabel.setText(configController.getAppName());
		appDescriptionLabel.setText(configController.getAppDescription());
		String libsString = "Open Source Libs: \n";
		for (Object lib : configController.getAppLibs()) {
			libsString += String.format("• %s\n", lib.toString());
		}
		libsLabel.setText(libsString);
		favIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_64.png")));
		
		appNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		appDescriptionLabel.setWrapText(true);
		appDescriptionLabel.setTextAlignment(TextAlignment.CENTER);
		
		//Buttons
		githubBtn.setGraphic(new ImageView(iconController.getIconImage("github", UIDefaults.UI_ICON_SMALL.getWidth(), UIDefaults.UI_ICON_SMALL.getHeight())));
		helpButton.setGraphic(new ImageView(iconController.getIconImage("help", UIDefaults.UI_ICON_SMALL.getWidth(), UIDefaults.UI_ICON_SMALL.getHeight())));
		HBox helpBtnBox = new HBox();
		HBox.setHgrow(helpBtnBox, Priority.ALWAYS);
		helpBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
		helpBtnBox.getChildren().add(helpButton);
		VBox.setVgrow(bottomBtnBox, Priority.ALWAYS);
		bottomBtnBox.setAlignment(Pos.BOTTOM_LEFT);
		bottomBtnBox.getChildren().addAll(githubBtn, helpBtnBox);
		
		mainBox.setAlignment(Pos.TOP_CENTER);
		mainBox.getChildren().addAll(favIcon, appNameLabel, appDescriptionLabel, libsLabel, bottomBtnBox);
		
		scene = new Scene(mainBox, 400, 400);
		scene.getStylesheets().add(configController.getAppStylePath().toString());
		
		//Button Handles
		githubBtn.setOnAction((e) -> {openURIInBrowser(configController.getAppGithubURI());});
		helpButton.setOnAction((e) -> {openURIInBrowser(configController.getAppHelpURI());});
		
		//Add all icon sizes
        try {
			this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_16.png")));
			this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_32.png")));
			this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_64.png")));
			this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_128.png")));
			this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_512.png")));
			this.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_full.png")));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error Loading App Icons");
		}
		
		this.setScene(scene);
		this.setResizable(false);
		this.setOnCloseRequest((e) -> {
			this.hide();
			e.consume();
		});
	}
	
	private void openURIInBrowser(URI link) {
		configController.getAppHostsServices().showDocument(link.toString());
	}
}
