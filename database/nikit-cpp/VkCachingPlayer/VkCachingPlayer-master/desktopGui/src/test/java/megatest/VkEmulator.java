package megatest;

// http://stackoverflow.com/questions/14179746/unit-test-a-servlet-with-an-embedded-jetty/14181904#14181904
// http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/examples/embedded/src/main/java/org/eclipse/jetty/embedded/MinimalServlets.java

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Test;

public class VkEmulator {
	
	static Logger LOGGER = Logger.getLogger(VkEmulator.class);
	private static int port = 8079;
	private Server server;
	
	@SuppressWarnings("serial")
	public static class HelloServlet extends HttpServlet {
		
		private String type = "text/xml";
		
		List<Mapping> mapList = new ArrayList<Mapping>();
	}
	
	public static class Mapping{
		URL url;
		File file;
	}
}