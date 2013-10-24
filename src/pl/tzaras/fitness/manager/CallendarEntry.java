package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.utils.ManagerUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CallendarEntry extends Button {

	private GymClass gymClass;
	
	public CallendarEntry(GymClass gClass, final ManagerController mController) {
		super(gClass.getClassType().getName()+"\n"+gClass.getClassTrainer().getSurrname());
		double prefHeight = mController.getCallendarPane().getPrefHeight()/(ManagerUtils.endHour-ManagerUtils.startHour);
		this.gymClass = gClass; 
		setPrefSize(87.0, gymClass.getDuration()/3);
		AnchorPane.setTopAnchor(this, gymClass.getStartTime().getHourOfDay()*prefHeight);
		AnchorPane.setLeftAnchor(this, gymClass.getStartTime().getDayOfWeek()*87.0);
		super.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		        mController.displayOverview(gymClass);
		    }
		});
	}

}
