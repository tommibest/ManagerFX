package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ComboBox;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.tzaras.fitness.manager.TrainerWrapper;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.HibernateUtil;

public class GymTrainerManager {

	public GymTrainerManager() {}
	
	public Long saveTrainer(String name, String surrname)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long instructorId = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = new GymTrainer();
			trainer.setName(name);
			trainer.setSurrname(surrname);
			instructorId = (Long) session.save(trainer);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return instructorId;
	}

	public List<GymTrainer> getInstructors()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<GymTrainer> trainers = new ArrayList<GymTrainer>();
		try {
			transaction = session.beginTransaction();
			trainers.addAll(session.createQuery("from GymTrainer").list());
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return trainers;
	}

	public void updateTrainer(Long trainerId, String name, String surrname)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = (GymTrainer) session.get(GymTrainer.class, trainerId);
			if (name != null && !name.isEmpty() ) trainer.setName(name);
			if (surrname != null && !surrname.isEmpty() ) trainer.setSurrname(surrname);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteInstructor(Long trainerId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = (GymTrainer) session.get(GymTrainer.class, trainerId);
			session.delete(trainer);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void initializeCombo(ComboBox<TrainerWrapper> instructorCombo) {
		instructorCombo.getItems().setAll(wrapTrainers(DataManager.getInstance().getGymTrainerManager().getInstructors()));	
	}
	
	private List<TrainerWrapper> wrapTrainers(List<GymTrainer> trainers) {
		ArrayList<TrainerWrapper> newList = new ArrayList<TrainerWrapper>();
		
		for (GymTrainer trainer : trainers){
			newList.add(new TrainerWrapper(trainer));
		}
		
		return newList;
	}

	public void delete(GymTrainer selectedItem) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = (GymTrainer) session.get(GymTrainer.class, selectedItem.getTrainerId());
			session.delete(trainer);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
}
