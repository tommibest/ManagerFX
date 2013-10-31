package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.util.Callback;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.db.data.GymRoom;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.HibernateUtil;


public class GymClassManager {

	public GymClassManager() {
		
	}
	
	public List<GymClass> getClasses()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymClass> classes = new ArrayList<GymClass>();
		try {
			transaction = session.beginTransaction();
			
			classes.addAll( session.createQuery("from GymClass").list() );
			for (Iterator iter = classes.iterator(); iter.hasNext();) {
				GymClass gClass = (GymClass) iter.next();
				System.out.println("Trainer: "+gClass.getClassTrainer().getName() + ", " + gClass.getClassTrainer().getSurrname());
				System.out.println("Room: "+gClass.getClassRoom().getName());
				System.out.println("ClassType: "+gClass.getClassType().getName());
			}
				
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return classes;
	}

	public Long saveClass(GymRoom room, DateTime startTime, int participants,
			GymTrainer classTrainer, GymClassType classType, long duration) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			GymClass gymClass = new GymClass();
			gymClass.setClassRoom(room);
			gymClass.setStartTime(startTime);
			gymClass.setClassType(classType);
			gymClass.setClassTrainer(classTrainer);
			gymClass.setDuration(duration);
			gymClass.setParticipants(participants);
			courseId = (Long) session.save(gymClass);
			System.out.println("Course ID: " + courseId );
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return courseId;
		
	}

	public List<GymClass> getClasses(DateTime monday, DateTime sunday) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymClass> classes = new ArrayList<GymClass>();
		try {

			transaction = session.beginTransaction();
			classes.addAll( session.createQuery("from GymClass where startTime >= :from and startTime <= :to").setParameter("from", monday).setParameter("to", sunday).list() );
			for (Iterator iter = classes.iterator(); iter.hasNext();) {
				GymClass gClass = (GymClass) iter.next();
				System.out.println("Trainer: "+gClass.getClassTrainer().getName() + ", " + gClass.getClassTrainer().getSurrname());
				System.out.println("Room: "+gClass.getClassRoom().getName());
				System.out.println("ClassType: "+gClass.getClassType().getName());
			}
				
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return classes;
	}

	public void updateClass(GymClass gymClass) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClass gClass = (GymClass) session.get(GymClass.class, gymClass.getClassId());
			gClass.setClassRoom(gymClass.getClassRoom());
			gClass.setClassType(gymClass.getClassType());
			gClass.setClassTrainer(gymClass.getClassTrainer());
			gClass.setStartTime(gymClass.getStartTime());
			gClass.setDuration(gymClass.getDuration());
			gClass.setParticipants(gymClass.getParticipants());
			
			session.update(gClass);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteClass(GymClass gymClass)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClass gClass = (GymClass) session.get(GymClass.class, gymClass.getClassId());
			
			session.delete(gClass);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Long saveClass(GymClass gymClass) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			courseId = (Long) session.save(gymClass);
			System.out.println("Course ID: " + courseId );
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return courseId;
		
	}

	/*
	public void initializeCombo(ComboBox<RoomWrapper> classTypeCombo) {
		classTypeCombo.getItems().setAll(wrapRoom(getRooms()));		
	}
	
	private List<RoomWrapper> wrapRoom(List<GymRoom> rooms) {
		ArrayList<RoomWrapper> wrapped = new ArrayList<RoomWrapper>();
		
		for (GymRoom classType : rooms) {
			wrapped.add(new RoomWrapper(classType));
		}
		return wrapped;
	}*/
	
}
