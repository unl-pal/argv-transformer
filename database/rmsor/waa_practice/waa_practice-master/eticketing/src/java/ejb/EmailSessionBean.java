package ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Protocol;
import model.UserInfo;

/**
 *
 * @author xtrememe
 */
@Stateless
public class EmailSessionBean {
    @EJB
    private UserFacade userFacade;
    private UserInfo user;
    /**
     * port 587 for TLS/STARTTLS
     * port 465 for SSL
     */
    private int port = 465;
    private String host = "smtp.gmail.com";
    private String from = "mmaharjan@mum.edu";
    private boolean auth = true;
    private String username = "mmaharjan@mum.edu";
    private String password = "CatsAndD0gs";
    private Protocol protocol = Protocol.SMTPS;
    private boolean debug = true;
}
