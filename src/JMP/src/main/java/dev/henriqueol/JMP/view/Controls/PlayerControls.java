package dev.henriqueol.JMP.view.Controls;

import dev.henriqueol.JMP.controller.IconController;
import dev.henriqueol.JMP.controller.PlayerController;
import dev.henriqueol.JMP.model.UIDefaults;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PlayerControls implements UIDefaults{
	private IconController icons = new IconController();
	private PlayerController playerController;
	
	private AnchorPane pc = new AnchorPane();
	HBox controlsBox = new HBox(UI_ICON_GAP);
	
	private Label timeLabel = new Label("00:00/00:00");
	private Label musicNameLabel = new Label("Music Name - Artist - Album");
	
	private Button prevBtn = new Button();
	private Button playPauseBtn = new Button();
	private Button nextBtn = new Button();
	
	private Slider musicSlider = new Slider(0, 0, -1);
	private ProgressBar musicProgress = new ProgressBar();
	
	public PlayerControls(PlayerController playerController) {
		this.playerController = playerController;
		
		VBox vbControlsTime = new VBox(UI_ITEM_GAP);
		VBox vbNameSlider = new VBox(UI_ITEM_GAP);
		HBox playerBox = new HBox(UI_ITEM_GAP);
		StackPane progressPane = new StackPane();
		
		setButtons();
		controlsBox.setAlignment(Pos.CENTER);
		controlsBox.getChildren().addAll(prevBtn, playPauseBtn, nextBtn);
		controlsBox.getStyleClass().add("controlsBox");
		vbControlsTime.setAlignment(Pos.CENTER);
		
		//Progress
		setSlider();
		vbNameSlider.setAlignment(Pos.CENTER);
		setMusicProgress();
		progressPane.getChildren().add(musicProgress);
		progressPane.getChildren().add(musicSlider);
		
		vbControlsTime.getChildren().addAll(timeLabel, controlsBox);
		vbNameSlider.getChildren().addAll(musicNameLabel, progressPane);
		
		playerBox.getChildren().addAll(vbControlsTime, vbNameSlider);
		HBox.setHgrow(vbNameSlider, Priority.ALWAYS);
		
		pc.getChildren().add(playerBox);
		AnchorPane.setLeftAnchor(playerBox, 0d);
		AnchorPane.setRightAnchor(playerBox, 0d);
		
		//Handles
		playPauseBtn.setOnMouseClicked((e) -> {
			playPauseToggle();
		});
		prevBtn.setOnMouseClicked((e) -> {
			playerController.playPrevious();
		});
		nextBtn.setOnMouseClicked((e) -> {
			playerController.playNext();
		});
		musicSlider.valueProperty().addListener((e) -> {
			playerController.seekNewTime(Duration.seconds(musicSlider.getValue()));
		});
	}
	
	private void setButtons() {
		//Setting the Buttons Icons
		prevBtn.setGraphic(new ImageView(icons.getIconImage("skip-back-circle", UI_ICON_SMALL.getWidth(), UI_ICON_SMALL.getHeight())));
		playPauseBtn.setGraphic(new ImageView(icons.getIconImage("play-circle", UI_ICON_SMALL.getWidth(), UI_ICON_SMALL.getHeight())));
		nextBtn.setGraphic(new ImageView(icons.getIconImage("skip-forward-circle", UI_ICON_SMALL.getWidth(), UI_ICON_SMALL.getHeight())));
		//Button sizes
		prevBtn.setPrefWidth(UI_ICON_SMALL.getWidth() + UI_ICON_PADDING);
		playPauseBtn.setPrefWidth(UI_ICON_SMALL.getWidth() + UI_ICON_PADDING);
		nextBtn.setPrefWidth(UI_ICON_SMALL.getWidth() + UI_ICON_PADDING);
	}
	
	private void playPauseToggle() {
		if (playerController.isPlaying()) {
			this.playerController.pause();
		} else {
			this.playerController.play(null);
		}
	}
	
	public void setPlayPauseIcon(boolean setPauseIcon) {
		ImageView playIcon = new ImageView(icons.getIconImage("play-circle", UI_ICON_SMALL.getWidth(), UI_ICON_SMALL.getHeight()));
		ImageView pauseIcon = new ImageView(icons.getIconImage("pause-circle", UI_ICON_SMALL.getWidth(), UI_ICON_SMALL.getHeight()));
		playPauseBtn.setGraphic(setPauseIcon == true ? pauseIcon : playIcon);
	}
	
	public void updateMediaName(String mediaName) {
        if (musicNameLabel.getText() != mediaName) {
        	musicNameLabel.setText(mediaName);
        	musicNameLabel.setTooltip(new Tooltip(mediaName));
		}
	}
	
	public void updateSlider(Duration mediaTime) {
		musicSlider.setMax(mediaTime.toSeconds());
		musicSlider.setValue(0);
	}
	
	public void updateMediaInfo( Duration mediaTime, Duration mediaDuration) {
		int totalSeconds = (int) mediaDuration.toSeconds() % 60;
		int totalMinutes = (int) mediaDuration.toMinutes() % 60;
		int totalHours = (int) mediaDuration.toHours();
		int currentSeconds = (int) mediaTime.toSeconds() % 60;
		int currentMinutes = (int) mediaTime.toMinutes() % 60;
		int currentHours = (int) mediaTime.toHours();
		
        String totalTime;
        String currentTime;
        
        if (totalHours > 0) {
        	totalTime = String.format("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds);
        	currentTime = String.format("%02d:%02d:%02d", currentHours, currentMinutes, currentSeconds);
		} else {
			totalTime = String.format("%02d:%02d", totalMinutes, totalSeconds);
			currentTime = String.format("%02d:%02d", currentMinutes, currentSeconds);
		}
		timeLabel.setText(String.format("%s/%s", currentTime, totalTime));
		musicProgress.setProgress(Math.max(mediaTime.toSeconds() / mediaDuration.toSeconds(), .01));
	}
	
	private void setSlider() {
		musicSlider.getStyleClass().add("musicSlider");
		musicSlider.setMaxHeight(Double.MAX_VALUE);
		HBox.setHgrow(musicSlider, Priority.ALWAYS);
	}
	
	private void setMusicProgress() {
		musicProgress.getStyleClass().add("musicProgressBar");
		musicProgress.setMaxWidth(Double.MAX_VALUE);
	}
	
	public AnchorPane getControls() {
		return pc;
	}
}
