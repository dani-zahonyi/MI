package miproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Stack;

public class DBConnect {
	// jdbc:oracle:thin:@hostname:port Number:databaseName

	// MI_ZAHDAN@//oracle.itk.ppke.hu:1521/gyak

	static private String userName = "MI_ZAHDAN";
	static private String password = "A29490";
	static private Connection connection;
	static final String DB_URL = "jdbc:oracle:thin:MI_ZAHDAN@//oracle.itk.ppke.hu:1521/gyak";
	private static HashMap<String, Integer> artists = new HashMap<String, Integer>();

	
	public DBConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DBConnect.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insertRecordIntoDbUserTable(String user,
			Stack<String> artists) throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String insertTableSQL = "INSERT INTO USER_MUSIC"
				+ "(USERNAME, ARTIST) " + "VALUES" + "('" + user + "','"
				+ artists.pop() + "' " + ")";

		try {
			statement = connection.createStatement();

			System.out.println(insertTableSQL);

			// execute insert SQL stetement
			statement.executeUpdate(insertTableSQL);

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		if (!artists.isEmpty())
			insertRecordIntoDbUserTable(user, artists);

	}

	public HashMap<String, String> getRandIDs() throws SQLException {
		HashMap<String, String> ids = new HashMap<String, String>();
		Random rand = new Random();

		int randomNum = rand.nextInt((500 - 100) + 1) + 100;
		Statement stmt = null;
		String query = "SELECT * FROM "
				+ "( SELECT * FROM top100 ORDER BY dbms_random.value ) "
				+ "WHERE rownum < 11";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ids.put(rs.getString("artist"), rs.getString("myspace_id"));

				System.out.println(ids.get(ids.size() - 1));
			}
		} catch (SQLException e) {

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return ids;
	}

	public ArrayList<String> getGenres(String artist) throws SQLException {
		ArrayList<String> genres = new ArrayList<String>();
		Statement stmt = null;
		String query = "SELECT  genre AS genre FROM music_genre WHERE LOWER('"
				+ artist + "') = LOWER('drudkh')";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("valami");
			while (rs.next()) {
				genres.add(rs.getString("genre"));
			}
		} catch (SQLException e) {
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		closeConnection();
		return genres;
	}

	public ArrayList<ArrayList<Boolean>> likedArtists() throws SQLException {

		ArrayList<ArrayList<Boolean>> likedArtists = new ArrayList<ArrayList<Boolean>>();
		connection = getConnection();

		HashMap<String, Integer> users = new HashMap<String, Integer>();
		
		Statement stmt = null;
		String query = "select distinct username from user_music order by username";
		String query2 = "select distinct artist from user_music order by artist";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int index = 0;
			while (rs.next()) {
				users.put(rs.getString("username"), index++);
			}
			rs = stmt.executeQuery(query2);
			index = 0;
			while (rs.next()) {
				artists.put(rs.getString("artist"), index++);
			}

			String queryAll = "select distinct artist, username from user_music order by artist";
			for (int i = 0; i < artists.size(); i++) {
				likedArtists.add(new ArrayList<Boolean>());
				for(int j=0; j<users.size(); j++)
					likedArtists.get(i).add(false);
			}
			
			rs = stmt.executeQuery(queryAll);
			while (rs.next()) {
				likedArtists.get(artists.get(rs.getString("artist"))).set(users.get(rs.getString("username")),true);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		closeConnection();

		return likedArtists;
	}

	public static int getAritstIndex(String artist){
//		System.out.println(artists);
		
		if(artists.containsKey(artist))
			return artists.get(artist);
		
		System.err.println("NEM TALÁLTA");
		return 0;
	}
	public static String getAritstByIndex(int index){
		
		    for (Entry<String, Integer> entry : artists.entrySet()) {
		        if (index==entry.getValue()) {
		            return entry.getKey();
		        }
		    }
		    return null;
	}
	
	static public void closeConnection() throws SQLException {
		if (!connection.isClosed())
			connection.close();
	}

	static public Connection getConnection() throws SQLException {

		Connection conn = null;

		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection(DB_URL, connectionProps);

		System.out.println("Connected to database");
		return conn;
	}

}
