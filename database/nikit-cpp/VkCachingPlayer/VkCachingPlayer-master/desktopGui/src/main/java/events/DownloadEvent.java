package events;

import org.apache.log4j.Logger;

import com.github.nikit.cpp.player.Song;

public class DownloadEvent {
	private static Logger LOGGER = Logger.getLogger(DownloadEvent.class);
	
	private Song song;
}
