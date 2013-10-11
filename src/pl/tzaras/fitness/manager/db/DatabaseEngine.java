package pl.tzaras.fitness.manager.db;

import org.hsqldb.Server;

public class DatabaseEngine {

	Server hsqlServer = null;
	String dbName = "gym_db";
	String dbPath = "file:resources/data/gym_db";
	
	public void start() {
        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, dbName);
		hsqlServer.setDatabasePath(0, dbPath);
        
        hsqlServer.start();		
	}

	public void stop() {
		hsqlServer.stop();
	}
}
