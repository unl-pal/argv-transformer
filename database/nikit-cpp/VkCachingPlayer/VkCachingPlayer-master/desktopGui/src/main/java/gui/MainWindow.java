package gui;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import events.NextSong;
import events.PlayPauseEvent;
import events.PlayIntent;
import events.StopIntent;
import events.PlayStopped;
import events.PlayStarted;
import events.ProgressEvent;
import events.PrevSong;

import com.github.nikit.cpp.player.PlayList;
import com.github.nikit.cpp.player.Song;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import config.Config;
import events.DownloadEvent;
import service.DownloadService;
import service.DownloadServiceException;
import service.PlayerService;
import vk.VkPlayListBuilder;
import vk.VkPlayListBuilderException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;

public class MainWindow extends JFrame {
	
	private static final String SPRING_CONFIG = "spring-config.xml";
	private static final String STOPPED = "Stopped";
	private static Config config;
	private static VkPlayListBuilder playlistBuilder;
	private static EventBus eventBus;
	private static PlayerService playerService;
	private static DownloadService downloadService;
	private static Logger LOGGER = Logger.getLogger(MainWindow.class);
	private static final long serialVersionUID = 1L;
	private JList<Song> songsList;
	private JPanel contentsPanel;
	private JLabel statusLabel;
	static MainWindow instance = null;
	private JButton btnPrev;
	private JButton btnPlay;
	private JPanel controlPanel;
	private JButton btnNext;
	private JButton btnStop;
	private SelectedListCellRenderer listRenderer;
	private JPanel sliderPanel;
	private JPanel buttonsPanel;
	private JSlider slider;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmNewMenuItem;
	private JSplitPane splitPane;
	private JScrollPane scrollRightPane;
	private JScrollPane scrollLeftPane;
	private JLabel imageLabel;
	private Hilighter hilighter;
	
	private static final String PLAY = "Play";
	private static final String PAUSE = "Pause";
}


class PlayListListModel extends AbstractListModel<Song> {
	private static final long serialVersionUID = 1L;
	// здесь будем хранить данные
	private List<Song> data = new ArrayList<Song>();
}

class SelectedListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	private List<HilightItem> items;
}

class HilightItem{
	private int hilighted = -1;
	private Color color = null;

}

class Hilighter{
	private List<HilightItem> items = new ArrayList<HilightItem>();
	private Set<Integer> playing = new HashSet<Integer>();
}