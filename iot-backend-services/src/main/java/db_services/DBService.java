package db_services;

import java.util.Map;
import java.util.UUID;

import database.DBMock;
import models.Device;
import models.IOTThing;

public class DBService {
	
	private DBMock database;
	private Map<UUID, IOTThing> things;
	private Map<UUID, Device> devices;

	public DBService() {
		database = DBMock.getInstance();
		things = database.getThings();
		devices = database.getDevices();
	}
	
	// TODO handle exception
	public void updateDB(IOTThing thing) throws Exception {
		
		if(things.get(thing.getID()) == null) {
			throw new Exception("");
		}
		
		things.replace(thing.getID(), thing);
	}
}
