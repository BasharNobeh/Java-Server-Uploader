package databaseManagment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeTracker {
	
	public static String GetCurrentTime() {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		  LocalDateTime now = LocalDateTime.now();  
		
		
		
		return dtf.format(now);
	}

}
