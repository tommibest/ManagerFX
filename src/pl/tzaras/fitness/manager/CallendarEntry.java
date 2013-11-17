package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.ManagerUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CallendarEntry extends Button {

	private GymClass gymClass;
	private ManagerController mController;
	
	public CallendarEntry(GymClass gClass, final ManagerController mController) {
		super();
		this.mController = mController;
		double hourHeight = (mController.getCallendarPane().getPrefHeight() - GUIHelper.getHeaderHeight()) / (ManagerUtils.endHour-ManagerUtils.startHour);
		this.gymClass = gClass;
		StringBuilder trainers = new StringBuilder();
		trainers.append(gymClass.getClassTrainer1().getSurrname()).append("; ");
		if ( gymClass.getClassTrainer2() != null ) {
			trainers.append(gymClass.getClassTrainer2().getSurrname()).append("; ");
		}
		super.setText(gClass.getClassType().getName()+"\n"+trainers.toString());
		setPrefSize(87.0, gymClass.getDuration()*(hourHeight/60));
		double posY = GUIHelper.getHeaderHeight() + ((gymClass.getStartTime().getHourOfDay()-ManagerUtils.startHour) + (gymClass.getStartTime().getMinuteOfHour()/60.0))*hourHeight;
		AnchorPane.setTopAnchor(this, posY);
		AnchorPane.setLeftAnchor(this, gymClass.getStartTime().getDayOfWeek()*87.0);
		super.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        mController.displayOverview(gymClass);
		    }
		});
	}
	
	public void update(){
		StringBuilder trainers = new StringBuilder();
		trainers.append(gymClass.getClassTrainer1().getSurrname()).append("; ");
		if ( gymClass.getClassTrainer2() != null ) {
			trainers.append(gymClass.getClassTrainer2().getSurrname()).append("; ");
		}
		setText(gymClass.getClassType().getName()+"\n"+trainers.toString());
		double hourHeight = mController.getCallendarPane().getPrefHeight() / (ManagerUtils.endHour-ManagerUtils.startHour);
		setPrefSize(87.0, gymClass.getDuration()*(hourHeight/60));
		double posY = GUIHelper.getHeaderHeight() + ((gymClass.getStartTime().getHourOfDay()-ManagerUtils.startHour) + (gymClass.getStartTime().getMinuteOfHour()/60.0))*hourHeight;
		AnchorPane.setTopAnchor(this, posY);
		System.out.println("left anchor: "+  gymClass.getStartTime().getDayOfWeek()*87.0);
		AnchorPane.setLeftAnchor(this, gymClass.getStartTime().getDayOfWeek()*87.0);
		
		super.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        mController.displayOverview(gymClass);
		    }
		});
	}

}
