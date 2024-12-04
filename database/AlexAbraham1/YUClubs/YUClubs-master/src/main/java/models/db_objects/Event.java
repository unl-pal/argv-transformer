package main.java.models.db_objects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName = "events") //Define SQL Table
public class Event implements Comparable<Event>
{
    @DatabaseField(generatedId = true) //auto-increment
    private int id;

    @DatabaseField(dataType = DataType.TIME_STAMP)
    private Timestamp modified_at;

    @DatabaseField
    private String name;

    @DatabaseField
    private String description;

    @DatabaseField
    private Timestamp time;

    @DatabaseField
    private String location;

    @DatabaseField
    private String flyer;
}