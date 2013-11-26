package pl.tzaras.fitness.manager.db;

import pl.tzaras.fitness.manager.db.data.GymTrainer;

public class TrainerWrapper {
	
	String name = null;
	GymTrainer trainer;
	
	public TrainerWrapper(GymTrainer trainer){
		this.trainer = trainer;
		this.name = trainer.getName() + ", " + trainer.getSurrname();
	}
	
	public TrainerWrapper(String name) {
		this.name = name;
	}

	public GymTrainer getGymTrainer() {
		return trainer;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}

}
