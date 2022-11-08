	package databaseManagment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DataBaseConnection.DataBaseConnection;
import classes.DevelopmentDetails;
import classes.ServerDetails;

public class getData {
	
	
	
    public static ArrayList<ServerDetails> ServersData(int nginxID) throws SQLException{
		ArrayList<ServerDetails> data=new ArrayList<ServerDetails>();
	   PreparedStatement stm=DataBaseConnection.DataBaseConnect().
			   prepareStatement("select serverID,start_deploy,tomcat_path,status,"
			   		+ "nginxID,nginx_server,nginx_path,nginx_conf_path,nginx_down_query,"
			   		+ "nginx_process_name,nginx_reload_server,nginx_min,nginx_max from server"
	   		+ " Inner Join nginx on nginx.FK_serverID=server.serverID where nginx.nginxID!=?");
	           stm.setInt(1, nginxID);
	   ResultSet result=stm.executeQuery();
	   while(result.next()){
	    
	     data.add(new ServerDetails(
	     result.getInt(1),
 	     result.getInt(2),
 	     result.getString(3),
 	     result.getBoolean(4),
         result.getInt(5),
 	     result.getBoolean(6),
 	     result.getString(7),
 	     result.getString(8),
 	     result.getString(9),
 	     result.getString(10),
 	     result.getBoolean(11),
 	     result.getInt(12),
 	     result.getInt(13)));
 	     
	   }
	return data;
}
    public static ServerDetails nginxServer() throws SQLException{

	   PreparedStatement stm= DataBaseConnection.DataBaseConnect().
			   prepareStatement("select serverID,start_deploy,tomcat_path,status,"
			   		+ "nginxID,nginx_server,nginx_path,nginx_conf_path,nginx_down_query,"
			   		+ "nginx_process_name,nginx_reload_server,nginx_min,nginx_max from server"
	   		+ " Inner Join nginx on nginx.FK_serverID=server.serverID where nginx.nginx_server=1");
	   ResultSet result=stm.executeQuery();
	   if(result.next()) {
	return new ServerDetails(
		     result.getInt(1),
	 	     result.getInt(2),
	 	     result.getString(3),
	 	     result.getBoolean(4),
	         result.getInt(5),
	 	     result.getBoolean(6),
	 	     result.getString(7),
	 	     result.getString(8),
	 	     result.getString(9),
	 	     result.getString(10),
	 	     result.getBoolean(11),
	 	     result.getInt(12),
	 	     result.getInt(13));
	   }
	   else {
		   return null;
	   }
}
    public static ServerDetails getSpecificServer(int serverID) throws SQLException{
	   PreparedStatement stm=DataBaseConnection.DataBaseConnect().
			   prepareStatement("select serverID,start_deploy,tomcat_path,status,"
			   		+ "nginxID,nginx_server,nginx_path,nginx_conf_path,nginx_down_query,"
			   		+ "nginx_process_name,nginx_reload_server,nginx_min,nginx_max from server"
	   		+ " Inner Join nginx on nginx.FK_serverID=server.serverID where server.serverID=?");
	          stm.setInt(1, serverID);
	   ResultSet result=stm.executeQuery();
	   if(result.next()) {
	return new ServerDetails(
		     result.getInt(1),
	 	     result.getInt(2),
	 	     result.getString(3),
	 	     result.getBoolean(4),
	         result.getInt(5),
	 	     result.getBoolean(6),
	 	     result.getString(7),
	 	     result.getString(8),
	 	     result.getString(9),
	 	     result.getString(10),
	 	     result.getBoolean(11),
	 	     result.getInt(12),
	 	     result.getInt(13));
	   }
	   else {
		   return null;
	   }
    }
    public static DevelopmentDetails GetDevelopmentTableData() throws SQLException{
 	   PreparedStatement stm=DataBaseConnection.DataBaseConnect().
 			   prepareStatement("select * from development ");
 	   ResultSet result=stm.executeQuery();
 	   if(result.next()) {
 	return new DevelopmentDetails(
 		     result.getInt(1),
 	 	     result.getInt(2));
 	 	   
 	   } else {
 		   return null;

}
    }
}
