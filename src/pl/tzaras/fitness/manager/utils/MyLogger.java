package pl.tzaras.fitness.manager.utils;

public class MyLogger {
	
	private static MyLogger instance;
	
	public static MyLogger getInstance() {
		if (instance == null) {
			instance = new MyLogger();
		}
		return instance;
	}
	
	public void logEntry(String msg) {
		System.out.println("ENTER: "+msg);
	}

	public void logEnd(String msg) {
		System.out.println("END: "+msg);
	}
	
	public void logInfo(String infoMsg){
		System.out.println("INFO: "+infoMsg);
	}
	
	public void logWarnig(String warningMsg){
		System.out.println("WARNING: "+warningMsg);
	}
	
	public void logError(String errorMsg){
		System.err.println("ERROR: "+errorMsg);
	}
}
