package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.utils.ManagerUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CallendarEntry extends Button {

	private GymClass gymClass;
	private ManagerController mController;
	
	public CallendarEntry(GymClass gClass, final ManagerController mController) {
		super(gClass.getClassType().getName()+"\n"+gClass.getClassTrainer().getSurrname());
		this.mController = mController;
		double hourHeight = (mController.getCallendarPane().getPrefHeight() - GUIHelper.getHeaderHeight()) / (ManagerUtils.endHour-ManagerUtils.startHour);
		this.gymClass = gClass; 
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
		setText(gymClass.getClassType().getName()+"\n"+gymClass.getClassTrainer().getSurrname());
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
