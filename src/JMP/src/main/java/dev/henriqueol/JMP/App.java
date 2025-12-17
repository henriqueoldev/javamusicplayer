package dev.henriqueol.JMP;

import dev.henriqueol.JMP.model.Defaults;
import dev.henriqueol.JMP.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application implements Defaults {

    @Override
    public void start(Stage stage) {
        var scene = new Scene(new MainView().getPane(), INITIAL_WINDOW_WIDTH, INITIAL_WINDOW_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/app.css").toString());
        
        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.setMinWidth(INITIAL_WINDOW_WIDTH);
        stage.setMinHeight(INITIAL_WINDOW_HEIGHT);
        stage.setFullScreenExitHint("F11 to exit fullscreen");
        
        //Key Handles
        scene.setOnKeyPressed((key) -> {
        	if (key.getCode() == KeyCode.F11) {
				stage.setFullScreen(stage.isFullScreen() == true ? false: true);
			}
        });
        
        //Add all icon sizes
        try {
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_16.png")));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_32.png")));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_64.png")));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_128.png")));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_512.png")));
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Favicon/icon_full.png")));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error Loading App Icons");
		}
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}