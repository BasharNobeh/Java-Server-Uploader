package Nginx;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import databaseManagment.CurrentTimeTracker;

public class NginxCommandsExecute {
	
    public static  void startNginx(String nginxLocation,String nginxProcessName) throws IOException, InterruptedException {
		try {
		
            System.out.println("try to start nginx ");
			ProcessBuilder builder = new ProcessBuilder(
					"cmd","/c",nginxLocation+nginxProcessName,"/removeDrive", "driveLocation");
			System.out.println(nginxLocation+nginxProcessName);
		         builder.start();
		       

		
		} catch (IOException e) {
			System.out.println(CurrentTimeTracker.GetCurrentTime() +" error command , can't start nginx");
		}
		
	

	}

	public static  void reloadNginx(String nginxLocation,String nginxProcessName) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("cmd /c \"cd "+nginxLocation+" &&  "+nginxProcessName+" -s reload\"");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			
			
		
		} catch (IOException e) {
			System.out.print(CurrentTimeTracker.GetCurrentTime() +" error command.");
		}
		
	

	}
	public static  void StopNginx(String nginxLocation,String nginxProcessName) throws IOException {
		Runtime runtime = Runtime.getRuntime();

		try {
			Process process = runtime.exec("cmd /c \"cd "+nginxLocation+" &&  "+nginxProcessName+" -s stop\"");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}			
		
		} catch (IOException e) {
			System.out.print(CurrentTimeTracker.GetCurrentTime() +" error command.");
		}
		
	

	}
	

}
