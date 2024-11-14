package com.freelancersteam.www.java.tomafoto.estudiandojmf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.*;
import javax.media.cdm.CaptureDeviceManager;
import java.io.*;
import java.awt.*;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 * @web http://jc-mouse.blogspot.com/
 * @author Mouse
 */
public class jmfVideo {
//Controlador universal de windows
    private String dispositivo = "vfw:Microsoft WDM Image Capture (Win32):0";
    private Player player = null;

    //Metodo para capturar la imagen de la webcam
    Image img = null;


}
