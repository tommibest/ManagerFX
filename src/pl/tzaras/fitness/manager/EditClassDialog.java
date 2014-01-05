package pl.tzaras.fitness.manager;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pl.tzaras.fitness.manager.db.data.GymClass;

public class EditClassDialog {
	
	private Stage stage = new Stage();
	private Parent root;	
	private EditDialogController controller;
	
	public EditClassDialog(Window owner) {

		stage = new Stage();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDialog.fxml"));
			root = (Parent) loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Dodaj zajęcia");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(owner);
			controller = (EditDialogController) loader.getController();
			GymClass newClass = new GymClass();
			newClass.setStartTime(AppState.getInstance().getCurrentMonday());
			controller.setGymClass(newClass, false);
			controller.setEditDialogStage(stage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public GymClass getGymClass() {
		if (controller.getStatus() == EditDialogController.OK_BUTTON )
			return controller.getGymClass();
		else 
			return null;
	}

	public int getRepetition() {
		if (controller.getStatus() == EditDialogController.OK_BUTTON) {
			return controller.getRepetition();
		} else {
			return -1;
		}
	}

	public void showAndWait() {
		stage.showAndWait();
	}

}
