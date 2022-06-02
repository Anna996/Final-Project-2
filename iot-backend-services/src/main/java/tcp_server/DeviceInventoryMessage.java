package tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;

import db_services.DBService;
import models.Device;
import models.IOTThing;

public class DeviceInventoryMessage extends Thread {

	private Socket socket;
	private DBService dbService;

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

			synchronized (dbService.getAllThings()) {
//				printThings("Before");
				System.out.println("--------------------------------------");
				System.out.println("Thread accepted requset from client " + thing.getID());
				System.out.println("Proccessing requset......");
				updateDB(thing);
				printThings("After update:");
				System.out.println("--------------------------------------");
			}
//			System.out.println("Recieved: " + thing);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO handle exception !
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
