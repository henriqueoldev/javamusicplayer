package dev.henriqueol.JMP.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.audio.exceptions.CannotReadVideoException;

import dev.henriqueol.JMP.model.Defaults;
import dev.henriqueol.JMP.model.MediaItem;
import dev.henriqueol.JMP.repository.PlaylistRepo;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

public class PlaylistController {
	private PlaylistRepo playlistRepository = new PlaylistRepo();
	private PlayerController playerController;
	
	public PlaylistController(PlayerController playerController) {
		this.playerController = playerController;
	}
	
	public ArrayList<MediaItem> loadMediaFiles(){
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aiff", "*.aif", "*.mp4", "*.m4a")
			);
			List<File> mediaFiles = fileChooser.showOpenMultipleDialog(null);
			if (mediaFiles != null) {
				ArrayList<MediaItem> loadedMediaItems = new ArrayList<MediaItem>();
				for (File file : mediaFiles) {
					loadedMediaItems.add(createMediaItem(file));
				}
				return loadedMediaItems;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Map<String, String> getMediaMetadata(Media mediaStream) {
		Map<String, String> mediaMetadata = new HashMap<String, String>();
		
		mediaMetadata.put("mediaName", "");
		mediaMetadata.put("mediaArtist", "");
		mediaMetadata.put("mediaAlbum", "");
		try {
			File mediaFile = new File(URI.create(mediaStream.getSource()));
			
			if (mediaFile.exists()) {
				AudioFile audioMedia = AudioFileIO.read(mediaFile);
				if (!audioMedia.getTag().getFirst(FieldKey.TITLE).isEmpty()) {
					mediaMetadata.put(Defaults.MEDIA_NAME, audioMedia.getTag().getFirst(FieldKey.TITLE));
					if (!audioMedia.getTag().getFirst(FieldKey.ARTIST).isEmpty()) {
						mediaMetadata.put(Defaults.MEDIA_ARTIST, audioMedia.getTag().getFirst(FieldKey.ARTIST));
					}
					if (!audioMedia.getTag().getFirst(FieldKey.ALBUM).isEmpty()) {
						mediaMetadata.put(Defaults.MEDIA_ALBUM, audioMedia.getTag().getFirst(FieldKey.ALBUM));
					}
				} else {
					mediaMetadata.put(
							Defaults.MEDIA_NAME,
							Paths.get(mediaStream.getSource()).getFileName().toString()
					);
				}
			}
			return mediaMetadata;
		} catch (Exception e) {
			if (e instanceof CannotReadVideoException) {
				System.err.println(
						"Error Loading file metadata: "
						+ mediaStream.getSource() + "\n"
						+ e.getLocalizedMessage());
			} else {
				e.printStackTrace();
			}
			try {
				mediaMetadata.put(
						Defaults.MEDIA_NAME,
						URLDecoder.decode(Paths.get(mediaStream.getSource()).getFileName().toString(), "UTF-8")
				);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			return mediaMetadata;
		}
	}
	
	public void savePlaylist() {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters()
            .addAll(
            		new FileChooser.ExtensionFilter("M3U files (*.m3u)", "*.m3u", "*.M3U", "*.m3u8", "*.M3U8")
            );
			File playlistFile = fc.showSaveDialog(null);
			if (playlistFile != null) {				
				playlistRepository.saveM3UPlaylistFromMediaList(playerController.getActiveQueue(), playlistFile.toURI());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadPlaylist() {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters()
            .addAll(
            		new FileChooser.ExtensionFilter("M3U files (*.m3u)", "*.m3u", "*.M3U", "*.m3u8", "*.M3U8")
            );
			File playlistFile = fc.showOpenDialog(null);
			if (playlistFile != null) {
				List<File> filesList =  playlistRepository.loadM3UPlaylist(playlistFile);
				if (filesList != null && !filesList.isEmpty()) {
					for (File file : filesList) {
						if (validateFileExtension(file)) {
							playerController.enqueueMediaItem(createMediaItem(file));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean validateFileExtension(File file) {
		for (String ext : Defaults.SUPPORTED_EXTENSIONS) {
			try {
				if (URLDecoder.decode(file.toPath().getFileName().toString(), "UTF-8").endsWith(ext)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private MediaItem createMediaItem(File file) {
		Media mediaFileItem = new Media(file.toURI().toString());
		Map<String, String> mediaMetadata = getMediaMetadata(mediaFileItem);
		MediaItem newMediaItem = new MediaItem(
				mediaFileItem,
				mediaMetadata.get(Defaults.MEDIA_NAME),
				mediaMetadata.get(Defaults.MEDIA_ARTIST),
				mediaMetadata.get(Defaults.MEDIA_ALBUM)
		);
		return newMediaItem;
	}
}
