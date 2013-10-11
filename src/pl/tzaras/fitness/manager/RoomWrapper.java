package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymRoom;

public class RoomWrapper {
	GymRoom room;

	public RoomWrapper(GymRoom room) {
		this.room = room;
	}

	public String toString() {
		return room.getName();
	}

	public GymRoom getRoom() {
		return room;
	}
}
