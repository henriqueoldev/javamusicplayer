package dev.henriqueol.JMP.model;

import javafx.scene.media.Media;

public class MediaItem {
	private Media mediaStream;
	private String mediaName;
	private String mediaArtist;
	private String mediaAlbum;
	public MediaItem(Media mediaStream, String mediaName, String mediaArtist, String mediaAlbum) {
		super();
		this.mediaStream = mediaStream;
		this.mediaName = mediaName;
		this.mediaArtist = mediaArtist;
		this.mediaAlbum = mediaAlbum;
	}
	public Media getmediaStream() {
		return mediaStream;
	}
	public void setmediaStream(Media mediaStream) {
		this.mediaStream = mediaStream;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaArtist() {
		return mediaArtist;
	}
	public void setMediaArtist(String mediaArtist) {
		this.mediaArtist = mediaArtist;
	}
	public String getMediaAlbum() {
		return mediaAlbum;
	}
	public void setMediaAlbum(String mediaAlbum) {
		this.mediaAlbum = mediaAlbum;
	}
}
