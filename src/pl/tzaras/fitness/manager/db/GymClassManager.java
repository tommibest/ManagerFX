package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.db.data.GymRoom;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.HibernateUtil;


public class GymClassManager {

	public GymClassManager() {
		
	}
	
	public List<GymClass> getClasses(String condition)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymClass> classes = new ArrayList<GymClass>();
		try {
			transaction = session.beginTransaction();
			String query = "from GymClass";
			if (condition != null) {
				query  += " " + condition; 
			}
			classes.addAll( session.createQuery(query).list() );
			for (Iterator iter = classes.iterator(); iter.hasNext();) {
				GymClass gClass = (GymClass) iter.next();
				System.out.println("Trainer1: " + gClass.getClassTrainer1().getName() + ", " + gClass.getClassTrainer1().getSurrname());
				if (gClass.getClassTrainer2() != null ) {
					System.out.println("Trainer2: " + gClass.getClassTrainer2().getName() + ", " + gClass.getClassTrainer2().getSurrname());
				}
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
			GymTrainer classTrainer1, GymTrainer classTrainer2, GymClassType classType, long duration) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			GymClass gymClass = new GymClass();
			gymClass.setClassRoom(room);
			gymClass.setStartTime(startTime);
			gymClass.setClassType(classType);
			gymClass.setClassTrainer1(classTrainer1);
			if ( classTrainer2 != null) {
				gymClass.setClassTrainer2(classTrainer2);
			}
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
	
	private List<GymClass> getClasses(GymRoom classRoom) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymClass> classes = new ArrayList<GymClass>();
		try {

			transaction = session.beginTransaction();
			classes.addAll( session.createQuery("from GymClass where classRoom = :room").setParameter("room", classRoom).list() );
			for (Iterator iter = classes.iterator(); iter.hasNext();) {
				GymClass gClass = (GymClass) iter.next();
				System.out.println("Trainer1: " + gClass.getClassTrainer1().getName() + ", " + gClass.getClassTrainer1().getSurrname());
				if (gClass.getClassTrainer2() != null ) {
					System.out.println("Trainer2: " + gClass.getClassTrainer2().getName() + ", " + gClass.getClassTrainer2().getSurrname());
				}
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

	public List<GymClass> getClasses(DateTime monday, DateTime sunday) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymClass> classes = new ArrayList<GymClass>();
		try {

			transaction = session.beginTransaction();
			classes.addAll( session.createQuery("from GymClass where startTime >= :from and startTime <= :to").setParameter("from", monday).setParameter("to", sunday).list() );
			for (Iterator iter = classes.iterator(); iter.hasNext();) {
				GymClass gClass = (GymClass) iter.next();
				System.out.println("Trainer1: " + gClass.getClassTrainer1().getName() + ", " + gClass.getClassTrainer1().getSurrname());
				if (gClass.getClassTrainer2() != null ) {
					System.out.println("Trainer2: " + gClass.getClassTrainer2().getName() + ", " + gClass.getClassTrainer2().getSurrname());
				}
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
			gClass.setClassTrainer1(gymClass.getClassTrainer1());
			if (gymClass.getClassTrainer2() != null) {
				gClass.setClassTrainer2(gymClass.getClassTrainer2());
			}
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

	public GymClass makeCopy(GymClass gClass) {
		GymClass newClass = new GymClass();
		newClass.setClassRoom(gClass.getClassRoom());
		newClass.setClassTrainer1(gClass.getClassTrainer1());
		if (gClass.getClassTrainer2() != null) {
			newClass.setClassTrainer2(gClass.getClassTrainer2());
		}
		newClass.setClassType(gClass.getClassType());
		newClass.setDuration(gClass.getDuration());
		newClass.setParticipants(gClass.getParticipants());
		newClass.setStartTime(gClass.getStartTime());
		return newClass;
	}

	public boolean isRoomAvailableForClass(GymClass gClass) {
		List<GymClass> classes = getClasses(gClass.getClassRoom());
		
		boolean retVal = true;
		if (classes.size() != 0) {
			for (GymClass c : classes) {
				if ( gClass.getStartTime().isAfter(c.getStartTime().getMillis()) && gClass.getStartTime().isBefore(c.getStartTime().plusMinutes((int)c.getDuration()).getMillis()) ) {
					retVal = false;
					break;
				}
			}
		}
		return retVal;
	}

	
	
}
