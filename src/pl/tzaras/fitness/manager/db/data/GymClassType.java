package pl.tzaras.fitness.manager.db.data;

// Generated Oct 13, 2013 8:20:36 PM by Hibernate Tools 3.4.0.CR1

/**
 * GymClassType generated by hbm2java
 */
public class GymClassType implements java.io.Serializable {

	private long classId;
	private String name;

	public GymClassType() {
	}

	public GymClassType(String name) {
		this.name = name;
	}

	public long getClassId() {
		return this.classId;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}