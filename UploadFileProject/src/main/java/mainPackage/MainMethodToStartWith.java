package mainPackage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import classes.ServerDetails;

public class MainMethodToStartWith {

	
	public static void deployWarFileToAllServers(File warFile,String ServersArray) throws SQLException, IOException, InterruptedException {
       Gson gson=new Gson();	
       Type type =new TypeToken<ArrayList<ServerDetails>>() {}.getType();
       ArrayList<ServerDetails>serverList=gson.fromJson(ServersArray,type);
       ArrayList<ServerDetails>OthersServers=new ArrayList<>();
		ServerDetails nginxServer=null;
        for(ServerDetails server:serverList) {
        	if(server.ngin_server) {
        		nginxServer=server;
        	}
        	else {
        		OthersServers.add(server);
        	}
        }
		if(nginxServer!=null) {
			System.out.print("dfsf");
	        if(new ServersOpertions().addDown_Reload_Deploy_UnDown_For_NginServer(nginxServer,warFile)) { 
	        	
	        	new ServersOpertions().addDown_Reload_Deploy_UnDown_For_OthersServers(nginxServer,OthersServers,warFile);
	        	
            }else {
    		    System.out.println("error in reloading nginx");
            }
	
	    }else {
		   System.out.println("there's no nginx server");
	    }
	}
}
