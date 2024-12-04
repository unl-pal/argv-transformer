package main.java.routes.post;

import main.java.models.DBC;
import main.java.models.Email;
import main.java.models.PasswordHash;
import main.java.models.db_objects.Club;
import main.java.models.db_objects.President;
import main.java.models.db_objects.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.modelAndView;

/**
 * Created by alexabraham on 3/29/15.
 */
public class LinkUserClubPostRoute implements TemplateViewRoute {
}
