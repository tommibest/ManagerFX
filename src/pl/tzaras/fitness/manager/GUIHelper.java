package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.utils.ManagerUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GUIHelper {

	public static void prepareHoursColumn(AnchorPane callendarPane) {
		double prefHeight = callendarPane.getPrefHeight()/(ManagerUtils.endHour-ManagerUtils.startHour);
		for (int i=ManagerUtils.startHour;i<ManagerUtils.endHour; i++) {
			Label newLbl = new Label();
			if (i<10) newLbl.setText("0"+i+":00");
			else newLbl.setText(String.valueOf(i)+":00");
			newLbl.setPrefSize(87.0, prefHeight);
			newLbl.setAlignment(Pos.CENTER);
			newLbl.getStyleClass().add("myborder");
			AnchorPane.setTopAnchor(newLbl, (i-ManagerUtils.startHour+1)*prefHeight);
			AnchorPane.setLeftAnchor(newLbl, 0.0);
			callendarPane.getChildren().add(newLbl);
		}
	}

	public static void prepareDayOfWeekHeaders(AnchorPane callendarPane) {
		Label [] weekLabel = new Label [] {
				new Label(ManagerUtils.MONDAY_pl), 
				new Label(ManagerUtils.TUESDAY_pl),
				new Label(ManagerUtils.WEDNESDAY_pl),
				new Label(ManagerUtils.THURSDAY_pl),
				new Label(ManagerUtils.FRIDAY_pl),
				new Label(ManagerUtils.SATURDAY_pl),
				new Label(ManagerUtils.SUNDAY_pl)
				};
		
		Double currentXPos = 88.0;
		for (Label lbl : weekLabel) {
			lbl.setPrefSize(87.0, 20.0);
			AnchorPane.setTopAnchor(lbl, 5.0);
			AnchorPane.setLeftAnchor(lbl, currentXPos);
			lbl.setAlignment(Pos.CENTER);
			currentXPos += 87.0;
			callendarPane.getChildren().add(lbl);
		}
	}
	
}
