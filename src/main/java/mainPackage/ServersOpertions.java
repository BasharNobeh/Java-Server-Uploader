package mainPackage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Nginx.NginxProcess;
import classes.ServerDetails;
import databaseManagment.CurrentTimeTracker;
import databaseManagment.getData;
import databaseManagment.updateData;
import fileContentMethods.TxtFileContent;
import fileContentMethods.WarFileDirectory;
import threads.Multithread;

public class ServersOpertions {

	
	
	
public void addDown_Reload_Deploy_UnDown_NginxServers(ArrayList<ServerDetails>serversData,File warFile) throws IOException, InterruptedException, SQLException {
	int j=1;
	
	
		    for(int i=0;i<serversData.size();i++) {
                 System.out.println("starting thread server "+(i+1));
			     Thread s=new Thread(new Multithread(serversData.get(i),warFile));
			     s.start();
		    }
		    
    for(int i=0;i<serversData.size();i++) {
	    ServerDetails Server=serversData.get(i);
		
    	if(Server.ngin_server==true) {
    		
	     System.out.println();
	     System.out.println(CurrentTimeTracker.GetCurrentTime() +" _____Start server "+(j)+"_____");
 	     System.out.println();
		    /* add down */
		 new TxtFileContent(Server.nginx_config_path).AddTextAfterWordInTxtFile(Server.nginx_down_query);
		    /* reload  */
		  if(new NginxProcess().reloadForServer(Server)) {
          	System.out.println(CurrentTimeTracker.GetCurrentTime() +" wait for 5 seconds ......");
          	Thread.sleep(5000);
          	new updateData().incrementDeployServer(Server);
              /*change deploy status */ 
  		    //new updateData().StartDeployOn(Server.serverID);

  		    //Server=getData.getSpecificServer(Server.serverID);
//  		    while(Server.start_deploying==1) {
//  		    	/*deploy */ 
//	    		    if(new WarFileDirectory(warFile).copyPasteWarFile(Server.tomcat_path)) {
//		    		    new updateData().StartDeployOff(Server.serverID);
//		    		    Server=getData.getSpecificServer(Server.serverID);
//		    		    new TxtFileContent(Server.nginx_config_path).DeleteTextAfterWordInTxtFile(Server.nginx_down_query);
//                    if(i==serversData.size()-1) {
//                  	 new NginxProcess().reloadForServer(Server);
//                    }
//	    		    }
//	            	System.out.println(CurrentTimeTracker.GetCurrentTime() +" wait for 5 seconds ......");
//	    		    Thread.sleep(5000);
//  		    }
  		   
            }else {
	              System.out.print(CurrentTimeTracker.GetCurrentTime() +" there is an error in nginx process numbers");
	                 }
             System.out.println();
             System.out.println(CurrentTimeTracker.GetCurrentTime() +" _____end server "+(j++)+"_____");
             System.out.println();
        	    
	     }
    }

    }
public void addDown_Reload_Deploy_UnDown_For_OthersServers(ArrayList<ServerDetails>serversData,File warFile) throws IOException, InterruptedException, SQLException {
	
    for(int i=0;i<serversData.size();i++) {
	
       System.out.println();
       System.out.println(CurrentTimeTracker.GetCurrentTime() +" _____Start server "+(i+2)+"_____");
	    System.out.println();
	    ServerDetails Server=serversData.get(i);
	    /*add down  */
	    new TxtFileContent(Server.nginx_config_path).AddTextAfterWordInTxtFile(Server.nginx_down_query);
	
	    /*reload server  */
       if(new NginxProcess().reloadForServer(Server)) {
       	System.out.println(CurrentTimeTracker.GetCurrentTime() +" wait for 5 seconds ......");
       	Thread.sleep(5000);
           /*change deploy status */ 
		    new updateData().StartDeployOn(Server.serverID);

		    Server=getData.getSpecificServer(Server.serverID);
		    while(Server.start_deploying==1) {
		    	/*deploy */ 
		    	System.out.print("start deploying is 1 ");
   		    if(new WarFileDirectory(warFile).copyPasteWarFile(Server.tomcat_path)) {
	    		    new updateData().StartDeployOff(Server.serverID);
	    		    Server=getData.getSpecificServer(Server.serverID);
	    		    new TxtFileContent(Server.nginx_config_path).DeleteTextAfterWordInTxtFile(Server.nginx_down_query);
                 if(i==serversData.size()-1) {
               	 new NginxProcess().reloadForServer(Server);
                 }
   		    }
           	System.out.println(CurrentTimeTracker.GetCurrentTime() +" wait for 5 seconds ......");
   		    Thread.sleep(5000);
		    }
		   
         }else {
                System.out.print(CurrentTimeTracker.GetCurrentTime() +" there is an error in nginx process numbers");
                }
                System.out.println();
                System.out.println(CurrentTimeTracker.GetCurrentTime() +" _____end server "+(i+2)+"_____");
                System.out.println();
                }
    }



}
