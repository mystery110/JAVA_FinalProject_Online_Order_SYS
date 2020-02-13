package finalDemo;
import java.sql.*;
import javax.swing.*;

public class SqlConnection {

	public static final String TABLE_NAME = "Profile";
	public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String PHONENUMBER = "Phonenumber";
    public static final String EMAIL = "Email";
    public static final String ADDRESS = "Address";
    public static final String PHOTO = "Photo";
	
	public static final Integer COL_ID = 1;
    public static final Integer COL_NAME = 2;
    public static final Integer COL_USERNAME = 3;
    public static final Integer COL_PASSWORD = 4;
    public static final Integer COL_PHONENUMBER = 5;
    public static final Integer COL_EMAIL = 6;
    public static final Integer COL_ADDRESS = 7;
    public static final Integer COL_PHOTO = 8;
    
	private static Connection connection = null;
	public static Connection dbConnector()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			//String path = "jdbc:sqlite:C:\\Users\\user\\eclipse-workspace\\serverDemo\\src\\Company.db";
			
			String path="jdbc:sqlite:ClientDB.db";			
			connection = DriverManager.getConnection(path);
			
			return connection;
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
	public static void CreateTable() throws SQLException {
		Connection connectdb=dbConnector();
		String query= "DROP TABLE IF EXISTS FOOD";
		PreparedStatement stmt=connectdb.prepareStatement(query);
		stmt.execute();
		
		query= "DROP TABLE IF EXISTS PROFILE";
		stmt=connectdb.prepareStatement(query);
		stmt.execute();
		
		
		query="CREATE TABLE Food (ID INTEGER ,PHOTO BLOB,NAME TEXT,PRICE INTEGER,DESCRIPTION TEXT)";
		 stmt=connectdb.prepareStatement(query);
		stmt.execute();
		
		query="CREATE TABLE Profile (ID INTEGER ,NAME TEXT, USERNAME TEXT,PASSWORD TEXT, "
				+ "PHONENUMBER TEXT,EMAIL TEXT,ADDRESS TEXT,PHOTO BLOB)";
		stmt=connectdb.prepareStatement(query);
		stmt.execute();
		connectdb.close();
		
	}
	public String[] getData(String username, Connection connection)
	{
		String[] data = new String[6];
		try {
			String query = "SELECT * FROM " + TABLE_NAME + " WHERE Username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) 
			{
				data[0] = resultSet.getString(NAME);
				data[1] = resultSet.getString(USERNAME);
				data[2] = resultSet.getString(PASSWORD);
				data[3] = resultSet.getString(PHONENUMBER);
				data[4] = resultSet.getString(EMAIL);
				data[5] = resultSet.getString(ADDRESS);
			}
			preparedStatement.close();
			resultSet.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public byte[] getImage(String username, Connection connection)
	{
		byte[] imageArray = null;
		try {
			String query = "SELECT * FROM " + TABLE_NAME + " WHERE Username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) 
			{
				imageArray = resultSet.getBytes(COL_PHOTO);
			}
			preparedStatement.close();
			resultSet.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return imageArray;
	}
	
	public void updateData(String query, byte[] image, Connection connection)
	{
		try {
			if (image == null) 
			{
				query = query.substring(0, query.length()-1);
				query += " WHERE Username = '" + LoginPage.USERNAME + "'";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
				preparedStatement.close();
			}
			else 
			{
				query += " Photo = ?";
				query += " WHERE Username = '" + LoginPage.USERNAME + "'";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setBytes(1, image);
				preparedStatement.execute();
				preparedStatement.close();
			}
			JOptionPane.showMessageDialog(null, "Update Successfully");	
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
