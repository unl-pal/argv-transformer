package database;

import constants.Constants;
import entity.Discipline;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class DBConnection implements Constants {
    private Connection conn = null;
    private ResultSet rs = null;
    private static PreparedStatement getDisciplinesList;
    private static PreparedStatement setDiscipline;
    private static PreparedStatement setDisciplinesByIdDelete;
    private static PreparedStatement getDisciplineById;
    private static PreparedStatement setDisciplineByIdUpdate;
}
