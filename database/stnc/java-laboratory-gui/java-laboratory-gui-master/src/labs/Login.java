package labs;
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
//ekle http://stackoverflow.com/questions/447481/how-do-i-use-jprogressbar-to-display-file-copy-progress*/


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
//import javax.swing.UIManager;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JPasswordField;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.SwingConstants;

import java.awt.Window.Type;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import labs.screen_utils;


public class Login {

	private JFrame frmLoginscreen;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JButton btnlogin;
	Connection connection = null;
}
