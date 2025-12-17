package dev.henriqueol.JMP.model;

public interface Defaults {
	public static final String APP_NAME = "Java Music Player";
	public static final double INITIAL_WINDOW_WIDTH = 320d;
	public static final double INITIAL_WINDOW_HEIGHT = 448d;
	public static final String MEDIA_NAME = "mediaName";
	public static final String MEDIA_ARTIST = "mediaArtist";
	public static final String MEDIA_ALBUM = "mediaAlbum";
	public static final String[] SUPPORTED_EXTENSIONS = {
			"wav", "mp3", "aiff", "aif", "mp4", "m4a"
	    };
	public static final double PLAYER_SEEK_SECONDS = 5;
}
