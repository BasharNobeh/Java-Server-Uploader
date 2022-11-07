package Processes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ComputerProcessesContent {
   
	public static int countSpeificProcess(String Processname) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		int count=0;
		try {
			Process process = runtime.exec("tasklist /fi \"imagename eq "+Processname+"\"");
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
				while ((line = br.readLine()) != null) {
					if(line.contains(Processname))
					count++;
				}

		} catch (IOException e) {
			System.out.println("error command.");
		}
		System.out.println();
		System.out.println("the number of process "+Processname+" is "+count);
		return count;
}

}
