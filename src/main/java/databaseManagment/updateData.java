package databaseManagment;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import DataBaseConnection.DataBaseConnection;
import classes.ServerDetails;

public class updateData {
	
	
	public void StartDeployOn(int id) {
		
		   
		try {
			PreparedStatement stm = DataBaseConnection.DataBaseConnect().
					   prepareStatement("update server "
					   		+ "set start_deploy=1 "
					   		+ "from server s inner join nginx "
					   		+ "on s.serverID=nginx.FK_serverID where s.serverID=?"
					   		);
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
						   		+ "on s.serverID=nginx.FK_serverID where s.serverID=?"
						   		);
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
    public void incrementDeployServer(ServerDetails server) {
    	try {
    		ServerDetails serverFromDatabase=getData.getSpecificServer(server.serverID);
    		int startDeply=serverFromDatabase.start_deploying;
    		int startDeplyWithIncrement=serverFromDatabase.start_deploying+1;
			PreparedStatement stm = DataBaseConnection.DataBaseConnect().
					 prepareStatement("update server "
						   		+ "set start_deploy=? "
						   		+ "from server s inner join nginx "
						   		+ "on s.serverID=nginx.FK_serverID where nginx.nginx_server=1"
						   		+ "and s.serverID=? and s.start_deploy=0");
			stm.setInt(1, startDeplyWithIncrement);
			stm.setInt(2, server.serverID);

			int result=stm.executeUpdate();
			if(result>0) {
				System.out.println(CurrentTimeTracker.GetCurrentTime() +" increment the server "+server.serverID+""
						+ " start deploy from  "+startDeply+" to "+startDeplyWithIncrement);
			}
			else {
				System.out.println(CurrentTimeTracker.GetCurrentTime() +" there is no server with this id to increment");

			}
		} catch (SQLException e) {
			System.out.println(CurrentTimeTracker.GetCurrentTime() +" there is an error in increment for this server");
			e.printStackTrace();
		}
	}

}
