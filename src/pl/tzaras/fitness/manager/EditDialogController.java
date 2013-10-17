package pl.tzaras.fitness.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import pl.tzaras.fitness.manager.db.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditDialogController  implements Initializable  {

	@FXML private TextField tfDuration;
	
	@FXML private ComboBox<TrainerWrapper> cmbTrainer;
	@FXML private ComboBox<TypeWrapper> cmbClassType;
	@FXML private ComboBox<String> cmbDay;
	@FXML private ComboBox<RoomWrapper> cmbRoom;
	@FXML private ComboBox<String> cmbHour;
	@FXML private ComboBox<String> cmbMinute;

	public void initialize(URL arg0, ResourceBundle arg1) {
		cmbDay.getItems().setAll("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela");
		ArrayList<String> hours = new ArrayList<String>();
		for (int i=0; i<24; i++) hours.add(String.valueOf(i));
		cmbHour.getItems().setAll(hours);
		
		ArrayList<String> minutes = new ArrayList<String>();
		for (int i=0; i<60; i++) minutes.add(String.valueOf(i));
		cmbMinute.getItems().setAll(minutes);
		
		DataManager.getInstance().getGymTrainerManager().initializeCombo(cmbTrainer);
		DataManager.getInstance().getGymClassTypeManager().initializeCombo(cmbClassType);
		DataManager.getInstance().getGymRoomManager().initializeCombo(cmbRoom);
		
	}

}