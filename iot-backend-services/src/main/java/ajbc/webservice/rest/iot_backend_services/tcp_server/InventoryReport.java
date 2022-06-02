package ajbc.webservice.rest.iot_backend_services.tcp_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import ajbc.webservice.rest.iot_backend_services.models.IOTThing;

public class InventoryReport {
	private IOTThing thing;
	private Gson gson;
	private ScheduledExecutorService service;
	private final int SECONDS = 2;
	private final String SERVER_NAME = "localhost";
	private final int SERVER_PORT = 8090;
	private final int POOL_SIZE = 1;

	public InventoryReport(IOTThing thing) {
		this.thing = thing;
		gson = new Gson();
	}

	public void start(){
		service = Executors.newScheduledThreadPool(POOL_SIZE);

		System.out.println("[ Client Started running ]");
		
		service.scheduleAtFixedRate(() -> {
			createReport();
			transmitReport();
		}, SECONDS, SECONDS, TimeUnit.SECONDS);
	}

	private void createReport() {
		thing.simulateInventoryChange();
	}

	private void transmitReport() {
		try (Socket socket = new Socket(SERVER_NAME, SERVER_PORT);
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

			writer.write(gson.toJson(thing));
			System.out.println("[CLIENT]: Report sent by Client " + thing.getID());

		} catch (IOException e) {
			System.err.println("Socket failed");
			e.printStackTrace();
		}
	}
	
	public void stop() throws InterruptedException  {
		System.out.println("[CLIENT " + thing.getID() + " DISCONNECTED]");
		service.shutdown();
		service.awaitTermination(SECONDS, TimeUnit.SECONDS);
	}
}
