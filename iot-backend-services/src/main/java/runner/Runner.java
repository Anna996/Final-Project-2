package runner;

import database.DBMock;
import models.IOTThing;

public class Runner {
	static IOTThing thing;

	public static void main(String[] args) throws InterruptedException {
		DBMock.getInstance().getThings().forEach((id, val) -> thing = val);
		thing.start();
	}
}
