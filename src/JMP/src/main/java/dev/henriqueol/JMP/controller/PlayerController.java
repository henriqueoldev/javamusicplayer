package dev.henriqueol.JMP.controller;

import java.util.ArrayList;

import dev.henriqueol.JMP.model.Defaults;
import dev.henriqueol.JMP.model.MediaItem;
import dev.henriqueol.JMP.model.UIDefaults;
import dev.henriqueol.JMP.view.Controls.PlayerControls;
import dev.henriqueol.JMP.view.Controls.PlaylistControls;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerController {
	private ArrayList<MediaItem> activeQueue = new ArrayList<MediaItem>();
	private int activeMediaIndex;
	private MediaPlayer mainMediaPlayer;
	private PlaylistControls playlistView;
	private PlayerControls playerView;
	
	public PlayerController() {
		try {
			final Timeline timeline = new Timeline(new KeyFrame(Duration.millis( 500 ), (e) -> {
				if (mainMediaPlayer != null && mainMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
					updateMediaInfo();
				}
			}));
			timeline.setCycleCount( Animation.INDEFINITE );
			timeline.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPlaylistView(PlaylistControls playlistView){
		this.playlistView = playlistView;
	}
	
	public void setPlayerView(PlayerControls playerView){
		this.playerView = playerView;
	}
	
	public void setActiveQueue(ArrayList<MediaItem> activeQueue){
		this.activeQueue = activeQueue;
	}
	
	public ArrayList<MediaItem> getActiveQueue() {
		return activeQueue;
	}
	
	public void playMediaItem(int index){
		try {
			if (mainMediaPlayer != null) {
				mainMediaPlayer.stop();
				mainMediaPlayer.dispose();
			}
			mainMediaPlayer = new MediaPlayer(activeQueue.get(index).getmediaStream());
			mainMediaPlayer.setOnEndOfMedia(() -> {
				playMediaItem((index + 1) % (activeQueue.size()));
		    });
			play(activeQueue.get(index));
			activeMediaIndex = index;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMediaItem(int index){
		try {
			if (mainMediaPlayer != null){
				if (mainMediaPlayer.getMedia() == activeQueue.get(index).getmediaStream()) {
					mainMediaPlayer.stop();
					pause();
				}
			}
			activeQueue.remove(index);
			playlistView.updatePlaylist(activeQueue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enqueueMediaItem(MediaItem item) {
		activeQueue.add(item);
		playlistView.updatePlaylist(activeQueue);
	}
	
	public void play(MediaItem mediaItem) {
		try {
			if (mainMediaPlayer != null) {
				mainMediaPlayer.play();
				playerView.setPlayPauseIcon(true);
				if (mediaItem != null) {
					String mediaName = String.format(
							"%s %s %s",
							mediaItem.getMediaName(),
							mediaItem.getMediaArtist(),
							mediaItem.getMediaAlbum()
					);
					playerView.updateMediaName(mediaName);
					Stage stage = (Stage) playerView.getControls().getScene().getWindow();
					stage.setTitle(Defaults.APP_NAME + " " + UIDefaults.TRACK_SEPARATOR + " " + mediaName);
				}
				updateMediaInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		try {
			if (mainMediaPlayer != null) {
				mainMediaPlayer.pause();
				playerView.setPlayPauseIcon(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playNext() {
		if (!activeQueue.isEmpty()) {
			int nextMediaIndex = (activeMediaIndex + 1) % activeQueue.size();
			playMediaItem(nextMediaIndex);
		}
	}
	
	public void playPrevious() {
		if (!activeQueue.isEmpty()) {
			int previousMediaIndex = (activeMediaIndex - 1) % activeQueue.size();
			playMediaItem(previousMediaIndex);
		}
	}
	
	public boolean isPlaying() {
		if (mainMediaPlayer != null) {
			return mainMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING ? true : false;
		}
		return false;
	}
	
	public void updateMediaInfo() {
        playerView.updateMediaInfo(mainMediaPlayer.getCurrentTime(), mainMediaPlayer.getTotalDuration());
	}
	
	public void seekNewTime(Duration newTime){
		if (mainMediaPlayer.getCurrentTime() != newTime) {
			mainMediaPlayer.seek(newTime);
			updateMediaInfo();
		}
	}
	
}
