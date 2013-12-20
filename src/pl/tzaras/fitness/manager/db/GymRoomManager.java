package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.ComboBox;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import pl.tzaras.fitness.manager.db.data.GymRoom;
import pl.tzaras.fitness.manager.utils.HibernateUtil;
import pl.tzaras.fitness.manager.utils.ManagerUtils;

public class GymRoomManager {

	public class CustomComparator implements Comparator<RoomWrapper> {
	    public int compare(RoomWrapper o1, RoomWrapper o2) {
	        return o1.getName().compareTo(o2.getName());
	    }
	}
	
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

	public void updateRoom(Long gClassId, String roomName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymRoom gClass = (GymRoom) session.get(GymRoom.class, gClassId);
			gClass.setName(roomName);
			transaction.commit();
			
			for (Iterator<GymRoom> iter = rooms.iterator(); iter.hasNext(); ) {
				GymRoom room = iter.next();
				if (room.getID() == gClassId) {
					room.setName(roomName);
				}
			}
			wrappedRooms = wrapRoom(rooms);
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
		
		Collections.sort(wrapped, new CustomComparator());
		return wrapped;
	}

	public int deleteRoom(GymRoom selectedItem) {
		int retVal = ManagerUtils.SUCCESS;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymRoom room = (GymRoom) session.get(GymRoom.class, selectedItem.getID());
			session.delete(room);
			transaction.commit();
			
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
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			System.err.println(e.getConstraintName());
			retVal = ManagerUtils.CONSTRAINT_VIOLATTION;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			retVal = ManagerUtils.UNKNOWN_FAILURE;
		} finally {
			session.close();
		}
		
		return retVal;
	}

	public boolean roomExists(String roomName) {
		for (GymRoom room : rooms) {
			if (room.getName().compareTo(roomName) == 0) {
				return true;
			}
		}
		return false;
	}

	public void initializeComboWithDefault(ComboBox<RoomWrapper> comboBox) {
		initializeCombo(comboBox);
		comboBox.getItems().add(defaultSelection);
		comboBox.setValue(defaultSelection);
	}

}
