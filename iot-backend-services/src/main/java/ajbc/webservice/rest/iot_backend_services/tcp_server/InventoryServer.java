package ajbc.webservice.rest.iot_backend_services.tcp_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;

public class InventoryServer extends Thread{
	private final int PORT = 8090;
	private boolean isRunning = true;
	private final int POOL_SIZE = 6;
	private final int SECONDS = 5;
	
	
	@Override
	public void run() {
		ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server connected");
			while (isRunning) {
				Socket socket = serverSocket.accept();
				executorService.execute(new DeviceInventoryMessage(socket));
			}
		} catch (IOException e) {
			System.err.println("Failed to start server on port " + PORT);
			e.printStackTrace();
		} finally {
			try {
				executorService.shutdown();
				executorService.awaitTermination(SECONDS, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopServer() {
		isRunning = false;
	}
}
