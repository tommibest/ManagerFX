package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymRoom;

public class RoomWrapper {
	GymRoom room;
	private String roomName;

	public RoomWrapper(GymRoom room) {
		this.room = room;
	}
	
	public RoomWrapper(String roomName) {
		this.roomName = roomName;
	}

	public String toString() {
		if (room != null) {
			return room.getName();
		} else {
			return roomName;
		}
	}

	public GymRoom getRoom() {
		return room;
	}
}
