package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymTrainer;

public class TrainerWrapper {
	GymTrainer trainer;
	
	public TrainerWrapper(GymTrainer trainer){
		this.trainer = trainer;
	}
	
	public GymTrainer getGymTrainer() {
		return trainer;
	}
	
	public String toString(){
		return trainer.getName() + ", " + trainer.getSurrname();
	}

}
