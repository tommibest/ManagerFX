package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.db.data.GymClass;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class CallendarEntry extends Button {

	private GymClass gymClass;
	
	public CallendarEntry(GymClass gClass) {
		super(gClass.getClassType().getName()+"\n"+gClass.getClassTrainer().getSurrname());
		this.gymClass = gClass; 
		setPrefSize(87.0, gymClass.getDuration()/3);
		AnchorPane.setTopAnchor(this, gymClass.getStartTime().getHourOfDay()*20.0);
		AnchorPane.setLeftAnchor(this, gymClass.getStartTime().getDayOfWeek()*87.0);
	}

}
