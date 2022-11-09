package databaseManagment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import DataBaseConnection.DataBaseConnection;

public class updateData {
	
	
	public void StartDeployOn(int id) {
		
		   
		try {
			PreparedStatement stm = DataBaseConnection.DataBaseConnect().
					   prepareStatement("update server "
					   		+ "set start_deploy=1 "
					   		+ "from server s inner join nginx "
					   		+ "on s.serverID=nginx.FK_serverID where nginx.nginx_server=1"
					   		+ "and s.serverID=?");
			stm.setInt(1, id);
			int result=stm.executeUpdate();
			
			if(result>0) {
				
				System.out.println(CurrentTimeTracker.GetCurrentTime() +" update start_deploy for server id "+id+" to 1  " );
			}
			else {
				System.out.println(CurrentTimeTracker.GetCurrentTime() +" no update for this server to 1");

			}
		} catch (SQLException e) {
			System.out.println(CurrentTimeTracker.GetCurrentTime() +" failed to update start_deploy for serverID "+id+" to 1" );

			e.printStackTrace();
		}
	}
    public void StartDeployOff(int id) {
    	try {
			PreparedStatement stm = DataBaseConnection.DataBaseConnect().
					 prepareStatement("update server "
						   		+ "set start_deploy=0 "
						   		+ "from server s inner join nginx "
						   		+ "on s.serverID=nginx.FK_serverID where nginx.nginx_server=1"
						   		+ "and s.serverID=?");
			stm.setInt(1, id);
			int result=stm.executeUpdate();
			if(result>0) {
				System.out.println(CurrentTimeTracker.GetCurrentTime() +" update start_deploy for server id "+id+" to 0");
			}
			else {
				System.out.println(CurrentTimeTracker.GetCurrentTime() +" no update for this server to 0");

			}
		} catch (SQLException e) {
			System.out.println(CurrentTimeTracker.GetCurrentTime() +" failed to update start_deploy for serverID"+id +"to 0");
			e.printStackTrace();
		}
	}
}
