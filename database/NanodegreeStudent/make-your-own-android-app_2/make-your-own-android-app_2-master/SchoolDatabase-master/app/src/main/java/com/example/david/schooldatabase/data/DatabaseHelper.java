package com.example.david.schooldatabase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by david on 3/13/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "SchoolDB";
    static final String STUDENTS_TABLE_NAME = "students";
    static final String COURSES_TABLE_NAME = "courses";
    static final String ENROLLMENTS_TABLE_NAME = "enrollments";
    static final String ASSESSMENTS_TABLE_NAME = "assessments";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE_1 =
            " CREATE TABLE " + STUDENTS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " index_no TEXT NOT NULL, " +
                    " first_name TEXT NOT NULL, " +
                    " last_name TEXT NOT NULL, " +
                    " department TEXT NOT NULL, " +
                    " level INTEGER NOT NULL);";

    static final String CREATE_DB_TABLE_2 =
            " CREATE TABLE " + COURSES_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " course_code TEXT NOT NULL, " +
                    " course_title TEXT NOT NULL, " +
                    " credits INTEGER NOT NULL);";

    static final String CREATE_DB_TABLE_3 =
            " CREATE TABLE " + ENROLLMENTS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " index_no TEXT NOT NULL, " +
                    " academic_year TEXT NOT NULL, " +
                    " semester TEXT NOT NULL, " +
                    " course_code TEXT NOT NULL);";

    static final String CREATE_DB_TABLE_4 =
            " CREATE TABLE " + ASSESSMENTS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " index_no TEXT NOT NULL, " +
                    " academic_year TEXT NOT NULL, " +
                    " semester TEXT NOT NULL, " +
                    " course_code TEXT NOT NULL, " +
                    " attendance TEXT NOT NULL, " +
                    " mid_semester TEXT NOT NULL, " +
                    " final_exam TEXT NOT NULL);";
}
