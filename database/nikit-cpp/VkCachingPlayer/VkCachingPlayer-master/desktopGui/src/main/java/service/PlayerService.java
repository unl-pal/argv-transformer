package service;

import java.io.File;

import org.apache.log4j.Logger;

import events.PlayStopped;
import player.Player;
import player.State;

import com.github.nikit.cpp.player.PlayList;
import com.github.nikit.cpp.player.Song;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import events.DownloadEvent;
import events.DownloadFinished;
import events.NextSong;
import events.PlayPauseEvent;
import events.PlayIntent;
import events.ProgressEvent;
import events.PrevSong;
import events.StopIntent;

public class PlayerService {

	private static Logger LOGGER = Logger.getLogger(PlayerService.class);

	private EventBus eventBus;
	private Player player;
	private PlayList playList;
	private Song currentSong;
	private int songMaxSize;
	private boolean songmaxSizeSetted = false;
	private boolean mayNextOnFinished = true;
	private volatile boolean isPaused = false;
}
