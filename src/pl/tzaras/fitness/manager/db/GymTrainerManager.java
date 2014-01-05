package pl.tzaras.fitness.manager.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.ComboBox;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import pl.tzaras.fitness.manager.CallendarEntryManager;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.HibernateUtil;
import pl.tzaras.fitness.manager.utils.ManagerUtils;

public class GymTrainerManager {

	public class CustomComparator implements Comparator<TrainerWrapper> {
	    public int compare(TrainerWrapper o1, TrainerWrapper o2) {
	        return o1.getGymTrainer().getSurrname().compareTo(o2.getGymTrainer().getSurrname());
	    }
	}

	private static final String DEFAULT = "Dowolny";
	
	private List<GymTrainer> trainers;
	private List<TrainerWrapper> wrappedTrainers;
	public TrainerWrapper defaultSelection = new TrainerWrapper(DEFAULT);
	
	public GymTrainerManager() {
		trainers = retrieveTrainers();
		wrappedTrainers = wrapTrainers(trainers);
	}
	
	public Long saveTrainer(String name, String surrname, double rateOfPay)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long instructorId = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = new GymTrainer();
			trainer.setName(name);
			trainer.setSurrname(surrname);
			trainer.setRateOfPay(rateOfPay);
			instructorId = (Long) session.save(trainer);
			transaction.commit();
			trainers.add(trainer);
			wrappedTrainers.add(new TrainerWrapper(trainer));
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return instructorId;
	}

	public List<GymTrainer> retrieveTrainers()
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

	public void updateTrainer(Long trainerId, String name, String surrname, double d)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = (GymTrainer) session.get(GymTrainer.class, trainerId);
			if (name != null && !name.isEmpty() ) trainer.setName(name);
			if (surrname != null && !surrname.isEmpty() ) trainer.setSurrname(surrname);
			trainer.setRateOfPay(d);
			transaction.commit();
			
			for(Iterator<GymTrainer> i = trainers.iterator(); i.hasNext(); ){
				GymTrainer curr = i.next();
				if (curr.getTrainerId() == trainerId) {
					curr.setName(name);
					curr.setSurrname(surrname);
					break;
				}
			}
			CallendarEntryManager.getInstance().refreshEntries();
			wrappedTrainers = wrapTrainers(trainers);
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void removeTrainer(Long trainerId)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = (GymTrainer) session.get(GymTrainer.class, trainerId);
			session.delete(trainer);
			transaction.commit();
			for (Iterator<TrainerWrapper> i = wrappedTrainers.iterator(); i.hasNext(); ){
				if (i.next().getGymTrainer().getTrainerId() == trainerId) {
					i.remove();
					break;
				}
			}
			for (Iterator<GymTrainer> i = trainers.iterator(); i.hasNext(); ){
				if (i.next().getTrainerId() == trainerId) {
					i.remove();
					break;
				}
			}
			
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void initializeCombo(ComboBox<TrainerWrapper> instructorCombo) {
		instructorCombo.getItems().clear();	
		instructorCombo.getItems().setAll(wrappedTrainers);	
	}
	
	public List<GymTrainer> getTrainers() {
		return trainers;
	}

	private List<TrainerWrapper> wrapTrainers(List<GymTrainer> trainers) {
		ArrayList<TrainerWrapper> newList = new ArrayList<TrainerWrapper>();
		for (GymTrainer trainer : trainers){
			newList.add(new TrainerWrapper(trainer));
		}
		Collections.sort(newList, new CustomComparator());
		
		return newList;
	}

	public int delete(GymTrainer selectedItem) {
		int retVal = ManagerUtils.SUCCESS;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymTrainer trainer = (GymTrainer) session.get(GymTrainer.class, selectedItem.getTrainerId());
			session.delete(trainer);
			transaction.commit();
			for (Iterator<TrainerWrapper> i = wrappedTrainers.iterator(); i.hasNext(); ){
				if (i.next().getGymTrainer().getTrainerId() == selectedItem.getTrainerId()) {
					i.remove();
					break;
				}
			}
			for (Iterator<GymTrainer> i = trainers.iterator(); i.hasNext(); ){
				if (i.next().getTrainerId() == selectedItem.getTrainerId()) {
					i.remove();
					break;
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

	public boolean trainerExists(String name, String surrname) {
		for (GymTrainer trainer : trainers){
			if (trainer.getName().compareTo(name) == 0 && trainer.getSurrname().compareTo(surrname) == 0) {
				return true;
			}
		}
		return false;
	}

	public void initializeComboWithDefault(ComboBox<TrainerWrapper> comboBox) {
		initializeCombo(comboBox);
		comboBox.getItems().add(defaultSelection);
		comboBox.setValue(defaultSelection);
//		comboBox.getSelectionModel().select(defaultSelection);
	}

}
