package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.tzaras.fitness.manager.TypeWrapper;
import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.util.HibernateUtil;

public class GymClassTypeManager {
	
	public GymClassTypeManager() {}

	public Long saveClass(String className)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			GymClassType gClass = new GymClassType();
			gClass.setName(className);
			courseId = (Long) session.save(gClass);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return courseId;
	}

	public List<GymClassType> getClassTypes()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ArrayList<GymClassType> coursesTypes = new ArrayList<GymClassType>();
		try {
			transaction = session.beginTransaction();
			
			coursesTypes.addAll(session.createQuery("from GymClassType").list());
			
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return coursesTypes;
	}

	public void updateClass(Long gClassId, String gClassName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClassType gClass = (GymClassType) session.get(GymClassType.class, gClassId);
			gClass.setName(gClassName);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteClass(Long gClassId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClassType gClass = (GymClassType) session.get(GymClassType.class, gClassId);
			session.delete(gClass);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void initializeCombo(ComboBox<TypeWrapper> classTypeCombo) {
		classTypeCombo.getItems().setAll(wrapClassTypes(DataManager.getInstance().getGymClassTypeManager().getClassTypes()));		
	}
	
	private List<TypeWrapper> wrapClassTypes(List<GymClassType> classTypes) {
		ArrayList<TypeWrapper> wrapped = new ArrayList<TypeWrapper>();
		
		for (GymClassType classType : classTypes) {
			wrapped.add(new TypeWrapper(classType));
		}
		return wrapped;
	}
	
}
