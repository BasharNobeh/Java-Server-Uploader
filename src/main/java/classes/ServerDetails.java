package classes;

public class ServerDetails {
    public int serverID;
    public int start_deploying;
    public String tomcat_path;
    public boolean status;
    public int nginx_id;
    public boolean ngin_server;
    public String nginx_path;
    public String nginx_config_path;
    public String nginx_down_query;
    public String nginx_process_name;
    public  boolean nginx_reload_server;
    public int ngin_min;
    public int nginx_max;
	public ServerDetails(int serverID,int start_deploying, String tomcat_path, boolean status, int nginx_id,
			boolean ngin_server, String nginx_path, String nginx_config_path, String nginx_down_query,
			String nginx_process_name, boolean nginx_reload_server, int ngin_min, int nginx_max) {
		
		this.start_deploying = start_deploying;
		this.tomcat_path = tomcat_path;
		this.status = status;
		this.nginx_id = nginx_id;
		this.serverID = serverID;
		this.ngin_server = ngin_server;
		this.nginx_path = nginx_path;
		this.nginx_config_path = nginx_config_path;
		this.nginx_down_query = nginx_down_query;
		this.nginx_process_name = nginx_process_name;
		this.nginx_reload_server = nginx_reload_server;
		this.ngin_min = ngin_min;
		this.nginx_max = nginx_max;
	}

    
	
   

}
