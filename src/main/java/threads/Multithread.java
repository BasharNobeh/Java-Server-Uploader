package threads;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import classes.ServerDetails;
import databaseManagment.getData;
import databaseManagment.updateData;
import fileContentMethods.WarFileDirectory;

public class Multithread implements Runnable {
	ServerDetails server;
	File warFile;
	public Multithread(ServerDetails server,File warFile) {
		this.server=server;
		this.warFile=warFile;
	}
	@Override
	public void run() {
    
		int startDeployValue = 0;
		try {
			startDeployValue = getData.GetDevelopmentTableData().startDeployValue;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		while(server.start_deploying<startDeployValue) {
			
			
		
		 
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		   

		

		
	}
		try {
			new WarFileDirectory(warFile).copyPasteWarFile(server.tomcat_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    new updateData().StartDeployOff(server.serverID);
	
}
    
	
}
