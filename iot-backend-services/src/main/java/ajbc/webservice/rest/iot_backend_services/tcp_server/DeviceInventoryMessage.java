package ajbc.webservice.rest.iot_backend_services.tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;
import ajbc.webservice.rest.iot_backend_services.db_services.DBService;
import ajbc.webservice.rest.iot_backend_services.models.Device;
import ajbc.webservice.rest.iot_backend_services.models.IOTThing;

public class DeviceInventoryMessage implements Runnable {

	private Socket socket;
	private DBService dbService;
	private static Object obj = new Object();

	public DeviceInventoryMessage(Socket socket) {
		this.socket = socket;
		dbService = new DBService();
	}

	@Override
	public void run() {

		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			String jsonIotThing = buffer.readLine();
			Gson gson = new Gson();
			IOTThing thing = gson.fromJson(jsonIotThing, IOTThing.class);

			synchronized (obj) {
				System.out.println("--------------------------------------");
				System.out.println("Thread accepted requset from client " + thing.getID());
				System.out.println("Proccessing requset......");
				updateDB(thing);
				printThings("After update:");
				System.out.println("--------------------------------------");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateDB(IOTThing thing) {
		try {
			dbService.updateDB(thing);
		} catch (Exception e) {

		}
	}

	private void printThings(String title) {
		System.out.println(title);

		List<IOTThing> things = dbService.getAllThings();
		for (IOTThing thing : things) {
			System.out.println(thing);
		}
		List<Device> devices = dbService.getAllDevices();
		for (Device device : devices) {
			System.out.println(device);
		}
	}
}
