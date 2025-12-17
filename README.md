<div align="center">
    <img src="https://raw.githubusercontent.com/henriqueoldev/javamusicplayer/master/assets/icon_full.png"
        height="128">
        <h1>Java Music Player</h1>
        <h4>A Simple JavaFX application to play audio media and manage m3u playlists, highly inspired by <a href="https://winamp.com/player/legacy">Winamp</a></h4>
</div>

![](https://img.shields.io/github/stars/henriqueoldev/JavaMusicPlayer) ![](https://img.shields.io/github/forks/henriqueoldev/JavaMusicPlayer) ![](https://img.shields.io/github/release/henriqueoldev/JavaMusicPlayer) ![](https://img.shields.io/github/issues/henriqueoldev/JavaMusicPlayer) ![](https://img.shields.io/github/downloads/henriqueoldev/javamusicplayer/total)
### Screenshots:
<div align="center">
    <img src="https://raw.githubusercontent.com/henriqueoldev/javamusicplayer/master/assets/player_empty.png"
        height="256">
	<img src="https://raw.githubusercontent.com/henriqueoldev/javamusicplayer/master/assets/player_playlist.png"
        height="256">
</div>

## Download
To run the player you must have at least [JRE 11](https://adoptium.net/temurin/releases/?version=11) installed, then simply **[download](https://github.com/henriqueoldev/javamusicplayer/releases/latest)** the jar and run it

## Goals
- [x] Play Audio Media
- [x] Load and Save M3U playlists
- [ ] M3U Playlists Editing
- [ ] Metadata Viewer
- [ ] Metadata Editor
- [ ] Player Themes/Skins

## Project setup
The project was made using [Eclipse](https://eclipseide.org/) and requires:

- Maven
- JDK 11+
- JavaFX 21 (Maven Dependency)
- JAudioTagger (Maven Dependency)

To get started, clone the repository and move the **JMP** folder to your workspace:
```bash
git clone https://github.com/henriqueoldev/
```
```bash
mv ./JMP ~/eclipse-workspace
```
After that run Maven Clean Install to download dependencies
```bash
mvn clean install
```
## Contributing

This is a simple open source project. Feel free to fork it or send issues and pull requests!

### License: [WTFPL+](https://github.com/henriqueoldev/javamusicplayer/license)
