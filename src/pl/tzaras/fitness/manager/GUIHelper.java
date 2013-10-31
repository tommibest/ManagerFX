package pl.tzaras.fitness.manager;

import pl.tzaras.fitness.manager.utils.ManagerUtils;
import pl.tzaras.fitness.manager.utils.MyLogger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GUIHelper {

	private static MyLogger log;
	static {
		log = MyLogger.getInstance();
	}
	
	private static double headerHeight = 25.0;
	
	public static void prepareHoursColumn(AnchorPane callendarPane) {
		log.logEntry("prepareHoursColumn");
		double rowHeight = callendarPane.getPrefHeight()/(ManagerUtils.endHour-ManagerUtils.startHour);
		for (int i=ManagerUtils.startHour;i<ManagerUtils.endHour; i++) {
			Label newLbl = new Label();
			if (i<10) newLbl.setText("0"+i+":00");
			else newLbl.setText(String.valueOf(i)+":00");
			newLbl.setPrefSize(87.0, rowHeight);
			newLbl.setAlignment(Pos.CENTER);
			newLbl.getStyleClass().add("myborder");
			double rowPosition = headerHeight + (i-ManagerUtils.startHour)*rowHeight;
			AnchorPane.setTopAnchor(newLbl, rowPosition);
			AnchorPane.setLeftAnchor(newLbl, 0.0);
			callendarPane.getChildren().add(newLbl);
		}
		log.logEnd("prepareHoursColumn");
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
		
		Double currentXPos = 87.0;
		for (Label lbl : weekLabel) {
			lbl.setPrefSize(87.0, headerHeight);
			AnchorPane.setTopAnchor(lbl, 0.0);
			AnchorPane.setLeftAnchor(lbl, currentXPos);
			lbl.setAlignment(Pos.CENTER);
			lbl.getStyleClass().add("myborder");
			currentXPos += 87.0;
			callendarPane.getChildren().add(lbl);
		}
	}

	public static double getHederHeight() {
		return headerHeight;
	}
	
}
