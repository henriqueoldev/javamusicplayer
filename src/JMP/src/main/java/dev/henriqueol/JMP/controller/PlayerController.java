package dev.henriqueol.JMP.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import dev.henriqueol.JMP.model.Defaults;
import dev.henriqueol.JMP.model.MediaItem;
import dev.henriqueol.JMP.model.UIDefaults;
import dev.henriqueol.JMP.view.Controls.PlayerControls;
import dev.henriqueol.JMP.view.Controls.PlaylistControls;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerController {
	private ArrayList<MediaItem> activeQueue = new ArrayList<MediaItem>();
	private int activeMediaIndex;
	private MediaPlayer mainMediaPlayer;
	private PlaylistControls playlistView;
	private PlayerControls playerView;
	private Timeline playerInfoTimeline;
	private boolean playerInfoTimelineChanging = false;
	
	private boolean shuffleQueue = false;
	private int repeatOption = Defaults.REPEAT_ALL;
	
	public PlayerController() {
		try {
			playerInfoTimeline = new Timeline(new KeyFrame(Duration.millis( 1000 ), (e) -> {
				if (mainMediaPlayer != null && mainMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
					playerInfoTimelineChanging = true;
					updateMediaInfo();
					playerInfoTimelineChanging = false;
				}
			}));
			playerInfoTimeline.setCycleCount( Animation.INDEFINITE );
			playerInfoTimeline.play();
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
			if (!activeQueue.isEmpty()) {
				mainMediaPlayer = new MediaPlayer(activeQueue.get(index).getmediaStream());
				mainMediaPlayer.setOnEndOfMedia(() -> {
					if (activeMediaIndex == activeQueue.size() - 1) {
						if (repeatOption == Defaults.REPEAT_NONE) {
							mainMediaPlayer.stop();
						} else if (repeatOption == Defaults.REPEAT_ALL) {
							playMediaItem(0);
						}
					} else if (repeatOption == Defaults.REPEAT_ONE) {
						playMediaItem(activeMediaIndex);
					} else {
						playMediaItem((index + 1) % (activeQueue.size()));
					}
			    });
				play(activeQueue.get(index));
				activeMediaIndex = index;
				playlistView.setPlaylistSelectedIndex(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMediaItem(int index){
		try {
			if (mainMediaPlayer != null){
				if (mainMediaPlayer.getMedia() == activeQueue.get(index).getmediaStream()) {
					mainMediaPlayer.stop();
					mainMediaPlayer = null;
					pause();
					updateMediaInfo();
				}
			}
			activeQueue.remove(index);
			playlistView.updatePlaylist(activeQueue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMediaItems(ObservableList<Integer> indices){
		try {
			List<Integer> arrayListIndices = new ArrayList<Integer>(indices);
			Collections.sort(arrayListIndices, Comparator.reverseOrder());
			for (Integer index : arrayListIndices) {
				if (mainMediaPlayer != null){
					if (mainMediaPlayer.getMedia() == activeQueue.get(index).getmediaStream()) {
						mainMediaPlayer.stop();
						mainMediaPlayer = null;
						pause();
						updateMediaInfo();
					}
				}
				activeQueue.remove(index.intValue());
			}
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
					Stage stage = (Stage) playerView.getScene().getWindow();
					stage.setTitle(Defaults.APP_NAME + " " + UIDefaults.TRACK_SEPARATOR + " " + mediaName);
				}
				mainMediaPlayer.setOnReady(() -> {
					playerView.updateSlider(mainMediaPlayer.getMedia().getDuration());
					updateMediaInfo();
				});
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
			int nextMediaIndex = 0;
			if (shuffleQueue) {
				Random rdm = new Random();
				nextMediaIndex = rdm.nextInt(activeQueue.size());
				while (nextMediaIndex == activeMediaIndex) {
					nextMediaIndex = rdm.nextInt(activeQueue.size());
				}
			} else {
				nextMediaIndex = (activeMediaIndex + 1) % activeQueue.size();
			}
			playMediaItem(nextMediaIndex);
		}
	}
	
	public void playPrevious() {
		if (!activeQueue.isEmpty()) {
			int previousMediaIndex = activeMediaIndex - 1;
			if (shuffleQueue) {
				Random rdm = new Random();
				previousMediaIndex = rdm.nextInt(activeQueue.size());
				while (previousMediaIndex == activeMediaIndex) {
					previousMediaIndex = rdm.nextInt(activeQueue.size());
				}
			} else {
				previousMediaIndex = previousMediaIndex < 0 ? activeQueue.size() - 1 : previousMediaIndex;
			}
			playMediaItem(previousMediaIndex);
		}
	}
	
	public boolean isPlaying() {
		if (mainMediaPlayer != null) {
			return mainMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING ? true : false;
		}
		return false;
	}
	
	//Terrible job, must fix later
	public void moveMediaInQueue(int index, boolean moveDown) {
		if (!activeQueue.isEmpty()) {
			MediaItem item = activeQueue.get(index);
			if (index == activeQueue.size() - 1 && activeQueue.size() > 1) {
				if (moveDown) {
					activeQueue.remove(index);
					activeQueue.addFirst(item);
				} else {
					Collections.swap(activeQueue, index, index - 1);
					playlistView.updatePlaylist(activeQueue);
				}
			} else if (index == 0 && activeQueue.size() > 1) {
				if (moveDown) {
					Collections.swap(activeQueue, index, index + 1);
					playlistView.updatePlaylist(activeQueue);
				} else {
					activeQueue.remove(index);
					activeQueue.add(item);
				}
			} else {
				Collections.swap(activeQueue, index, moveDown ? index + 1 : index - 1);
				playlistView.updatePlaylist(activeQueue);
			}
			playlistView.updatePlaylist(activeQueue);
			activeMediaIndex = activeQueue.indexOf(item);
		}
	}
	
	public void updateMediaInfo() {
		if (mainMediaPlayer != null) {
			playerView.updateMediaInfo(mainMediaPlayer.getCurrentTime(), mainMediaPlayer.getTotalDuration());
		} else {
			playerView.resetMediaInfo();
		}
	}
	
	public void updateMediaInfo(Duration newTime) {
        playerView.updateMediaInfo(newTime, mainMediaPlayer.getTotalDuration());
	}
	
	public void seekNewTime(Duration newTime){
		if (mainMediaPlayer != null && mainMediaPlayer.getCurrentTime() != newTime) {
			mainMediaPlayer.seek(newTime);
			updateMediaInfo(newTime);
		}
	}
	
	public void pausePlayerInfoTimeline() {
		playerInfoTimeline.stop();
		playerInfoTimeline.jumpTo(Duration.ZERO);
	}
	
	public void playPlayerInfoTimeline() {
		playerInfoTimeline.play();
	}
	
	public boolean isPlayerInfoTimelineUpdating() {
		return playerInfoTimelineChanging;
	}
	
	public boolean isShuffleQueue() {
		return shuffleQueue;
	}

	public void setShuffleQueue(boolean shuffleQueue) {
		this.shuffleQueue = shuffleQueue;
	}

	public int getRepeatOption() {
		return repeatOption;
	}

	public void setRepeatOption(int repeatOption) {
		this.repeatOption = repeatOption;
	}
}
