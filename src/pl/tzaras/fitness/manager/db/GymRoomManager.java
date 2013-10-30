package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.ComboBox;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.tzaras.fitness.manager.RoomWrapper;
import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.db.data.GymRoom;
import pl.tzaras.fitness.manager.utils.HibernateUtil;

public class GymRoomManager {
	
	public static final String DEFAULT = "Dowolna";
	
	private List<GymRoom> rooms;
	private List<RoomWrapper> wrappedRooms;
	public RoomWrapper defaultSelection = new RoomWrapper(DEFAULT);
	
	public GymRoomManager() {
		rooms = retrieveRooms();
		wrappedRooms = wrapRoom(rooms);
	}

	public Long saveRoom(String roomName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			GymRoom room = new GymRoom();
			room.setName(roomName);
			courseId = (Long) session.save(room);
			transaction.commit();
			rooms.add(room);
			wrappedRooms.add(new RoomWrapper(room));
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return courseId;
	}

	private List<GymRoom> retrieveRooms()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymRoom> rooms = new ArrayList<GymRoom>();
		try {
			transaction = session.beginTransaction();
			
			rooms.addAll(session.createQuery("from GymRoom").list());
			
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rooms;
	}

	public void updateClass(Long gClassId, String roomName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymRoom gClass = (GymRoom) session.get(GymRoom.class, gClassId);
			gClass.setName(roomName);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (Iterator<GymRoom> iter = rooms.iterator(); iter.hasNext(); ) {
			GymRoom room = iter.next();
			if (room.getID() == gClassId) {
				room.setName(roomName);
			}
		}
	}

	public void initializeCombo(ComboBox<RoomWrapper> classTypeCombo) {
		classTypeCombo.getItems().setAll(wrappedRooms);		
	}
	
	public List<GymRoom> getRooms() {
		return rooms;
	}

	private List<RoomWrapper> wrapRoom(List<GymRoom> rooms) {
		LinkedList<RoomWrapper> wrapped = new LinkedList<RoomWrapper>();
		
		for (GymRoom classType : rooms) {
			wrapped.add(new RoomWrapper(classType));
		}
		return wrapped;
	}

	public void delete(GymRoom selectedItem) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymRoom room = (GymRoom) session.get(GymRoom.class, selectedItem.getID());
			session.delete(room);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (Iterator<GymRoom> iter = rooms.iterator(); iter.hasNext(); ) {
			if (iter.next().getID() == selectedItem.getID()) {
				iter.remove();
				break;
			}
		}
		for (Iterator<RoomWrapper> iter = wrappedRooms.iterator(); iter.hasNext(); ) {
			RoomWrapper wrapped = iter.next();
			if ( wrapped.getRoom() != null ) {
				if (iter.next().getRoom().getID() == selectedItem.getID()) {
					iter.remove();
					break;
				}
			}
		}
	}

}
