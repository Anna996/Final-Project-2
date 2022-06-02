package ajbc.webservice.rest.iot_backend_services.tcp_server;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MultiThreadedServerRunner implements ServletContextListener {
	
	private InventoryServer server;
		
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		server = new InventoryServer();
		server.start();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		server.stopServer();
	}
}
