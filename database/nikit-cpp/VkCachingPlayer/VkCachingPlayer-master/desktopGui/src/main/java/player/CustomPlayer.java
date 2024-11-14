package player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import com.github.nikit.cpp.player.Song;
import com.google.common.eventbus.EventBus;

import events.PlayStopped;
import events.PlayStarted;
import events.ProgressEvent;
import javazoom.jl.player.Player;

/**
 * Must be singletone
 * @author nik
 *
 */
public class CustomPlayer implements player.Player{
	private static final Logger LOGGER = Logger.getLogger(CustomPlayer.class);
	private volatile Player player;
	private volatile FileInputStream FIS;
	private volatile BufferedInputStream BIS;
	private volatile boolean canResume;
	private volatile int total;
	private volatile int stopped;
	private volatile boolean valid;
	private volatile EventBus eventBus;
	private volatile State state;
	private volatile Song playedSong;
	public static final int statusThreadSleep = 800;
	public static final int UNEXISTED_POSITION = -1;

}
