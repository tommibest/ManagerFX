package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.ComboBox;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.utils.HibernateUtil;

public class GymClassTypeManager {
	
	private List<GymClassType> classTypes;
	private List<TypeWrapper> wrappedTypes;
	
	public GymClassTypeManager() {
		classTypes = getTypesFromDB();
		wrappedTypes = wrapClassTypes(classTypes);
	}

	public Long addClassType(String className)
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
			
			classTypes.add(gClass);
			wrappedTypes.add(new TypeWrapper(gClass));
			
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return courseId;
	}

	private List<GymClassType> getTypesFromDB()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		LinkedList<GymClassType> coursesTypes = new LinkedList<GymClassType>();
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

	public void updateClassType(Long gClassId, String gClassName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClassType gClass = (GymClassType) session.get(GymClassType.class, gClassId);
			gClass.setName(gClassName);
			transaction.commit();
			for (Iterator<GymClassType> i = classTypes.iterator(); i.hasNext(); ) {
				GymClassType currType = i.next();
				if (currType.getClassId() == gClass.getClassId()){
					currType.setName(gClass.getName());
				}
			}
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteClassType(GymClassType selectedItem) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClassType classType = (GymClassType) session.get(GymClassType.class, selectedItem.getClassId());
			session.delete(classType);
			transaction.commit();
			
			for (Iterator<TypeWrapper> i = wrappedTypes.iterator(); i.hasNext(); ) {
				TypeWrapper currType = i.next();
				if (currType.getClassType().getClassId() == selectedItem.getClassId()){
					i.remove();
				}
			}
			
			for (Iterator<GymClassType> i = classTypes.iterator(); i.hasNext(); ) {
				GymClassType currType = i.next();
				if (currType.getClassId() == selectedItem.getClassId()){
					i.remove();
				}
			}
			
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
	
	public List<GymClassType> getClassTypes() {
		return classTypes;
	}

	private List<TypeWrapper> wrapClassTypes(List<GymClassType> classTypes) {
		ArrayList<TypeWrapper> wrapped = new ArrayList<TypeWrapper>();
		
		for (GymClassType classType : classTypes) {
			wrapped.add(new TypeWrapper(classType));
		}
		return wrapped;
	}

	
	
}
