

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StatusTracker
 */
@WebServlet("/StatusTracker")
public class StatusTracker extends HttpServlet {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session2 = request.getSession();
		
		response.setContentType("text/plain");
	    PrintWriter pwriter=response.getWriter();
	    System.out.println("Logs are Ready ");
	    
	     
	    	pwriter.write(session2.getAttribute("data").toString());
	    	
	    	
	    	
	    
	    
	    

	    
		
		
		
		
	}
	
	

}
