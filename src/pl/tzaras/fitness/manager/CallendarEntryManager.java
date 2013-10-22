package pl.tzaras.fitness.manager;

import java.util.HashMap;

import pl.tzaras.fitness.manager.db.data.GymClass;

public class CallendarEntryManager {

	private static CallendarEntryManager instance;
	private HashMap<Long,CallendarEntry> entries;
	
	public static CallendarEntryManager getInstance() {
		if (instance == null)
			instance = new CallendarEntryManager();
		return instance;
	}
	
	private CallendarEntryManager() {
		entries = new HashMap<Long, CallendarEntry>();
	}
	
	public CallendarEntry getEntry(GymClass gClass) {
		if ( entries.containsKey(gClass.getClassId()) ) {
			return entries.get(gClass.getClassId());
		} else {
			CallendarEntry newCE =  new CallendarEntry(gClass);
			entries.put(gClass.getClassId(), newCE);
			return newCE;
		}
	}

	public void remove(GymClass selectedItem) {
		entries.remove(selectedItem.getClassId());
	}

}
