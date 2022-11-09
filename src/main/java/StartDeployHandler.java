

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetServersNames
 */
@SuppressWarnings("serial")
@WebServlet("/StartDeployHandler")
public class StartDeployHandler extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isDeployingRunChecker = "SELECT isDeployingRun FROM dbo.Deployment";
		String UpdateValues = "UPDATE dbo.Deployment\r\n"
				+ "SET \r\n"
				+ "    isDeployingRun = ?,\r\n"
				+ "    startDeployValue = ?";
		
		String UpdateNginxServer = "UPDATE dbo.nginx \r\n"
				+ "SET nginx_server = 1\r\n"
				+ "WHERE FK_serverID = ?";
		
		
		
		
		
		String[] ServersID = request.getParameter("NS_Array").split(",");
		
		
		
		
		
		
		
		
		
		PreparedStatement st;
		try {
			
			st = DataBaseConnection.DataBaseConnect().prepareStatement(isDeployingRunChecker);
			ResultSet rs = st.executeQuery();
			 PrintWriter out = response.getWriter();
		       
		        
			
			if(rs.next()) {
				PreparedStatement st2 =  DataBaseConnection.DataBaseConnect().prepareStatement(UpdateValues);
				
				if(rs.getInt(1) == 0) {
					
					st2.setInt(1, 1);
					st2.setInt(2, Integer.parseInt(request.getParameter("value")));
					out.println("Updating !");
					for(String ID : ServersID) {
						PreparedStatement stNginxServer =  DataBaseConnection.DataBaseConnect().prepareStatement(UpdateNginxServer);
						stNginxServer.setString(1, ID);
						stNginxServer.executeUpdate();
						
					}
					
					st2.executeUpdate();
					
					
					
				}else if (rs.getInt(1) == 1) {
					
					out.println("Seems like someone Else is uploading ! Try again later ");
					
					
				}
				
				
				
					
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}

}
