import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FilenameUtils;


import mainPackage.MainMethodToStartWith;
/* to import org.apache.commons.io.FilenameUtils Please add : 
 * 
 *  		<dependency>
  <groupId>commons-io</groupId>
  <artifactId>commons-io</artifactId>
  <version>2.11.0</version>
</dependency>  
 */
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
/**
 * Servlet implementation class fileuploadservlet
 */
@SuppressWarnings("serial")
@WebServlet("/fileuploadservlet")
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 10 MB
		  maxFileSize = 1024 * 1024 * 1000,      // 1000 MB 1GB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class fileuploadservlet extends HttpServlet {
	
	private String sql = "SELECT Uploading_Path FROM TestDB.dbo.server WHERE Uploading_URL =?";
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//###########################################################################################
		 String  url = request.getRequestURL().toString();
		//############################################################################################
		
		StringBuilder str = new StringBuilder();
		// ## SQL part where we get the Uploading path of the servlet 
		String path = ""; 
		try {
			
			PreparedStatement st = DataBaseConnection.DataBaseConnect().prepareStatement(sql);
			st.setString(1, url);
			ResultSet rs = st.executeQuery();	
						
			while (rs.next()) {
	        	if (rs.getString(1) != null ) {
	        		path = rs.getString("Uploading_Path");	        		
	        	}					
			}
		}catch(Exception e) {str.append(" An Error Happened ");}
		// The end of the sql part 
		
		// ## Creating a session to track the logs 
		HttpSession session = request.getSession();
		session.removeAttribute("data");
		session.setAttribute("data", str);
		//  end of the session creation part 
			
		Part filePart = request.getPart("file");
		
	    String fileName = filePart.getSubmittedFileName();
	    str.append("Server 1 : Loading Your file .. " + fileName);
	    
	    //## Getting file names inside the folder 
		  File directoryPath = new File(path);
	    //##List of all files and directories in the selected path
	      String contents[] = directoryPath.list();     
	      //## Getting file extension
	      String extension = FilenameUtils.getExtension(fileName);	      
	      //## Uploading the file and changing the name of the old file (If found)  
	      File file = null ;
	      for(int i=0; i<contents.length; i++) {
	    	  if (contents[i].equals(fileName) ) {
	    		  str.append("\nFound A file with the same name in directory...\n " );
	    		   		 
	    		  Calendar cal = Calendar.getInstance();
	    	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	        String date = sdf.format(cal.getTime());
	    	        date = date.replaceAll("\\s", "_");
	    	        date = date.replaceAll(":", "-");
	    	        
	    	       String [] FileNameArray = fileName.split("."+extension);
	    	        file = new File(path+"\\" +fileName);
	    		 try {
	    			 
		    		 
	 	    		
		    		  String oldFileName = FileNameArray[0]+date+"."+extension;
		    		  File rename = new File(path+"\\"+oldFileName);
		    		  
		    		  file.renameTo(rename);
		    		  str.append("\nSwapped the name succesfully\n New file name : " + fileName +"\nOld file name : " + oldFileName);
		    		  
	    		 }catch(Exception  e){
	    			 str.append("An Error Happened");
	    			 } 	    		
	    		 			};	    		 			
	    		 					}	
	      
	      for (Part part : request.getParts()) {
	    	  
	    	  part.write(path+"\\" + fileName);
	    	  }
	      
	      // ######################
	      HttpSession session3 = request.getSession();
	      try {
			MainMethodToStartWith.deployWarFileToAllServers(file, (String)session3.getAttribute("ServersData") );
		} catch (SQLException e) {
			
			System.out.println("An Error Happened 1 ");
		} catch (IOException e) {
			
			//System.out.println("An Error Happened 2 ");
			System.out.println(e);

		} catch (InterruptedException e) {
			
			System.out.println("An Error Happened 3 ");
		}
	   // ######################
	      
	      
	    //## End of the Uploading Part
	      
	      
	      
	      
	      str.append("\n ..Uploading Complete .. D@NE");	        
	  }
	
	}