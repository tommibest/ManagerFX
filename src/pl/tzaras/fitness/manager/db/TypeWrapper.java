package pl.tzaras.fitness.manager.db;

import pl.tzaras.fitness.manager.db.data.GymClassType;

public class TypeWrapper {
	GymClassType classType;
	
	public TypeWrapper(GymClassType classType) {
		this.classType = classType; 
	}
	
	public String toString() {
		if (classType != null) {
			return classType.getName();
		} else {
			return null;
		}
	}

	public GymClassType getClassType() {
		return classType;
	}

}
