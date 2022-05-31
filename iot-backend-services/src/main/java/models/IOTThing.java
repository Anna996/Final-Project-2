package models;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class IOTThing extends Hardware {

	private List<Device> devices;
	private boolean isRunning = true;
	private final int M_SECONDS = 20000;
	private final String SERVER_NAME = "localhost";
	private final int SERVER_PORT = 8090;
	private Gson gson = new Gson();

	public IOTThing() {
		super();
		devices = new ArrayList<Device>();
	}

	public IOTThing(Type type, String model, String manufacturer) {
		super(type, model, manufacturer);
		devices = new ArrayList<Device>();
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public void addDevice(Device device) {
		devices.add(device);
	}

	public void start() {
		while (isRunning) {
			createReport();
			transmitReport();
			sleep();
		}
	}

	private void createReport() {
		devices.forEach(Device::simulateReading);

	}

	private void transmitReport() {
		try (Socket socket = new Socket(SERVER_NAME, SERVER_PORT);
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

			writer.write(gson.toJson(this));
			System.out.println("Report sent");

		} catch (IOException e) {
			System.err.println("Socket failed");
			e.printStackTrace();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(M_SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		isRunning = false;
	}

	@Override
	public String toString() {
		return "IOTThing " + super.toString() + "[devices=" + devices + ", isRunning=" + isRunning + ", M_SECONDS="
				+ M_SECONDS + "]";
	}
}
