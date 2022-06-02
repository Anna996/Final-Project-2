package ajbc.webservice.rest.iot_backend_services.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;
import ajbc.webservice.rest.iot_backend_services.db_services.DBService;
import ajbc.webservice.rest.iot_backend_services.models.IOTThing;
import ajbc.webservice.rest.iot_backend_services.tcp_server.InventoryReport;
import ajbc.webservice.rest.iot_backend_services.tcp_server.InventoryServer;

public class Runner {
	static IOTThing thing;
	private static final int POOL_SIZE = 2;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
		InventoryServer server = new InventoryServer();
		
		// TODO fix problem....
		executorService.execute(()-> server.run());
		executorService.execute(()-> runOne());
	}
	
	
	public static void runAll() throws InterruptedException {
		List<InventoryReport> clients = new ArrayList<InventoryReport>();

		new DBService().getAllThings().forEach(thing -> {
			clients.add(new InventoryReport(thing));
		});

		clients.forEach(client -> client.start());
		Thread.sleep(30000);

		clients.forEach(client -> {
			try {
				client.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public static void runOne()  {
		new DBService().getAllThings().forEach(val -> {
			thing = val;
		});
		InventoryReport client = new InventoryReport(thing);
		client.start();
		try {
			Thread.sleep(20000);
			client.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
