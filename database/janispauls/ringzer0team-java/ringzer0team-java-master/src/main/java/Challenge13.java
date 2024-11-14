import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Challenge13 {

    private final String username;
    private final String password;
    private final String challengeUrl = "http://ringzer0team.com/challenges/13";
    private final String loginUrl = "http://ringzer0team.com/login";
    private String solution;
    private String flag;
    private CloseableHttpClient client;
}
