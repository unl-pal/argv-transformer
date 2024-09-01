package webserver;


/* An example of a very simple, multi-threaded HTTP server.
 Modified by Ralph Bunker from http://java.sun.com/developer/technicalArticles/Networking/Webserver/
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
class WebServer {

    /* static class data/methods */
    static File root; // the web server's virtual root
    static Scanner sc;

    // An instance variable of Container class
    private Hashtable mylets = new Hashtable();
}
