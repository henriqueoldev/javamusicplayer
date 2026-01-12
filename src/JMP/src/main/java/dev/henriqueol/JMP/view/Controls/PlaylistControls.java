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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
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
	
	private TextField playlistTitleLabel = new TextField("Playlist");
	
	private Button addItem = new Button();
	private Button openPlaylist = new Button();
	private Button savePlaylist = new Button();
	
	private VBox playlistPanel = new VBox();
	private HBox playlistControls = new HBox(UI_ITEM_GAP);
	private VBox playlistItems = new VBox(UI_ITEM_GAP);
	private ListView<MediaItem> playlistQueueListView = new ListView<MediaItem>();
	
	private PlaylistContextMenu playlistContextMenu = new PlaylistContextMenu();
	
	public PlaylistControls(PlayerController playerController, PlaylistController playlistController) {
		this.playlistController = playlistController;
		this.playerController = playerController;
		HBox controlsHBox = new HBox();
		
		setButtons();
		controlsHBox.getChildren().addAll(addItem, openPlaylist, savePlaylist);
		controlsHBox.setAlignment(Pos.CENTER_RIGHT);
		
		playlistControls.getChildren().addAll(playlistTitleLabel, controlsHBox);
		playlistControls.setAlignment(Pos.CENTER);
		
		playlistQueueListView.getStyleClass().add("playlist-listview");
		playlistQueueListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		playlistQueueListView.setContextMenu(playlistContextMenu);
		playlistItems.getStyleClass().add("playlist-items-panel");
		playlistPanel.getStyleClass().add("playlist-controls");
		
		HBox.setHgrow(playlistTitleLabel, Priority.ALWAYS);
		playlistTitleLabel.getStyleClass().add("playlist-title");
		
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
			playlistController.savePlaylist(playlistTitleLabel.getText());
		});
		openPlaylist.setOnMouseClicked((e) -> {
			playlistController.loadPlaylist();
			playlistTitleLabel.setText(playlistController.getActivePlaylistTitle());
		});
		
		//Playlist Context Menu Handles
		playlistContextMenu.setOnRemoveAction(() -> {
			removeSelectedMedia();
		});
		playlistContextMenu.setOnAddAction(() -> {
			addQueueItem();
		});
		playlistContextMenu.setOnMoveUpAction(() -> {
			playerController.moveMediaInQueue(playlistQueueListView.getSelectionModel().getSelectedIndex(), false);
		});
		playlistContextMenu.setOnMoveDownAction(() -> {
			playerController.moveMediaInQueue(playlistQueueListView.getSelectionModel().getSelectedIndex(), true);
		});
		
		//Playlist item Handles
		playlistQueueListView.setOnMouseClicked((e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getClickCount() == 2) {
					playMediaAtSelectedIndex();
				}
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
					break;
				}
				case DOWN: {
					if (e.isControlDown()) {
						playerController.moveMediaInQueue(playlistQueueListView.getSelectionModel().getSelectedIndex(), true);
					}
					break;
				}
				case UP: {
					if (e.isControlDown()) {
						playerController.moveMediaInQueue(playlistQueueListView.getSelectionModel().getSelectedIndex(), false);
					}
					break;
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
		if (!playlistQueueListView.getItems().isEmpty()) {
			ObservableList<Integer> selectedIndices = playlistQueueListView.getSelectionModel().getSelectedIndices();
			int lastIndex = selectedIndices.getLast();
			playerController.removeMediaItems(selectedIndices);
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
	
	public void setPlaylistSelectedIndex(int index) {
		playlistQueueListView.getSelectionModel().clearAndSelect(index);
	}
}
