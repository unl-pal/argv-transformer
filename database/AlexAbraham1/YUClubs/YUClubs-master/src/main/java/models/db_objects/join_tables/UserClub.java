package main.java.models.db_objects.join_tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import main.java.models.db_objects.Club;
import main.java.models.db_objects.User;

import java.sql.Timestamp;

@DatabaseTable(tableName = "users_clubs") //Define SQL Table
public class UserClub
{
    public final static String USER_ID_FIELD_NAME = "user_id";
    public final static String CLUB_ID_FIELD_NAME = "club_id";

    @DatabaseField(generatedId = true) //auto-increment
    private int id;

    @DatabaseField(dataType = DataType.TIME_STAMP)
    private Timestamp modified_at;

    @DatabaseField(foreign = true, columnName = USER_ID_FIELD_NAME)
    User user;

    @DatabaseField(foreign = true, columnName = CLUB_ID_FIELD_NAME)
    Club club;

}