package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;



import controller.Controller;

public class Manager {
	
	public static final String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String URL = "jdbc:sqlserver://localhost;databaseName=School";
	public static final String STUDENT="Student", CLASS="Class", ENROLLED_CLASSES="EnrolledClasses";
	
	public static final String INNER_JOIN_QUERY = "SELECT EnrolledClasses.StudentID, Student.Name, Class.ID,Class.Title FROM Student INNER JOIN EnrolledClasses " +
			"ON Student.ID=EnrolledClasses.StudentID INNER JOIN Class ON Class.ID=EnrolledClasses.ClassID";
	
	private Connection connection;
	private Controller control;

}
