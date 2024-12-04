package com.example.david.schooldatabase.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by david on 3/6/15.
 */

public class SchoolDBProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.david.schooldatabase";
    static final String URL_1 = "content://" + PROVIDER_NAME + "/students";
    public static final Uri CONTENT_URI_1 = Uri.parse(URL_1);
    static final String URL_2 = "content://" + PROVIDER_NAME + "/courses";
    public static final Uri CONTENT_URI_2 = Uri.parse(URL_2);
    static final String URL_3 = "content://" + PROVIDER_NAME + "/enrollments";
    public static final Uri CONTENT_URI_3 = Uri.parse(URL_3);
    static final String URL_4 = "content://" + PROVIDER_NAME + "/assessments";
    public static final Uri CONTENT_URI_4 = Uri.parse(URL_4);
    public static final String _ID = "_id";
    public static final String INDEX_NO = "index_no";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String DEPARTMENT = "department";
    public static final String LEVEL = "level";
    public static final String COURSE_CODE = "course_code";
    public static final String COURSE_TITLE = "course_title";
    public static final String CREDITS = "credits";
    public static final String ACADEMIC_YEAR = "academic_year";
    public static final String SEMESTER = "semester";
    public static final String ATTENDANCE = "attendance";
    public static final String MID_SEMESTER = "mid_semester";
    public static final String FINAL_EXAM = "final_exam";

    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;
    private static HashMap<String, String> COURSES_PROJECTION_MAP;
    private static HashMap<String, String> ENROLLMENTS_PROJECTION_MAP;
    private static HashMap<String, String> ASSESSMENTS_PROJECTION_MAP;

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;
    static final int COURSES = 3;
    static final int COURSE_ID = 4;
    static final int ENROLLMENTS = 5;
    static final int ENROLLMENT_ID = 6;
    static final int ASSESSMENTS = 7;
    static final int ASSESSMENT_ID = 8;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
        uriMatcher.addURI(PROVIDER_NAME, "courses", COURSES);
        uriMatcher.addURI(PROVIDER_NAME, "courses/#", COURSE_ID);
        uriMatcher.addURI(PROVIDER_NAME, "enrollments", ENROLLMENTS);
        uriMatcher.addURI(PROVIDER_NAME, "enrollments/#", ENROLLMENT_ID);
        uriMatcher.addURI(PROVIDER_NAME, "assessments", ASSESSMENTS);
        uriMatcher.addURI(PROVIDER_NAME, "assessments/#", ASSESSMENT_ID);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;

}