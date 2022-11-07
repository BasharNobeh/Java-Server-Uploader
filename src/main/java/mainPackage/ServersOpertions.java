package mainPackage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Nginx.NginxProcess;
import classes.ServerDetails;
import databaseManagment.getData;
import databaseManagment.updateData;
import fileContentMethods.TxtFileContent;
import fileContentMethods.WarFileDirectory;

public class ServersOpertions {

	
	
	
public boolean addDown_Reload_Deploy_UnDown_For_NginServer(ServerDetails nginxServer,File warFile) throws IOException, InterruptedException {
		System.out.println(warFile.toPath());
	    boolean reloadNginxServer=false;
		System.out.println();
        System.out.println("_____ Start server 1 _____");
	    System.out.println();
		    /* add down */
		new TxtFileContent(nginxServer.nginx_config_path).AddTextAfterWordInTxtFile(nginxServer.nginx_down_query);
		    /* reload  */
        if(new NginxProcess().reloadForServer(nginxServer)) {	
             reloadNginxServer=true;
             System.out.println("wait for 5 seconds ......");
             Thread.sleep(5000);
                /*change deploy status */ 
    		 new updateData().StartDeployOn(nginxServer.serverID);
    		    /*deploy */ 
    		 new WarFileDirectory(warFile).copyPasteWarFile(nginxServer.tomcat_path);
                /*delete down */
    		 new TxtFileContent(nginxServer.nginx_config_path).DeleteTextAfterWordInTxtFile(nginxServer.nginx_down_query);
    		    
    		 System.out.println();
    	     System.out.println("_____ End server 1 _____");
        	 System.out.println();
        	    
	     }
         return reloadNginxServer;
}

public void addDown_Reload_Deploy_UnDown_For_OthersServers(ServerDetails nginxServer,ArrayList<ServerDetails>serversData,File warFile) throws IOException, InterruptedException, SQLException {
	
         for(int i=0;i<serversData.size();i++) {
    	
	        System.out.println();
	        System.out.println("_____Start server "+(i+2)+"_____");
    	    System.out.println();
    	    ServerDetails Server=serversData.get(i);
    	    /*add down  */
    	    new TxtFileContent(Server.nginx_config_path).AddTextAfterWordInTxtFile(Server.nginx_down_query);
    	
    	    /*reload server  */
            if(new NginxProcess().reloadForServer(Server)) {
            	System.out.println("wait for 5 seconds ......");
            	Thread.sleep(5000);
                /*change deploy status */ 
    		    new updateData().StartDeployOn(Server.serverID);
            	System.out.println("wait for  seconds ......");

    		    Thread.sleep(2000);
    		    Server=getData.getSpecificServer(Server.serverID);
    		    while(Server.start_deploying==true) {
    		    	/*deploy */ 
	    		    if(new WarFileDirectory(warFile).copyPasteWarFile(Server.tomcat_path)) {
		    		    new updateData().StartDeployOff(Server.serverID);
		    		    Server=getData.getSpecificServer(Server.serverID);
		    		    new TxtFileContent(Server.nginx_config_path).DeleteTextAfterWordInTxtFile(Server.nginx_down_query);
                      if(i==serversData.size()-1) {
                    	 new NginxProcess().reloadForServer(Server);
                      }
	    		    }
	            	System.out.println("wait for 5 seconds ......");
	    		    Thread.sleep(5000);
    		    }
    		   
              }else {
	                 System.out.print("there is an error in nginx process numbers");
	                 }
                     System.out.println();
                     System.out.println("_____end server "+(i+2)+"_____");
                     System.out.println();
                     }
         }

}
