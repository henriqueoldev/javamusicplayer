package dev.henriqueol.JMP.view.Controls;

import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.model.MediaItem;
import dev.henriqueol.JMP.model.UIDefaults;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MediaItemView implements UIDefaults{
	private IconController icons = new IconController();
	private PlayerController playerController;
	private int queueIndex;
	private HBox musicUINode =  new HBox();
	private Label musicTitleLabel;
	private Button removeMusicBtn = new Button();
	
	public MediaItemView(MediaItem musicMediaItem, int queueIndex, PlayerController playerController) {
		this.queueIndex = queueIndex;
		this.playerController = playerController;
		musicTitleLabel = new Label(
				String.format(
						"%s %s %s", 
						musicMediaItem.getMediaName(),
						musicMediaItem.getMediaArtist(),
						musicMediaItem.getMediaAlbum()
				)
		);
		musicTitleLabel.setTooltip(new Tooltip(musicTitleLabel.getText()));
		musicTitleLabel.setMaxWidth(Double.MAX_VALUE);
		removeMusicBtn.setGraphic(new ImageView(icons.getIconImage("x", UI_ICON_SMALLER.getWidth(), UI_ICON_SMALLER.getHeight())));
		
		HBox.setHgrow(musicTitleLabel, Priority.ALWAYS);
		musicUINode.getChildren().addAll(musicTitleLabel, removeMusicBtn);
		musicUINode.setAlignment(Pos.CENTER);
		musicUINode.getStyleClass().add("playlistItem");
		
		//Handles
		musicUINode.setOnMouseClicked((e) -> {
			playSelf();
		});
		
		removeMusicBtn.setOnMouseClicked((e) -> {
			removeSelf();
		});
	}
	private void removeSelf() {
		playerController.removeMediaItem(queueIndex);
	}
	
	private void playSelf() {
		playerController.playMediaItem(queueIndex);
	}
	
	public int getQueueIndex() {
		return queueIndex;
	}
	
	public HBox getMusicUINode() {
		return musicUINode;
	}
}
