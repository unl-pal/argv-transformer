package labs;

/*
 * ini okumak 
 * http://www.rgagnon.com/javadetails/java-0024.html
 * http://yourjavacode.blogspot.com.tr/2013/05/ini4j-how-to-read-and-write-from.html
 * sade https://www.daniweb.com/software-development/java/threads/123311/read-and-update-a-ini-file
 * 
 //http://stackoverflow.com/questions/26589976/how-to-format-date-and-time-from-jdatechooser-to-mysql-datetime-column
 // cok onemli http://naveenrajput10.blogspot.com.tr/2014/06/set-date-on-jdatechooser-and-retrieve.html
 //https://www.youtube.com/watch?v=EkAaFNV-GB0
 //http://stackoverflow.com/questions/11752989/netbeanshow-to-set-date-to-jdatechooser-which-is-retrieve-from-database
 http://stackoverflow.com/questions/3504986/java-date-time-format
 http://naveenrajput10.blogspot.com.tr/2014/06/set-date-on-jdatechooser-and-retrieve.html
 http://stackoverflow.com/questions/11719917/how-to-clear-jdatechooser-field
 eklenecek id yeri kaldı ve add dediğinde aslınd boşaltıp eklemeli o kaldı 
 */

/*
 //explanation büyük olacak  ekle
 //upload file ekle
 //tarih olarak seçilecek 
 //girişe enter ile olsun 
 //08/02/2015  ilk işe yarar java uygulamam lara ver 
 //db ayarı ini den oku 
 //dabata se ini ye bağlı 
 //tema seçimi ayarı iniye ver 

 tarih hatalı ---
 explanation buyuk ---
 open ve file calışmıyor ---
 attığı yerde aynı isimde dosya var mı yok mu kontrol etsin ***
 load data yı refresh yapallım ---
 kayıt yapıldı uyarısı ingilizce olsun ---
 dosya yuklendi dediği sadece uyarı label ına gelsin ve orada wait olabilir 
 filtreleme de date secince tarih gelsin
 //https://blogs.oracle.com/CoreJavaTechTips/entry/making_progress_with_swing_s
 //ekle http://stackoverflow.com/questions/447481/how-do-i-use-jprogressbar-to-display-file-copy-progress



 +++gradelere sort koncak 
 çıkışa evet hayır sorusu konulacak
 File name de dosya kopyalama kaydet denilince yapılacak.
 alt veritabanları yapsana banaaaaa :)
 ID kalkacak kategori gelecek yeeyyyy :D


 */

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;

import javax.swing.JProgressBar;

import java.awt.Font;

import javax.swing.JInternalFrame;

import java.beans.PropertyVetoException;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ImageIcon;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class HomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	Connection connection = null;
	private JButton btnLoad;
	private JTextField textDatename;
	private JTextArea textExplanation;
	private JTextField textfilename;
	private JTable table;
	private JComboBox<Object> comboBox_categoryFilter;
	private JComboBox<Object> comboBox_1;

	private JComboBox<Object> comboBox_filter;
	private JLabel lblDurum;
	private JLabel lblIdst;
	private JDateChooser textDate;

	private JPanel contentPane;
	private JTextField textSearch;
	private JPanel panel_data;
	private JPanel panel_action;
	java.util.Date dt1;
	private JInternalFrame internalFrame;
	private JInternalFrame internalFrame_1;
	private JProgressBar progress;

	private JTable table_category;
	private JTextField textField_categoryName;

	private int fontSize_;
	private String filename;
	private String path;
	private JLabel lblCatid;
}
