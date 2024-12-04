package main.java.models;

import main.java.configs.EmailConfig;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Email {

    //EmailConfig is a class in main.java.configs package which was NOT committed to GitHub
    //Create your own class and add fields for gmail username and password
    private static final String gmailUser = EmailConfig.GMAIL_USER;
    private static final String gmailPassword = EmailConfig.GMAIL_PASS;
}
