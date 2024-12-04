package main.java.models.db_objects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName = "presidents") //Define SQL Table
public class President
{
    @DatabaseField(generatedId = true) //auto-increment
    private int id;

    @DatabaseField(dataType = DataType.TIME_STAMP)
    private Timestamp modified_at;

    @DatabaseField
    private String name;

    @DatabaseField
    private String email;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String image;

    @DatabaseField(foreign = true)
    private User user;

}