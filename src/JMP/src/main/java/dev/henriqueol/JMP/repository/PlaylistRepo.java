package dev.henriqueol.JMP.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import dev.henriqueol.JMP.model.MediaItem;

public class PlaylistRepo {
	public void saveM3UPlaylistFromMediaList(List<MediaItem> mediaPlaylist, URI filePath) {
		try {
			BufferedWriter bw;
			String filename = Paths.get(filePath).getFileName().toString();
			filename = URLDecoder.decode(filename, "UTF-8");
			if (filename.toLowerCase().endsWith(".m3u") || filename.toLowerCase().endsWith(".m3u8")) {
				bw = new BufferedWriter(new FileWriter(filePath.getPath()));
				filename = filename.substring(0, filename.lastIndexOf("."));
			} else {
				bw = new BufferedWriter(new FileWriter(filePath.getPath()+".m3u"));
			}
			
			bw.write("#EXTM3U");
			bw.newLine();
			bw.write("#PLAYLIST:"+filename+"");
			bw.newLine();
			for (MediaItem mediaItem : mediaPlaylist) {
				bw.newLine();
				bw.write(
						String.format(
								"#EXTINF:,%s %s %s",
								mediaItem.getMediaName(),
								mediaItem.getMediaArtist(),
								mediaItem.getMediaAlbum()
				));
				bw.newLine();
				bw.write(URLDecoder.decode(URI.create(mediaItem.getmediaStream().getSource().toString()).getPath(), "UTF-8"));
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(
					String.format(
							"Error: Failed to save Playlist\n%s",
							e.getStackTrace().toString())
			);
		}
	}
	public List<File> loadM3UPlaylist(File playlistFile) {
		try {
			BufferedReader br =  new BufferedReader(new FileReader(playlistFile.getPath()));
			ArrayList<File> mediaFilepaths = new ArrayList<File>();
			String line;
			
			while((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					File mediaFile;
					try {
						mediaFile = new File(line);
					} catch (Exception e) {
						mediaFile = null;
						System.err.println("Failed to load file at: " + line);
						e.printStackTrace();
					}
					
					if (mediaFile != null) {
						mediaFilepaths.add(mediaFile);
					}
				}
			}
			br.close();
			return mediaFilepaths;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
