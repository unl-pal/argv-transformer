package core;

import java.sql.*;
import java.util.ArrayList;

import model.CharacterModel;
import model.UserModel;

public class Database {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
}
