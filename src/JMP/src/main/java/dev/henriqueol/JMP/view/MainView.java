package dev.henriqueol.JMP.view;


import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.controller.PlaylistController;
import dev.henriqueol.JMP.view.Controls.PlayerControls;
import dev.henriqueol.JMP.view.Controls.PlaylistControls;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView{
	private VBox mainPane = new VBox();
	
	public MainView() {
		PlayerController playerController = new PlayerController();
		PlaylistController playlistController = new PlaylistController(playerController);
		
		PlayerControls playerControls = new PlayerControls(playerController);
		PlaylistControls playlistControls = new PlaylistControls(playerController, playlistController);
		
		mainPane.getChildren().addAll(playerControls.getControls(), playlistControls.getplaylistControlsPane());
		
		playerController.setPlaylistView(playlistControls);
		playerController.setPlayerView(playerControls);
		
		VBox.setVgrow(playlistControls.getplaylistControlsPane(), Priority.ALWAYS);
		AnchorPane.setBottomAnchor(mainPane, 0d);
		AnchorPane.setTopAnchor(mainPane, 0d);
	}
	
	public VBox getPane(){
		return mainPane;
	}
}
