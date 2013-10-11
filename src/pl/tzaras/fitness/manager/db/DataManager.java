package pl.tzaras.fitness.manager.db;



public class DataManager {

	private static DataManager instance;
	
	private GymClassTypeManager gymClassTypeManager;
	private GymTrainerManager gymInstructorManager;
	private GymRoomManager gymRoomManager;
	private GymClassManager gymClassManager;
	
	public static DataManager getInstance(){
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	private DataManager(){
		gymClassTypeManager = new GymClassTypeManager();
		gymInstructorManager = new GymTrainerManager();	
		gymRoomManager = new GymRoomManager();
		gymClassManager = new GymClassManager();
	}

	public GymClassTypeManager getGymClassTypeManager() {
		return gymClassTypeManager;
	}

	public GymTrainerManager getGymTrainerManager() {
		return gymInstructorManager;
	}

	public GymRoomManager getGymRoomManager() {
		return gymRoomManager;
	}

	public GymClassManager getGymClassManager() {
		return gymClassManager;
	}

		
}
