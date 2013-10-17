package pl.tzaras.fitness.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import pl.tzaras.fitness.manager.db.DataManager;
import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.utils.ManagerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditDialogController  implements Initializable  {

	Stage dialogStage;
	
	@FXML private TextField tfDuration;
	@FXML private TextField tfParticipants;
	
	@FXML private ComboBox<TrainerWrapper> cmbTrainer;
	@FXML private ComboBox<TypeWrapper> cmbClassType;
	@FXML private ComboBox<String> cmbDay;
	@FXML private ComboBox<RoomWrapper> cmbRoom;
	@FXML private ComboBox<String> cmbHour;
	@FXML private ComboBox<String> cmbMinute;
	private GymClass gymClass;

	public void initialize(URL arg0, ResourceBundle arg1) {
		ManagerUtils.fillWithWeekDays(cmbDay);
		ManagerUtils.fillWithHours(cmbHour);
		ManagerUtils.fillWithMinutes(cmbMinute);
		
		DataManager.getInstance().getGymTrainerManager().initializeCombo(cmbTrainer);
		DataManager.getInstance().getGymClassTypeManager().initializeCombo(cmbClassType);
		DataManager.getInstance().getGymRoomManager().initializeCombo(cmbRoom);
	}
	
	@FXML protected void updateClass(MouseEvent event) {
		gymClass.setClassRoom(cmbRoom.getSelectionModel().getSelectedItem().getRoom());
		gymClass.setClassTrainer(cmbTrainer.getSelectionModel().getSelectedItem().getGymTrainer());
		gymClass.setClassType(cmbClassType.getSelectionModel().getSelectedItem().getClassType());
		
		gymClass.setStartTime(gymClass.getStartTime()
				.withDayOfWeek(ManagerUtils.dayToInt(cmbDay.getValue()))
				.withHourOfDay(Integer.valueOf(cmbHour.getValue()))
				.withMinuteOfHour(Integer.valueOf(cmbMinute.getValue())));
		gymClass.setDuration(Long.valueOf(tfDuration.getText()));
		gymClass.setParticipants(Integer.valueOf(tfParticipants.getText()));
		
		dialogStage.close();
	}
	
	@FXML protected void handleCancel(MouseEvent event) {
		dialogStage.close();
	}
	
	public GymClass getGymClass() {
		return this.gymClass;
	}
	
	public void setGymClass(GymClass gymClass) {
		this.gymClass = gymClass;
		
		for (TrainerWrapper tw : cmbTrainer.getItems()) {
			if (tw.getGymTrainer().getTrainerId() == gymClass.getClassTrainer().getTrainerId()) {
				cmbTrainer.getSelectionModel().select(tw);
				break;
			}
		}
		
		for (RoomWrapper rw : cmbRoom.getItems()) {
			if (rw.getRoom().getID() == gymClass.getClassRoom().getID()) {
				cmbRoom.getSelectionModel().select(rw);
				break;
			}
		}
		
		for (TypeWrapper tw : cmbClassType.getItems()) {
			if (tw.getClassType().getClassId() == gymClass.getClassType().getClassId()) {
				cmbClassType.getSelectionModel().select(tw);
				break;
			}
		}
		
		cmbDay.getSelectionModel().select(ManagerUtils.intToWeekDay(gymClass.getStartTime().getDayOfWeek()));
		cmbHour.getSelectionModel().select(String.valueOf(gymClass.getStartTime().getHourOfDay()));
		cmbMinute.getSelectionModel().select(String.valueOf(gymClass.getStartTime().getMinuteOfHour()));
		tfDuration.setText(String.valueOf(gymClass.getDuration()));
		tfParticipants.setText(String.valueOf(gymClass.getParticipants()));
	}

	public void setEditDialogStage(Stage stage) {
		this.dialogStage = stage;
	}
	
}