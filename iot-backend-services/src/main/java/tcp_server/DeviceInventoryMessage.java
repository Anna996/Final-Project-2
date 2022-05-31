package tcp_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.google.gson.Gson;

import db_services.DBService;
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
			System.out.println("Client " + socket.getInetAddress().getHostName() + " accepted");
			String jsonIotThing = buffer.readLine();
			Gson gson = new Gson();
			IOTThing thing = gson.fromJson(jsonIotThing, IOTThing.class);
			updateDB(thing);
			System.out.println(thing);

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
}
