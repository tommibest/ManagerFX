package pl.tzaras.fitness.manager.db.data;

// Generated Oct 13, 2013 8:20:36 PM by Hibernate Tools 3.4.0.CR1

/**
 * GymTrainer generated by hbm2java
 */
public class GymTrainer implements java.io.Serializable {

	private long trainerId;
	private String name;
	private String surrname;

	public GymTrainer() {
	}

	public GymTrainer(String name, String surrname) {
		this.name = name;
		this.surrname = surrname;
	}

	public long getTrainerId() {
		return this.trainerId;
	}

	public void setTrainerId(long trainerId) {
		this.trainerId = trainerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurrname() {
		return this.surrname;
	}

	public void setSurrname(String surrname) {
		this.surrname = surrname;
	}

}
