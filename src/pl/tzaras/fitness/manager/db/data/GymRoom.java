package pl.tzaras.fitness.manager.db.data;

// Generated Nov 17, 2013 7:59:56 PM by Hibernate Tools 4.0.0

/**
 * GymRoom generated by hbm2java
 */
public class GymRoom implements java.io.Serializable {

	private long ID;
	private String name;

	public GymRoom() {
	}

	public GymRoom(String name) {
		this.name = name;
	}

	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
