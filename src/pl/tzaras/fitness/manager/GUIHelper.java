package pl.tzaras.fitness.manager;

import org.thehecklers.monologfx.MonologFX;
import org.thehecklers.monologfx.MonologFXBuilder;
import org.thehecklers.monologfx.MonologFXButton;
import org.thehecklers.monologfx.MonologFXButtonBuilder;
import org.thehecklers.monologfx.MonologFXButton.Type;

import pl.tzaras.fitness.manager.utils.ManagerUtils;
import pl.tzaras.fitness.manager.utils.MyLogger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

public class GUIHelper {

	private static final Color CALLENDAR_LINE_COLOR = Color.GRAY;
	private static final double CALLENDAR_LINE_WIDTH = 0.1f;
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
			newLbl.setStyle("-fx-background-color: #eee; -fx-border-color:white;");
			double rowPosition = headerHeight + (i-ManagerUtils.startHour)*rowHeight;
			if ( i%2 == 1 ) {
				Rectangle rectangle = RectangleBuilder.create().x(88).y(rowPosition).width(callendarPane.getWidth()-88).height(rowHeight).style("-fx-fill:  #efefef;").build();
				callendarPane.getChildren().add(rectangle);
			}
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
		
		Double currentXPos = 88.0;
		for (Label lbl : weekLabel) {
			lbl.setPrefSize(87.0, headerHeight);
			AnchorPane.setTopAnchor(lbl, 0.0);
			AnchorPane.setLeftAnchor(lbl, currentXPos);
			lbl.setAlignment(Pos.CENTER);
			lbl.setStyle("-fx-background-color: #eee; -fx-border-color:white;");
			lbl.getStyleClass().add("myborder");
			Line line = LineBuilder.create().startX(currentXPos+87).startY(0).endX(currentXPos+87).endY(callendarPane.getHeight()).style("-fx-fill:  #eee;").strokeWidth(CALLENDAR_LINE_WIDTH).build();
			callendarPane.getChildren().add(line);
			currentXPos += 87.0;
			callendarPane.getChildren().add(lbl);
		}
		
	}

	public static double getHederHeight() {
		return headerHeight;
	}

	public static Type confirmDeletion(String message) {
		MonologFXButton btnYes = MonologFXButtonBuilder.create().defaultButton(true).type(MonologFXButton.Type.YES).label("Tak").build();
		MonologFXButton btnNo = MonologFXButtonBuilder.create().cancelButton(false).type(MonologFXButton.Type.NO).label("Nie").build();
		MonologFX confirmDialog = MonologFXBuilder.create().modal(true).message(message).titleText("Usuwanie").button(btnYes).button(btnNo).buttonAlignment(MonologFX.ButtonAlignment.CENTER).build();
		return confirmDialog.showDialog();
	}

	public static Type nothingSelectedWarning(String message) {
		MonologFXButton btnYes = MonologFXButtonBuilder.create().defaultButton(true).type(MonologFXButton.Type.OK).build();
		MonologFX nothingSelectedDialog = MonologFXBuilder.create().modal(true).message(message).titleText("Brak zaznaczenia").button(btnYes).buttonAlignment(MonologFX.ButtonAlignment.CENTER).build();
		return nothingSelectedDialog.showDialog();
	}

	public static Type cannotDelete(String message) {
		MonologFXButton btnYes = MonologFXButtonBuilder.create().defaultButton(true).type(MonologFXButton.Type.OK).build();
		MonologFX nothingSelectedDialog = MonologFXBuilder.create().modal(true).message(message).titleText("Brak zaznaczenia").button(btnYes).buttonAlignment(MonologFX.ButtonAlignment.CENTER).build();
		return nothingSelectedDialog.showDialog();		
	}
	
}
