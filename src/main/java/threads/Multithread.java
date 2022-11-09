package threads;

import java.io.File;
import java.io.IOException;

import classes.ServerDetails;
import databaseManagment.getData;
import databaseManagment.updateData;
import fileContentMethods.TxtFileContent;
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
         
		
			 int totalStartDeploy=getData.getStartDeployTotal();
			 int startDeployValue = getData.GetDevelopmentTableData().startDeployValue;
		while(totalStartDeploy<startDeployValue) {

			totalStartDeploy=getData.getStartDeployTotal();

					try {
						
						System.out.println();
						System.out.println(" inside while ,server "+server.serverID + " SD ="+totalStartDeploy +" SDV ="+startDeployValue);

						System.out.println();


						totalStartDeploy=getData.getStartDeployTotal();
						Thread.sleep(5000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				

				
		   

		

		
	}
		try {
			new WarFileDirectory(warFile).copyPasteWarFile(server.tomcat_path);
			new updateData().StartDeployOff(server.serverID);
			new TxtFileContent(server.nginx_config_path).DeleteTextAfterWordInTxtFile(server.nginx_down_query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   

}
    
	
}
