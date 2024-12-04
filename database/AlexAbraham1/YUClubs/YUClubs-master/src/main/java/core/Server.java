package main.java.core;

import main.java.models.*;

import main.java.models.db_objects.Club;
import main.java.models.db_objects.User;
import main.java.routes.get.*;
import main.java.services.EventService;
import main.java.services.UserService;
import org.apache.commons.io.FileUtils;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import static spark.Spark.*;

public class Server {

    private static final String[] protectedRoutes = {"/me", "/passwordReset", "/createClub", "/api/user", "/api/user/*"};
}
