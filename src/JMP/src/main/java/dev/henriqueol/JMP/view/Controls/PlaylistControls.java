package dev.henriqueol.JMP.view.Controls;

import java.util.ArrayList;
import java.util.List;

import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.controller.PlaylistController;
import dev.henriqueol.JMP.model.MediaItem;
import dev.henriqueol.JMP.model.UIDefaults;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PlaylistControls implements UIDefaults{
	private PlayerController playerController;
	private PlaylistController playlistController;
	private IconController icons = new IconController();
	private AnchorPane playlistControlsPane = new AnchorPane();
	
	private Label playlistTitleLabel = new Label("Playlist");
	
	private Button addItem = new Button();
	private Button openPlaylist = new Button();
	private Button savePlaylist = new Button();
	
	private VBox playlistPanel = new VBox();
	private HBox playlistControls = new HBox();
	private VBox playlistItems = new VBox(UI_ITEM_GAP);
	
	public PlaylistControls(PlayerController playerController, PlaylistController playlistController) {
		this.playlistController = playlistController;
		this.playerController = playerController;
		HBox controlsHBox = new HBox();
		ScrollPane itemsScroll;
		
		setButtons();
		controlsHBox.getChildren().addAll(addItem, openPlaylist, savePlaylist);
		controlsHBox.setAlignment(Pos.CENTER_RIGHT);
		
		playlistControls.getChildren().addAll(playlistTitleLabel, controlsHBox);
		playlistControls.setAlignment(Pos.CENTER);
		HBox.setHgrow(controlsHBox, Priority.ALWAYS);
		
		playlistItems.getStyleClass().add("playlist-items-panel");
		
		itemsScroll = new ScrollPane(playlistItems);
		itemsScroll.setFitToWidth(true);
		playlistPanel.getStyleClass().add("playlist-controls");
		
		playlistPanel.getChildren().addAll(playlistControls, itemsScroll);
		
		AnchorPane.setLeftAnchor(playlistPanel, 0d);
		AnchorPane.setRightAnchor(playlistPanel, 0d);
		AnchorPane.setBottomAnchor(playlistPanel, 0d);
		AnchorPane.setTopAnchor(playlistPanel, 0d);
		playlistControlsPane.getChildren().add(playlistPanel);
		
		//Handles
		addItem.setOnMouseClicked((e) -> {
			addQueueItem();
		});
		savePlaylist.setOnMouseClicked((e) -> {
			playlistController.savePlaylist();
		});
		openPlaylist.setOnMouseClicked((e) -> {
			playlistController.loadPlaylist();
		});
	}
	
	private void setButtons() {
		//Setting the Buttons Icons
		addItem.setGraphic(new ImageView(icons.getIconImage("plus", UI_ICON_SMALLER.getWidth(), UI_ICON_SMALLER.getHeight())));
		openPlaylist.setGraphic(new ImageView(icons.getIconImage("folder", UI_ICON_SMALLER.getWidth(), UI_ICON_SMALLER.getHeight())));
		savePlaylist.setGraphic(new ImageView(icons.getIconImage("save", UI_ICON_SMALLER.getWidth(), UI_ICON_SMALLER.getHeight())));
		
		//Button Widths
		addItem.setPrefWidth(UI_ICON_SMALLER.getWidth() + UI_ICON_PADDING);
		openPlaylist.setPrefWidth(UI_ICON_SMALLER.getWidth() + UI_ICON_PADDING);
		savePlaylist.setPrefWidth(UI_ICON_SMALLER.getWidth() + UI_ICON_PADDING);
	}
	
	private void addQueueItem() {
		try {
			List<MediaItem> loadedFiles = playlistController.loadMediaFiles();
			if (loadedFiles != null) {
				for (MediaItem mediaItem : loadedFiles) {
					playerController.enqueueMediaItem(mediaItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getplaylistControlsPane() {
		return playlistControlsPane;
	}
	
	public void updatePlaylist(ArrayList<MediaItem> activeQueue) {
		playlistItems.getChildren().clear();
		for (int i = 0; i < activeQueue.size(); i++) {
			playlistItems.getChildren().add(new MediaItemView(activeQueue.get(i), i, playerController).getMusicUINode());
		}
	}
}
