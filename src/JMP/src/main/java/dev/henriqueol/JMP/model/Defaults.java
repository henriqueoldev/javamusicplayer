package dev.henriqueol.JMP.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	//Just having some fun
	public static final List<String> characterQuotes = new ArrayList<String>(List.of(
            "I'm ready, let's go!",
            "It's showtime!",
            "What you talking 'bout, Willis?",
            "That’s all, folks!",
            "Wubba Lubba dub-dub",
            "Eh, what’s up, Doc?",
            "It’s time to be awesome!",
            "Why So Serious, son?",
            "Keep it cool, man.",
            "Feel the rhythm, feel the ride!",
            "It’s a fact, Jack!",
            "Let’s make some noise!",
            "Don’t look back...",
            "May the Force be with you.",
            "I am Groot.",
            "I'll be back.",
            "I'm gonna make him an offer he can't refuse.",
            "May the Force be with you.",
            "You talkin' to me?",
         	"I love the smell of napalm in the morning.",
         	"E.T. phone home.",
         	"Hasta la vista, baby.",
         	"Houston, we have a problem.",
         	"Hello, World!",
         	"Say 'hello' to my little friend!",
         	"Elementary, my dear Watson.",
         	"Here's Johnny!",
         	"Listen to them. Children of the night. What music they make.",
         	"My precious.",
         	"A martini. Shaken, not stirred.",
         	"D'oh!",
         	"Beep! Beep!",
         	"Ay, Caramba!",
         	"Oh My God! They Killed Kenny!",
         	"Giggity, Giggity, Giggity...",
         	"I Am the Vengeance...",
         	"By the Power of Greyskull",
         	"I am Cornholio!",
         	"I need TP for my bunghole!"
        ));
	
	public static String getRandomQuote() {
		Random rdm = new Random();
		return characterQuotes.get(rdm.nextInt(characterQuotes.size()));
	}
}
