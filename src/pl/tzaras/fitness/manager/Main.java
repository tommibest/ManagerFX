package pl.tzaras.fitness.manager;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


/*import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.tzaras.fitness.manager.db.DatabaseEngine;
import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		DatabaseEngine dbEngine = new DatabaseEngine();
		dbEngine.start();
		Main obj = new Main();
		Long classId1 = obj.saveClass("Physics");
		Long classId2 = obj.saveClass("Chemistry");
		Long classId3 = obj.saveClass("Momo");
		obj.listClasses();
		obj.updateClass(classId3, "Mathematics");
		//obj.deleteClass(classId2);
		obj.listClasses();
		
		dbEngine.stop();
	}

	public Long saveClass(String className)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			GymClass gClass = new GymClass();
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

	public void listClasses()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<GymClass> courses = session.createQuery("from GymClass").list();
			for (Iterator<GymClass> iterator = courses.iterator(); iterator.hasNext();)
			{
				GymClass gClass = iterator.next();
				System.out.println(gClass.getName());
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateClass(Long gClassId, String gClassName)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			GymClass gClass = (GymClass) session.get(GymClass.class, gClassId);
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
			GymClass gClass = (GymClass) session.get(GymClass.class, gClassId);
			session.delete(gClass);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}*/