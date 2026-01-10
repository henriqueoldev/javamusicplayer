package dev.henriqueol.JMP.view.Controls;

import java.util.ArrayList;
import java.util.List;

import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.controller.PlaylistController;
import dev.henriqueol.JMP.model.MediaItem;
import dev.henriqueol.JMP.model.UIDefaults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
	private ListView<MediaItem> playlistQueueListView = new ListView<MediaItem>();
	
	public PlaylistControls(PlayerController playerController, PlaylistController playlistController) {
		this.playlistController = playlistController;
		this.playerController = playerController;
		HBox controlsHBox = new HBox();
		
		setButtons();
		controlsHBox.getChildren().addAll(addItem, openPlaylist, savePlaylist);
		controlsHBox.setAlignment(Pos.CENTER_RIGHT);
		
		playlistControls.getChildren().addAll(playlistTitleLabel, controlsHBox);
		playlistControls.setAlignment(Pos.CENTER);
		HBox.setHgrow(controlsHBox, Priority.ALWAYS);
		
		playlistQueueListView.getStyleClass().add("playlist-listview");
		playlistQueueListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		playlistItems.getStyleClass().add("playlist-items-panel");
		playlistPanel.getStyleClass().add("playlist-controls");
		
		playlistPanel.getChildren().addAll(playlistControls, playlistQueueListView);
		
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
		
		//Playlist item Handles
		playlistQueueListView.setOnMouseClicked((e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getClickCount() == 2) {
					playMediaAtSelectedIndex();
				}
			} else if (e.getButton() == MouseButton.SECONDARY) {
				
			}
		});
		
		playlistQueueListView.setOnKeyPressed((e) -> {
			switch (e.getCode()) {
				case DELETE: {
					removeSelectedMedia();
					break;
				}
				case ENTER: {
					playMediaAtSelectedIndex();
				}
				default: {
					
				}
			}
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
	
	private void playMediaAtSelectedIndex() {
		playerController.playMediaItem(playlistQueueListView.getSelectionModel().getSelectedIndex());
	}
	
	private void removeSelectedMedia() {
		ObservableList<Integer> selectedIndices = playlistQueueListView.getSelectionModel().getSelectedIndices();
		int lastIndex = selectedIndices.getLast();
		playerController.removeMediaItems(selectedIndices);
		if (!playlistQueueListView.getItems().isEmpty()) {
			if (lastIndex + 1 < playlistQueueListView.getItems().size()) {
				playlistQueueListView.getSelectionModel().select(lastIndex);
			} else {
				playlistQueueListView.getSelectionModel().selectFirst();
			}
		}
	}
	
	public AnchorPane getplaylistControlsPane() {
		return playlistControlsPane;
	}
	
	public void updatePlaylist(ArrayList<MediaItem> activeQueue) {
		playlistQueueListView.setItems(FXCollections.observableArrayList(activeQueue));
	}
}
