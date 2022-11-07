package Nginx;

import java.io.IOException;

import Processes.ComputerProcessesContent;
import classes.ServerDetails;

public class NginxProcess {

	
	public boolean reloadForServer(ServerDetails server) throws IOException, InterruptedException {
		   int count=0;
           Long startTime=System.currentTimeMillis();
           boolean count41Or21=false;
           do {
/*count how many processes */count=ComputerProcessesContent.countSpeificProcess(server.nginx_process_name);
                     if(count==0) {
/*start nginx  */              NginxCommandsExecute.startNginx(server.nginx_path,server.nginx_process_name);
                               System.out.println("can't reload , because nginx service is not working");
                      }
                      else if(count==server.ngin_min||count==server.nginx_max) {
                              System.out.println("reload nginx");
/*reload nginx  */                   NginxCommandsExecute.reloadNginx(server.nginx_path,server.nginx_process_name);
	                             count41Or21=true;
                                Thread.sleep(5000);
                              break;
	                     }
                      else {
                          System.out.println("try to stop nginx");

                    	 NginxCommandsExecute.StopNginx(server.nginx_path,server.nginx_process_name);

                      }
         Thread.sleep(2000);
           }while(!count41Or21 &&((System.currentTimeMillis()-startTime)<30000));
           
           return count41Or21;
	}
}
