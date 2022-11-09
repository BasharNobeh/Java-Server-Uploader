

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
/*
 * <dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.10</version>
		</dependency>
 * 
 */

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



class ServerDetails {
    public int serverID;
    public boolean start_deploying;
    public String tomcat_path;
    public boolean status;
    public String Uploading_URL;
    public String logTracker;
    public String Upload_Path;
    public int nginx_id;
    public ServerDetails(int serverID, boolean start_deploying, String tomcat_path, boolean status,
			String uploading_URL, String logTracker, String upload_Path, int nginx_id, boolean ngin_server,
			String nginx_path, String nginx_config_path, String nginx_down_query, String nginx_process_name,
			boolean nginx_reload_server, int ngin_min, int nginx_max) {
		
		this.serverID = serverID;
		this.start_deploying = start_deploying;
		this.tomcat_path = tomcat_path;
		this.status = status;
		this.Uploading_URL = uploading_URL;
		this.logTracker = logTracker;
		this.Upload_Path = upload_Path;
		this.nginx_id = nginx_id;
		this.ngin_server = ngin_server;
		this.nginx_path = nginx_path;
		this.nginx_config_path = nginx_config_path;
		this.nginx_down_query = nginx_down_query;
		this.nginx_process_name = nginx_process_name;
		this.nginx_reload_server = nginx_reload_server;
		this.ngin_min = ngin_min;
		this.nginx_max = nginx_max;
	}
	public boolean ngin_server;
    public String nginx_path;
    public String nginx_config_path;
    public String nginx_down_query;
    public String nginx_process_name;
    public  boolean nginx_reload_server;
    public int ngin_min;
    public int nginx_max;
	

    
	
   

}

@WebServlet("/GetServersData")
public class GetServersData extends HttpServlet {
	
	
	String sql = "SELECT * FROM TestDB.dbo.server FULL JOIN TestDB.dbo.nginx on TestDB.dbo.server.serverID = TestDB.dbo.nginx.FK_serverID ;";
	 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
		
			
			PreparedStatement st = DataBaseConnection.DataBaseConnect().prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();
			
			
			response.setStatus(200);
			Gson gson = new Gson();
			
			response.setContentType("application/json");
	        response.setCharacterEncoding("utf-8");
	        
	        Set<ServerDetails> Data = new HashSet();
	        Gson gson1 = new Gson();
	        
	        while (rs.next()) {
	        	if (rs.getString(5) != null && rs.getString(6)!= null && rs.getString(7)!= null  &&rs.getString(11)!= null &&rs.getString(12) != null && rs.getString(10) != null  ) {
	        		Data.add(new ServerDetails (rs.getInt(1),rs.getBoolean(2),
	        				rs.getString(3),rs.getBoolean(4),
	        				rs.getString(5),rs.getString(6),
	        				rs.getString(7),rs.getInt(8),
	        				rs.getBoolean(10),rs.getString(11),
	        				rs.getString(12),rs.getString(13),
	        				rs.getString(14),rs.getBoolean(15),
	        				rs.getInt(16) ,rs.getInt(17)));	
	        	}
	        	
				
			}
	        
	        String jsonData = gson1.toJson(Data);
	        
	        PrintWriter out = response.getWriter();
	        request.setAttribute("ServersData", jsonData);
	        out.println(jsonData);
	        
	        HttpSession session = request.getSession();
			session.setAttribute("ServersData", jsonData);
	      
//	        RequestDispatcher rd=request.getRequestDispatcher("MainPage.jsp");
//	        rd.forward(request,response);
			
			
		}catch(Exception e ) {
			System.out.println(e);
		}
	}

}
