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
	
		
	   new ServersOpertions().addDown_Reload_Deploy_UnDown_Servers(serverList,warFile);
	        	
	        	
            
	
	    
	}
}
