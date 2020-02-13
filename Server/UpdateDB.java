package serverDemo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateDB {
	
	private Connection connectDB;
	private ByteArrayOutputStream byteOutputStream;
	private byte[] imageToByte=new byte[1024];
	private int noFood;
	
	public void InsertAll(int noFood_func) {
		noFood=noFood_func;
		try {
			FileInputStream fileInputStream;
			byteOutputStream=new ByteArrayOutputStream();
			connectDB=SqlConnection.dbConnector("Food");
			for(int noImage=1;noImage<=noFood;noImage++) {
				File image=new File("Resources//Photo//"+noImage+".jpg");
				fileInputStream=new FileInputStream(image);
				for(int readNum;(readNum=fileInputStream.read(imageToByte))!=-1;) {
					byteOutputStream.write(imageToByte,0,readNum);
				}
				PreparedStatement pS=connectDB.prepareStatement("INSERT INTO Food (ID, Photo) VALUES("+noImage+",?)");
				pS.setBytes(1,byteOutputStream.toByteArray());
				pS.executeUpdate();
				pS.close();
				byteOutputStream.flush();
				fileInputStream.close();
			}
			connectDB.close();
			System.out.println("Inserted");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] GetImage(String name_func) {
		String query="SELECT Photo FROM Profile WHERE Username ='"+name_func+"'";
		connectDB=SqlConnection.dbConnector("Food");
		byte []imageArr=null;
		try {
			PreparedStatement stmt=connectDB.prepareStatement(query);
			ResultSet imageRetrive=stmt.executeQuery();
			if(imageRetrive.next()) {
				imageArr=imageRetrive.getBytes("Photo");
			}

			connectDB.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return imageArr;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UpdateDB temp=new UpdateDB();
		temp.GetImage("Burger");
	}
	
	public boolean GetProfile(String username_func) throws SQLException {
		connectDB=SqlConnection.dbConnector("Food");
		String query="SELECT * FROM Profile WHERE Username ='"+username_func+"' ";
		PreparedStatement stmt=connectDB.prepareStatement(query);
		ResultSet dataRetriveResultSet=stmt.executeQuery();
		if(dataRetriveResultSet.next()) {
			connectDB.close();
			return true;			
		}
		else {
			connectDB.close();
			return false;
		}
	}
	
	
	
	public boolean GetProfile(String username_func,String password_func) throws SQLException {
		connectDB=SqlConnection.dbConnector("Food");
		String query="SELECT * FROM Profile WHERE Username ='"+username_func+"' AND Password "
				+ "='"+password_func+"'";
		PreparedStatement stmt=connectDB.prepareStatement(query);
		ResultSet dataRetriveResultSet=stmt.executeQuery();
		if(dataRetriveResultSet.next()) {
			connectDB.close();
			return true;			
		}
		else {
			connectDB.close();
			return false;
		}
	}
	
	public void Update(String name_func,String username_func,String password_func,String phonenumber_func,String email_func,String address_func) {
		connectDB=SqlConnection.dbConnector("Food");
		String query="INSERT INTO Profile(ID,Name,Username,Password,Phonenumber,Email,Address,Photo) VALUES (null,'"
				+name_func+"','"+username_func+"','"+password_func+"','"+phonenumber_func+"','"
				+email_func+"','"+address_func+"',null)";
		try {
			PreparedStatement stmt=connectDB.prepareStatement(query);
			stmt.executeUpdate();
			connectDB.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void UpdateData(String name_func,String username_func,String password_func,String phonenumber_func,String email_func,String address_func) throws SQLException {
		connectDB=SqlConnection.dbConnector("Food");
		String query="Update Profile SET Name ='"+name_func+"',Password='"+password_func+"',Phonenumber='"+
				phonenumber_func+ "',Email='"+email_func+"',Address='"+address_func+"' WHERE USERNAME ='"+username_func+"'";
		try {
			PreparedStatement stmt=connectDB.prepareStatement(query);
			stmt.executeUpdate();
			connectDB.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		connectDB.close();
	}

	public boolean GetProfile(String username_func,String password_func,String []information_func) {
		connectDB=SqlConnection.dbConnector("Food");
		String query="SELECT * FROM Profile WHERE Username ='"+username_func+"' AND Password "
				+ "='"+password_func+"'";
		try {
			PreparedStatement stmt=connectDB.prepareStatement(query);
			ResultSet dataRetriveResultSet=stmt.executeQuery();
			if(dataRetriveResultSet.next()) {
				information_func[0]=Integer.toString(dataRetriveResultSet.getInt("ID"));
				information_func[1]=dataRetriveResultSet.getString("Name");
				information_func[2]=dataRetriveResultSet.getString("Phonenumber");
				information_func[3]=dataRetriveResultSet.getString("Address");
				information_func[4]=dataRetriveResultSet.getString("Email");
				connectDB.close();
				return true;
				
			}
			else {
				connectDB.close();
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void updateImage(String name,byte [] image) throws SQLException {
		connectDB=SqlConnection.dbConnector("Food");
		String query="UPDATE PROFILE SET PHOTO =? WHERE USERNAME ='"+name+"'";
		try {
			PreparedStatement stmt=connectDB.prepareStatement(query);
			stmt.setBytes(1,image);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("Read");
		connectDB.close();
	}
}
