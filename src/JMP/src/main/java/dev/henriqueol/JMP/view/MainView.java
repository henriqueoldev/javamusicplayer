package dev.henriqueol.JMP.view;


import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.controller.PlaylistController;
import dev.henriqueol.JMP.view.Controls.BottomControls;
import dev.henriqueol.JMP.view.Controls.PlayerControls;
import dev.henriqueol.JMP.view.Controls.PlaylistControls;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends VBox{
	
	public MainView() {
		PlayerController playerController = new PlayerController();
		PlaylistController playlistController = new PlaylistController(playerController);
		
		PlayerControls playerControls = new PlayerControls(playerController);
		PlaylistControls playlistControls = new PlaylistControls(playerController, playlistController);
		BottomControls bottomControls = new BottomControls(playerController);
		
		this.getChildren().addAll(playerControls, playlistControls.getplaylistControlsPane(), bottomControls);
		
		playerController.setPlaylistView(playlistControls);
		playerController.setPlayerView(playerControls);
		
		VBox.setVgrow(playlistControls.getplaylistControlsPane(), Priority.ALWAYS);
		AnchorPane.setBottomAnchor(this, 0d);
		AnchorPane.setTopAnchor(this, 0d);
	}
}
