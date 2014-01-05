package pl.tzaras.fitness.manager;

import org.joda.time.DateTime;

import pl.tzaras.fitness.manager.db.data.GymClass;

public class AppState {

	private static AppState __instance;
	DateTime currentMonday;
	DateTime currentSunday;
	GymClass selectedClass;
	private boolean populate = false;
	private boolean initializing;
	
	private AppState() {
		
	}
	
	public static AppState getInstance() {
		if (__instance == null) {
			__instance = new AppState();
		}
		return __instance;
	}

	public DateTime getCurrentMonday() {
		return currentMonday;
	}

	public DateTime getCurrentSunday() {
		return currentSunday;
	}

	public void setCurrentSunday(DateTime currentSunday) {
		this.currentSunday = currentSunday;
	}

	public GymClass getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(GymClass selectedClass) {
		this.selectedClass = selectedClass;
	}

	public boolean isPopulate() {
		return populate;
	}

	public void setPopulate(boolean populate) {
		this.populate = populate;
	}

	public boolean isInitializing() {
		return initializing;
	}

	public void setInitializing(boolean initializing) {
		this.initializing = initializing;
	}

	public void setCurrentMonday(DateTime currentMonday) {
		this.currentMonday = currentMonday;
	}
	
}
