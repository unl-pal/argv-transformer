package com.imiFirewall;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.imiFirewall.common.Commons;
import com.imiFirewall.util.imiUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class imiSql{
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private Context mCtx;    //�뵱ǰ���������
    
	
	public static final String KEY_ID     = "_id";  //ʹ��SimpleCursorAdapter Cursor����ʱע������,ǰ����_
	public static final String KEY_PHONE  = "phone";
	public static final String KEY_NAME   = "name";
	public static final String KEY_TYPE   = "type";
	public static final String KEY_DATE   = "date";
	
	private static final String DATABSE_CREATE_PASSWORD="create table PasswordBox (_id integer primary key autoincrement,name text,"
		+"password text);";
	private static final String DATABASE_CREATE_OPTIONS="create table Options (_id integer primary key autoincrement,optionsName text,"
		+"optionsInt integer, optionsString text)";
	private static final String DATABASE_CREATE_USERLIST="create table UserList (_id integer primary key autoincrement,name text,"
		+"phone text,type integer,message integer,call integer)";	
	private static final String DATABASE_CREATE_USERLOG="create table UserLog (_id integer primary key autoincrement,name text,"
		+"phone text,type integer,date long,content text,logThread integer,logState integer)";
	
	
    public static final  String DATABASE_NAME             = "imiFirewall";
    
	public static final  String DATABASE_TABLE_PASSWORD   ="PasswordBox";
	public static final  String DATABASE_TABLE_USERLIST   ="UserList";
	public static final  String DATABASE_TABLE_OPTIONS    ="Options";
	public static final  String DATABASE_TABLE_USERLOG    ="UserLog";
	
    
    private static final int DATABASE_VERSION = 1;

	private static class DatabaseHelper extends SQLiteOpenHelper {
	}
}