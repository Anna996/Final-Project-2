package tcp_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import database.DBMock;

public class InventoryServer {
	private final int PORT = 8090;
	private boolean isRunning = true;
	private final int POOL_SIZE = 6;

	public void run()  {
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
		}finally {
			executorService.shutdown();
		}
	}

	public void stop() {
		isRunning = false;
	}
}
