package ajbc.webservice.rest.iot_backend_services.tcp_server;

import ajbc.webservice.rest.iot_backend_services.db_services.DBService;
import ajbc.webservice.rest.iot_backend_services.models.IOTThing;
import ajbc.webservice.rest.iot_backend_services.models.Type;

public class ClientRunner {

	public static void main(String[] args) {
		
		IOTThing newThing = new DBService().createNewThing(Type.CONTROLLER, "model X", "manufacturer X");
		InventoryReport client = new InventoryReport(newThing);

		try {
			client.start();
			Thread.sleep(20000);
			client.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
