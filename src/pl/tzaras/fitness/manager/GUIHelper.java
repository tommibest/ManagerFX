package pl.tzaras.fitness.manager;

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
import javafx.stage.StageStyle;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXBuilder;
import jfxtras.labs.dialogs.MonologFXButton;
import jfxtras.labs.dialogs.MonologFXButton.Type;
import jfxtras.labs.dialogs.MonologFXButtonBuilder;

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
		double rowHeight = (callendarPane.getPrefHeight()-headerHeight)/(ManagerUtils.endHour-ManagerUtils.startHour);
		for (int i=ManagerUtils.startHour;i<ManagerUtils.endHour; i++) {
			Label newLbl = new Label();
			if (i<10) newLbl.setText("0"+i+":00");
			else newLbl.setText(String.valueOf(i)+":00");
			newLbl.setPrefSize(87.0, rowHeight);
			newLbl.setAlignment(Pos.CENTER);
			newLbl.setStyle("-fx-background-color: #eee; -fx-border-color:white;");
			double rowPosition = headerHeight + (i-ManagerUtils.startHour)*rowHeight;
			if ( i%2 == 1 ) {
				callendarPane.getChildren().add(RectangleBuilder.create().x(88).y(rowPosition).width(callendarPane.getPrefWidth()-88).height(rowHeight).style("-fx-fill:  #efefef;").build());
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
			if ( !lbl.getText().equalsIgnoreCase(ManagerUtils.SUNDAY_pl) ) {
				callendarPane.getChildren().add(
					LineBuilder.create().startX(currentXPos + 87).startY(0)
							.endX(currentXPos + 87)
							.endY(callendarPane.getPrefHeight())
							.style("-fx-fill:  #eee;")
							.strokeWidth(CALLENDAR_LINE_WIDTH).build());
			}
			currentXPos += 87.0;
			callendarPane.getChildren().add(lbl);
		}
		
	}

	public static double getHeaderHeight() {
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
		MonologFX cannotDeleteDialog = MonologFXBuilder.create().modal(true).message(message).titleText("Brak zaznaczenia").button(btnYes).buttonAlignment(MonologFX.ButtonAlignment.CENTER).build();
		return cannotDeleteDialog.showDialog();		
	}
	
}
