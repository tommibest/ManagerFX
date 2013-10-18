package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
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
	
	public GymRoomManager() {}

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
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return courseId;
	}

	public List<GymRoom> getRooms()
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
	}

	public void deleteClass(Long roomId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymRoom room = (GymRoom) session.get(GymRoom.class, roomId);
			session.delete(room);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void initializeCombo(ComboBox<RoomWrapper> classTypeCombo) {
		classTypeCombo.getItems().setAll(wrapRoom(getRooms()));		
	}
	
	private List<RoomWrapper> wrapRoom(List<GymRoom> rooms) {
		ArrayList<RoomWrapper> wrapped = new ArrayList<RoomWrapper>();
		
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
	}

}
