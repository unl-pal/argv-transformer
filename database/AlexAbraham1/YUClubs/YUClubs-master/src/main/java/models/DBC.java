package main.java.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import main.java.configs.SQLConfig;
import main.java.models.db_objects.Club;
import main.java.models.db_objects.Event;
import main.java.models.db_objects.President;
import main.java.models.db_objects.User;
import main.java.models.db_objects.join_tables.EventClub;
import main.java.models.db_objects.join_tables.UserClub;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Database Connection
 */
public class DBC {

    //SQLConfig is a class in main.java.configs package which was NOT committed to GitHub
    //Create your own class and add fields for MySQL url, user, and password
    private static final String databaseURL = SQLConfig.URL;
    private static final String databaseUser = SQLConfig.USER;
    private static final String databasePassword = SQLConfig.PASS;

    private static ConnectionSource connectionSource;
    private static Dao<User, String> userDao;
    private static Dao<Club, String> clubDao;
    private static Dao<Event, String> eventDao;
    private static Dao<President, String> presidentDao;

    private static Dao<UserClub, String> userClubDao;
    private static Dao<EventClub, String> eventClubDao;
}
