/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tzaras.fitness.manager;

import java.awt.Event;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.labs.dialogs.MonologFXButton;

import org.hibernate.dialect.FirebirdDialect;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.tzaras.fitness.manager.db.DataManager;
import pl.tzaras.fitness.manager.db.GymClassManager;
import pl.tzaras.fitness.manager.db.GymClassTypeManager;
import pl.tzaras.fitness.manager.db.GymRoomManager;
import pl.tzaras.fitness.manager.db.GymTrainerManager;
import pl.tzaras.fitness.manager.db.RoomWrapper;
import pl.tzaras.fitness.manager.db.TrainerWrapper;
import pl.tzaras.fitness.manager.db.TypeWrapper;
import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.db.data.GymRoom;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.ManagerUtils;

/**
 * 
 * @author tommi
 */
public class ManagerController implements Initializable {

	private static AppState state;
	static {
		state = AppState.getInstance();
	}
	
	@FXML
	private TextField txtFieldInstructorName;
	@FXML
	private TextField txtFieldInstructorSurrname;
	@FXML
	private TextField txtFieldInstructorRate;
	@FXML
	private TextField tfClassTypeName;
	@FXML
	private TextField tfRoomName;
	@FXML
	private TextField tfDuration;

	@FXML
	private Button btnAddClass;
	@FXML
	private Button btnAddInstructor;
	@FXML
	private Button btnAddRoom;

	@FXML
	private ComboBox<String> daysCombo;
	@FXML
	private ComboBox<String> cmbHours;
	@FXML
	private ComboBox<String> cmbMinutes;

	@FXML
	private TableView<GymRoom> tblRoom;
	@FXML
	private TableColumn<GymRoom, String> colRoomName;

	@FXML
	private TableView<GymClassType> tblClassType;
	@FXML
	private TableColumn<GymClassType, String> colClassType;

	@FXML
	private TableView<GymTrainer> tblTrainers;
	@FXML
	private TableColumn<GymTrainer, String> colName;
	@FXML
	private TableColumn<GymTrainer, String> colSurrname;
	@FXML
	private TableColumn<GymTrainer, Double> colRate;

	@FXML
	private TableView<GymClass> reportTable;
	@FXML
	private TableColumn<GymClass, String> reportClassType;
	@FXML
	private TableColumn<GymClass, String> reportClassTrainer;
	@FXML
	private TableColumn<GymClass, String> reportClassRoom;
	@FXML
	private TableColumn<GymClass, String> reportClassDay;
	@FXML
	private TableColumn<GymClass, String> reportClassHour;
	@FXML
	private TableColumn<GymClass, Integer> reportParticipants;
	@FXML
	private TableColumn<GymClass, Long> reportClassDuration;

	@FXML
	private Label lblTrainer2;
	@FXML
	private Label lblOverviewType;
	@FXML
	private Label lblOverviewRoom;
	@FXML
	private Label lblOverviewTrainer;
	@FXML
	private Label lblOverviewTrainer1;
	@FXML
	private Label lblOverviewDay;
	@FXML
	private Label lblOverviewHour;
	@FXML
	private Label lblOverviewEnrolled;
	@FXML
	private Button btnRemoveClass;
	@FXML
	private Button btnEditClass;

	@FXML
	private ComboBox<RoomWrapper> cmbSelectRoom;
	@FXML
	private ComboBox<TrainerWrapper> cmbSelectTrainer;
	@FXML
	private Button btnNextWeek;
	@FXML
	private Button btnPrevWeek;
	@FXML
	private Label lblCurrentWeek;

	@FXML
	private CheckBox chbTrainer;
	@FXML
	private CheckBox chbType;
	@FXML
	private CheckBox chbDay;
	@FXML
	private CheckBox chbFromDate;
	@FXML
	private CheckBox chbToDate;
	@FXML
	private CheckBox chbFromHour;
	@FXML
	private CheckBox chbToHour;

	@FXML
	private ComboBox<TrainerWrapper> cmbSearchTrainer;
	@FXML
	private ComboBox<TypeWrapper> cmbSearchType;
	@FXML
	private ComboBox<String> cmbSearchDay;
	@FXML
	private TextField tfFromDate;
	@FXML
	private TextField tfToDate;
	@FXML
	private ComboBox<String> cmbFromHour;
	@FXML
	private ComboBox<String> cmbFromMinute;
	@FXML
	private ComboBox<String> cmbToHour;
	@FXML
	private ComboBox<String> cmbToMinute;
	@FXML
	private TextField tfToHour;
	@FXML
	private Button btnSearch;

	@FXML
	private TextField tfSearchResult;

	@FXML
	private TextField tfPayResult;
	@FXML
	private AnchorPane callendarPane;

	@FXML
	private CheckBox chbPension;
	@FXML
	private TextField tfPension;

	@FXML
	protected void addTrainer(MouseEvent arg0) {
		if (!txtFieldInstructorName.getText().isEmpty() && !txtFieldInstructorSurrname.getText().isEmpty()) {
			GymTrainerManager mngr = DataManager.getInstance().getGymTrainerManager();
			double rateOfPay = 0.0;
			if (!txtFieldInstructorRate.getText().isEmpty())
				rateOfPay = Double.parseDouble(txtFieldInstructorRate.getText());

			if (!mngr.trainerExists(txtFieldInstructorName.getText(), txtFieldInstructorSurrname.getText())) {

				mngr.saveTrainer(txtFieldInstructorName.getText(), txtFieldInstructorSurrname.getText(), rateOfPay);
				System.out.println("Adding instructor: " + txtFieldInstructorName.getText() + ", "
						+ txtFieldInstructorSurrname.getText());

				TrainerWrapper tw = cmbSelectTrainer.getSelectionModel().getSelectedItem();
				mngr.initializeComboWithDefault(cmbSelectTrainer);
				cmbSelectTrainer.setValue(tw);
				mngr.initializeComboWithDefault(cmbSearchTrainer);

				ObservableList<GymTrainer> data = FXCollections.observableArrayList(DataManager.getInstance()
						.getGymTrainerManager().getTrainers());
				for (GymTrainer trainer : data) {
					System.out.println(trainer.getName() + ", " + trainer.getSurrname());
				}
				tblTrainers.setItems(data);
			} else {
				GUIHelper.warningWithOk("Trener " + txtFieldInstructorName.getText() + " " + txtFieldInstructorSurrname.getText()
						+ " już istnieje.");
			}
			txtFieldInstructorName.setText("");
			txtFieldInstructorSurrname.setText("");
			txtFieldInstructorRate.setText("");
		} else {
			System.err.println("Name or surrname of instructor is empty");
		}
	}

	@FXML
	protected void removeInstructor(MouseEvent arg0) {
		GymTrainer trainer = tblTrainers.getSelectionModel().getSelectedItem();
		if (tblTrainers.getSelectionModel().getSelectedItem() == null) {
			GUIHelper.nothingSelectedWarning("Żaden trener na liście nie została zaznaczona do usunięcia");
		} else if (GUIHelper.confirmDeletion("Czy na pewno chcesz usunąć trenera: " + trainer.getName() + " "
				+ trainer.getSurrname() + "?") == MonologFXButton.Type.YES) {
			GymTrainerManager mngr = DataManager.getInstance().getGymTrainerManager();

			int result = mngr.delete(trainer);
			if (result == ManagerUtils.SUCCESS) {
				mngr.initializeCombo(cmbSearchTrainer);
				mngr.initializeComboWithDefault(cmbSelectTrainer);

				ObservableList<GymTrainer> data = FXCollections.observableArrayList(mngr.getTrainers());
				tblTrainers.setItems(data);
			} else if (result == ManagerUtils.CONSTRAINT_VIOLATTION) {

				GUIHelper.cannotDelete("Trener: " + trainer.getName() + " " + trainer.getSurrname()
						+ ", nie może być usunięty.\n Jest przypisany do jakichś zajęć.");
			}
		}
	}

	@FXML
	protected void removeClassType(MouseEvent arg0) {
		GymClassType cType = tblClassType.getSelectionModel().getSelectedItem();
		if (tblClassType.getSelectionModel().getSelectedItem() == null) {
			GUIHelper.nothingSelectedWarning("Żaden typ zajęć na liście nie została zaznaczona do usunięcia");
		} else if (GUIHelper.confirmDeletion("Czy na pewno chcesz usunąć typ zajęć: " + cType.getName() + "?") == MonologFXButton.Type.YES) {
			GymClassTypeManager mngr = DataManager.getInstance().getGymClassTypeManager();

			int result = mngr.deleteClassType(cType);
			if (result == ManagerUtils.SUCCESS) {
				mngr.initializeCombo(cmbSearchType);

				ObservableList<GymClassType> data = FXCollections.observableArrayList(mngr.getClassTypes());
				tblClassType.setItems(data);
			} else if (result == ManagerUtils.CONSTRAINT_VIOLATTION) {
				GUIHelper
						.cannotDelete("Typ zajęć: " + cType.getName() + ", nie może być usunięty.\n Istnieją zajęcia tego typu.");
			}
		}
	}

	@FXML
	protected void removeRoom(MouseEvent arg0) {
		GymRoom room = tblRoom.getSelectionModel().getSelectedItem();

		if (tblRoom.getSelectionModel().getSelectedItem() == null) {
			GUIHelper.nothingSelectedWarning("Żadna sala na liście nie została zaznaczona do usunięcia");
		} else if (GUIHelper.confirmDeletion("Czy na pewno chcesz usunąć salę: " + room.getName() + "?") == MonologFXButton.Type.YES) {
			GymRoomManager mngr = DataManager.getInstance().getGymRoomManager();
			int result = mngr.deleteRoom(room);
			if (result == ManagerUtils.SUCCESS) {
				ObservableList<GymRoom> data = FXCollections.observableArrayList(mngr.getRooms());
				tblRoom.setItems(data);
			} else if (result == ManagerUtils.CONSTRAINT_VIOLATTION) {
				GUIHelper.cannotDelete("Sala: " + room.getName()
						+ ", nie może być usunięta.\n Istnieją zajęcia przypisane do tej sali.");
			}
		}
	}

	@FXML
	protected void addClassType(MouseEvent arg0) {
		if (!tfClassTypeName.getText().isEmpty()) {
			GymClassTypeManager mngr = DataManager.getInstance().getGymClassTypeManager();
			if (!mngr.classTypeExists(tfClassTypeName.getText())) {
				System.out.println("Adding class: " + tfClassTypeName.getText());
				mngr.addClassType(tfClassTypeName.getText());

				mngr.initializeCombo(cmbSearchType);

				ObservableList<GymClassType> classTypes = FXCollections.observableArrayList(mngr.getClassTypes());
				tblClassType.setItems(classTypes);
			} else {
				GUIHelper.warningWithOk("Zajęcia " + tfClassTypeName.getText() + " juz istnieją");
			}

			tfClassTypeName.setText("");
		} else {
			System.err.println("Cannot add empty class type.");
		}
	}

	@FXML
	protected void editType(CellEditEvent<GymClassType, String> event) {
		DataManager.getInstance().getGymClassTypeManager().updateClassType(event.getRowValue().getClassId(), event.getNewValue());
	}

	@FXML
	protected void addRoom(MouseEvent arg0) {
		if (!tfRoomName.getText().isEmpty()) {
			GymRoomManager mngr = DataManager.getInstance().getGymRoomManager();
			if (!mngr.roomExists(tfRoomName.getText())) {
				System.out.println("Adding room: " + tfRoomName.getText());
				mngr.saveRoom(tfRoomName.getText());

				RoomWrapper rw = cmbSelectRoom.getSelectionModel().getSelectedItem();
				mngr.initializeComboWithDefault(cmbSelectRoom);
				cmbSelectRoom.setValue(rw);

				ObservableList<GymRoom> rooms = FXCollections.observableArrayList(mngr.getRooms());
				tblRoom.setItems(rooms);
			} else {
				GUIHelper.warningWithOk("Sala " + tfRoomName.getText() + " już istnieje.");
				tfRoomName.setText("");
			}

		} else {
			System.err.println("Cannot add empty room.");
		}
	}

	@FXML
	protected void addClass(ActionEvent event) {
		GymClassManager mngr = DataManager.getInstance().getGymClassManager();

		EditClassDialog dialog = new EditClassDialog(((Node) event.getSource()).getScene().getWindow());
		dialog.showAndWait();
		GymClass gClass = null;
		if ( (gClass = dialog.getGymClass()) != null ){
			if ( mngr.isRoomAvailableForClass(gClass) ) { 
				mngr.saveClass(gClass);
				populateCurrentWeekData();
				displayOverview(gClass);
			} else {
				DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-YYYY HH:mm");
				GUIHelper.displayWarning("Nie można utworzyć zajęć:" +fmt.print(gClass.getStartTime())+"\nSala o wybranej godzinie jest już zajęta.");
			}
			for (int i = 0; i < dialog.getRepetition(); i++) {
				GymClass classCopy = mngr.makeCopy(gClass);
				classCopy.setStartTime(gClass.getStartTime().plusWeeks(i + 1));
				classCopy.setParticipants(0);
				if ( mngr.isRoomAvailableForClass(classCopy) ) { 
					mngr.saveClass(classCopy);
				} else {
					DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-YYYY HH:mm");
					GUIHelper.displayWarning("Nie można utworzyć zajęć:" +fmt.print(classCopy.getStartTime())+"\nSala o wybranej godzinie jest już zajęta.");
				}
			}
			
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		state.setInitializing(true);
		if (state.isPopulate()) {
			populateTestData();
		}

		initializeMainTab();
		initializeReportTab();
		initializeManagementTab();
		populateCurrentWeekData();
		state.setInitializing(false);
	}

	private void initializeReportTab() {
		reportClassType
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
						if (p.getValue() != null) {
							return new SimpleStringProperty(p.getValue().getClassType().getName());
						} else {
							return new SimpleStringProperty("<no name>");
						}
					}
				});

		reportClassTrainer
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
						if (p.getValue() != null) {
							System.out.println(p.getValue().getStartTime());
							return new SimpleStringProperty(p.getValue().getClassTrainer1().getName() + ", "
									+ p.getValue().getClassTrainer1().getSurrname());
						} else {
							return new SimpleStringProperty("<no name>");
						}
					}
				});

		reportClassRoom
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
						if (p.getValue() != null) {
							return new SimpleStringProperty(p.getValue().getClassRoom().getName());
						} else {
							return new SimpleStringProperty("<no name>");
						}
					}
				});

		reportClassDay
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
						if (p.getValue() != null) {
							DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-YYYY");
							return new SimpleStringProperty(fmt.print(p.getValue().getStartTime()));
						} else {
							return new SimpleStringProperty("<no name>");
						}
					}
				});

		reportClassHour
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
						if (p.getValue() != null) {
							DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
							return new SimpleStringProperty(fmt.print(p.getValue().getStartTime()));
						} else {
							return new SimpleStringProperty("<no name>");
						}
					}
				});

		reportClassDuration.setCellValueFactory(new PropertyValueFactory<GymClass, Long>("duration"));
		reportParticipants.setCellValueFactory(new PropertyValueFactory<GymClass, Integer>("participants"));
	}

	private void initializeMainTab() {

		DateTime now = new DateTime();
		state.setCurrentMonday(now.withDayOfWeek(DateTimeConstants.MONDAY).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
		state.setCurrentSunday(state.getCurrentMonday().plusWeeks(1));
		displaySelectedWeek();
		initializeCalendarView();
		populateCurrentWeekData();
		initializeSearchPanel();
		btnEditClass.setDisable(true);
		btnRemoveClass.setDisable(true);
	}

	private void initializeCalendarView() {
		callendarPane.setStyle("-fx-background-color: white");
		DataManager.getInstance().getGymRoomManager().initializeComboWithDefault(cmbSelectRoom);
		DataManager.getInstance().getGymTrainerManager().initializeComboWithDefault(cmbSelectTrainer);
	}

	private void initializeSearchPanel() {
		ManagerUtils.fillWithWeekDays(cmbSearchDay);
		DataManager.getInstance().getGymTrainerManager().initializeCombo(cmbSearchTrainer);
		DataManager.getInstance().getGymClassTypeManager().initializeCombo(cmbSearchType);

		ManagerUtils.fillWithHours(cmbFromHour);
		ManagerUtils.fillWithHours(cmbToHour);
		ManagerUtils.fillWithMinutes(cmbFromMinute);
		ManagerUtils.fillWithMinutes(cmbToMinute);
	}

	@FXML
	protected void refreshCallendar(ActionEvent event) {
		if (!state.isInitializing()) {
			populateCurrentWeekData();
		}
	}

	private void populateCurrentWeekData() {
		callendarPane.getChildren().clear();
		GUIHelper.prepareHoursColumn(callendarPane);
		GUIHelper.prepareDayOfWeekHeaders(callendarPane);

		ObservableList<GymClass> data = FXCollections.observableArrayList(DataManager.getInstance().getGymClassManager()
				.getClasses(state.getCurrentMonday(), state.getCurrentSunday()));

		for (GymClass gymClass : data) {
			if ((cmbSelectRoom.getValue().equals(DataManager.getInstance().getGymRoomManager().defaultSelection) || gymClass
					.getClassRoom().getID() == cmbSelectRoom.getSelectionModel().getSelectedItem().getRoom().getID())
					&& ((cmbSelectTrainer.getItems().size() != 0 && cmbSelectTrainer.getValue().equals(
							DataManager.getInstance().getGymTrainerManager().defaultSelection))
							|| (gymClass.getClassTrainer1() != null && gymClass.getClassTrainer1().getTrainerId() == cmbSelectTrainer
									.getSelectionModel().getSelectedItem().getGymTrainer().getTrainerId()) || (gymClass
							.getClassTrainer2() != null && gymClass.getClassTrainer2().getTrainerId() == cmbSelectTrainer
							.getSelectionModel().getSelectedItem().getGymTrainer().getTrainerId()))) {

				callendarPane.getChildren().add(CallendarEntryManager.getInstance().getEntry(gymClass, this));
				System.out.println("Getting: " + gymClass.getClassId());
			}
		}
	}

	private boolean updateTrainer(GymTrainer newTrainer, boolean validateExistance) {
		GymTrainerManager mngr = DataManager.getInstance().getGymTrainerManager();
		if (!mngr.trainerExists(newTrainer.getName(), newTrainer.getSurrname()) || !validateExistance) {
			mngr.updateTrainer(newTrainer.getTrainerId(), newTrainer.getName(), newTrainer.getSurrname(),
					newTrainer.getRateOfPay());
			mngr.initializeCombo(cmbSearchTrainer);
			TrainerWrapper tw = cmbSelectTrainer.getSelectionModel().getSelectedItem();
			mngr.initializeComboWithDefault(cmbSelectTrainer);
			cmbSelectTrainer.setValue(tw);
			CallendarEntryManager.getInstance().refreshEntries();
			populateCurrentWeekData();
			return true;
		} else {
			GUIHelper
					.nothingSelectedWarning("Trener " + newTrainer.getName() + " " + newTrainer.getSurrname() + " już istnieje.");
			return false;
		}
	}

	private boolean updateClassType(GymClassType classType, boolean validateExistance) {
		GymClassTypeManager mngr = DataManager.getInstance().getGymClassTypeManager();
		if (!mngr.classTypeExists(classType.getName()) || !validateExistance) {
			mngr.updateClassType(classType.getClassId(), classType.getName());
			CallendarEntryManager.getInstance().refreshEntries();
			populateCurrentWeekData();
			DataManager.getInstance().getGymClassTypeManager().initializeCombo(cmbSearchType);
			return true;
		} else {
			GUIHelper.nothingSelectedWarning("Typ zajęć " + classType.getName() + " już istnieje.");
			return false;
		}
	}

	private boolean updateGymRoom(GymRoom newGymRoom, boolean b) {
		GymRoomManager mngr = DataManager.getInstance().getGymRoomManager();
		if (!mngr.roomExists(newGymRoom.getName())) {
			mngr.updateRoom(newGymRoom.getID(), newGymRoom.getName());
			mngr.initializeComboWithDefault(cmbSelectRoom);
			CallendarEntryManager.getInstance().refreshEntries();
			populateCurrentWeekData();
			return false;
		} else {
			GUIHelper.nothingSelectedWarning("Sala " + newGymRoom.getName() + " już istnieje.");
			return false;
		}
	}

	private void initializeManagementTab() {
		ObservableList<GymTrainer> data = FXCollections.observableArrayList(DataManager.getInstance().getGymTrainerManager()
				.getTrainers());
		for (GymTrainer trainer : data) {
			System.out.println(trainer.getName() + ", " + trainer.getSurrname());
		}

		tblTrainers.setItems(data);
		tblTrainers.setEditable(true);
		Callback<TableColumn<GymTrainer, String>, TableCell<GymTrainer, String>> trainerStringCellFactory = new Callback<TableColumn<GymTrainer, String>, TableCell<GymTrainer, String>>() {
			public TableCell<GymTrainer, String> call(TableColumn<GymTrainer, String> p) {
				return new EditingCell<GymTrainer, String>();
			}
		};
		colName.setCellValueFactory(new PropertyValueFactory<GymTrainer, String>("name"));
		colName.setCellFactory(trainerStringCellFactory);
		colName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GymTrainer, String>>() {
			public void handle(TableColumn.CellEditEvent<GymTrainer, String> t) {
				GymTrainer trainer = (GymTrainer) t.getTableView().getItems().get(t.getTablePosition().getRow());
				if (t.getNewValue().compareTo(t.getOldValue()) != 0) {
					GymTrainer newTrainer = new GymTrainer(t.getNewValue(), trainer.getSurrname(), trainer.getRateOfPay());
					newTrainer.setTrainerId(trainer.getTrainerId());
					if (!updateTrainer(newTrainer, true)) {
						tblTrainers.getColumns().get(0).setVisible(false);
						tblTrainers.getColumns().get(0).setVisible(true);
					}
				}

			}
		});

		colSurrname.setCellValueFactory(new PropertyValueFactory<GymTrainer, String>("surrname"));
		colSurrname.setCellFactory(trainerStringCellFactory);
		colSurrname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GymTrainer, String>>() {
			public void handle(TableColumn.CellEditEvent<GymTrainer, String> t) {
				GymTrainer trainer = (GymTrainer) t.getTableView().getItems().get(t.getTablePosition().getRow());
				if (t.getNewValue().compareTo(t.getOldValue()) != 0) {
					GymTrainer newTrainer = new GymTrainer(trainer.getName(), t.getNewValue(), trainer.getRateOfPay());
					newTrainer.setTrainerId(trainer.getTrainerId());
					if (!updateTrainer(newTrainer, true)) {
						tblTrainers.getColumns().get(1).setVisible(false);
						tblTrainers.getColumns().get(1).setVisible(true);
					}
				}
			}
		});

		Callback<TableColumn<GymTrainer, Double>, TableCell<GymTrainer, Double>> trainerDoubleCellFactory = new Callback<TableColumn<GymTrainer, Double>, TableCell<GymTrainer, Double>>() {

			public TableCell<GymTrainer, Double> call(TableColumn<GymTrainer, Double> arg0) {
				return new EditingDoubleCell<GymTrainer, Double>();
			}

		};
		colRate.setCellValueFactory(new PropertyValueFactory<GymTrainer, Double>("rateOfPay"));
		colRate.setCellFactory(trainerDoubleCellFactory);
		colRate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GymTrainer, Double>>() {
			public void handle(TableColumn.CellEditEvent<GymTrainer, Double> t) {
				GymTrainer trainer = (GymTrainer) t.getTableView().getItems().get(t.getTablePosition().getRow());
				if (t.getNewValue().compareTo(t.getOldValue()) != 0) {
					GymTrainer newTrainer = new GymTrainer(trainer.getName(), trainer.getSurrname(), t.getNewValue());
					newTrainer.setTrainerId(trainer.getTrainerId());
					updateTrainer(newTrainer, false);
				}

			}
		});

		colClassType.setCellValueFactory(new PropertyValueFactory<GymClassType, String>("name"));
		ObservableList<GymClassType> classTypes = FXCollections.observableArrayList(DataManager.getInstance()
				.getGymClassTypeManager().getClassTypes());
		for (GymClassType classType : classTypes) {
			System.out.println(classType.getName());
		}
		tblClassType.setItems(classTypes);
		tblClassType.setEditable(true);
		Callback<TableColumn<GymClassType, String>, TableCell<GymClassType, String>> cellFactory = new Callback<TableColumn<GymClassType, String>, TableCell<GymClassType, String>>() {
			public TableCell<GymClassType, String> call(TableColumn<GymClassType, String> p) {
				return new EditingCell<GymClassType, String>();
			}
		};
		colClassType.setCellFactory(cellFactory);
		colClassType.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GymClassType, String>>() {
			public void handle(TableColumn.CellEditEvent<GymClassType, String> t) {
				GymClassType type = (GymClassType) t.getTableView().getItems().get(t.getTablePosition().getRow());
				if (t.getNewValue().compareTo(t.getOldValue()) != 0) {
					GymClassType newClassType = new GymClassType(type.getClassId(), t.getNewValue());
					if (!updateClassType(newClassType, true)) {
						tblClassType.getColumns().get(1).setVisible(false);
						tblClassType.getColumns().get(1).setVisible(true);
					}
				}

			}

		});
		// ---

		colRoomName.setCellValueFactory(new PropertyValueFactory<GymRoom, String>("name"));
		ObservableList<GymRoom> rooms = FXCollections.observableArrayList(DataManager.getInstance().getGymRoomManager()
				.getRooms());
		for (GymClassType classType : classTypes) {
			System.out.println(classType.getName());
		}
		tblRoom.setItems(rooms);
		tblRoom.setEditable(true);
		Callback<TableColumn<GymRoom, String>, TableCell<GymRoom, String>> roomCellFactory = new Callback<TableColumn<GymRoom, String>, TableCell<GymRoom, String>>() {
			public TableCell<GymRoom, String> call(TableColumn<GymRoom, String> p) {
				return new EditingCell<GymRoom, String>();
			}
		};
		colRoomName.setCellFactory(roomCellFactory);
		colRoomName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GymRoom, String>>() {
			public void handle(TableColumn.CellEditEvent<GymRoom, String> t) {
				GymRoom room = (GymRoom) t.getTableView().getItems().get(t.getTablePosition().getRow());
				if (t.getNewValue().compareTo(t.getOldValue()) != 0) {
					GymRoom newGymRoom = new GymRoom(room.getID(), t.getNewValue());
					if (!updateGymRoom(newGymRoom, true)) {
						tblRoom.getColumns().get(1).setVisible(false);
						tblRoom.getColumns().get(1).setVisible(true);
					}
				}
			}
		});

	}

	private void populateTestData() {
		DataManager.getInstance().getGymTrainerManager().saveTrainer("Szymon", "Wesołowski", 0.0);
		DataManager.getInstance().getGymTrainerManager().saveTrainer("Trener", "Fitness", 0.0);
		DataManager.getInstance().getGymTrainerManager().saveTrainer("Trener", "Mental", 0.0);

		DataManager.getInstance().getGymClassTypeManager().addClassType("FITNESS");
		DataManager.getInstance().getGymClassTypeManager().addClassType("ZUMBA");
		DataManager.getInstance().getGymClassTypeManager().addClassType("GRID");
		DataManager.getInstance().getGymClassTypeManager().addClassType("HAPPY Hours");

		DataManager.getInstance().getGymRoomManager().saveRoom("Sala FITNESS");
		DataManager.getInstance().getGymRoomManager().saveRoom("Sala ROWEROWA");
		DataManager.getInstance().getGymRoomManager().saveRoom("Sala MENTAL");
		DataManager.getInstance().getGymRoomManager().saveRoom("Siłownia");
		DataManager.getInstance().getGymRoomManager().saveRoom("OUTODOOR");
	}

	@FXML
	protected void prevWeekAction(MouseEvent arg0) {
		state.setCurrentMonday( state.getCurrentMonday().minusWeeks(1) );
		state.setCurrentSunday( state.getCurrentSunday().minusWeeks(1) );
		displaySelectedWeek();
		populateCurrentWeekData();
		clearOverview();
	}

	@FXML
	protected void nextWeekAction(MouseEvent arg0) {
		state.setCurrentMonday( state.getCurrentMonday().plusWeeks(1) );
		state.setCurrentSunday( state.getCurrentSunday().plusWeeks(1) );
		displaySelectedWeek();
		populateCurrentWeekData();
		clearOverview();
	}

	private void displaySelectedWeek() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		lblCurrentWeek.setText(fmt.print(state.getCurrentMonday()) + " - " + fmt.print(state.getCurrentSunday()));
	}

	@FXML
	protected void editClass(MouseEvent event) {
		Stage stage = new Stage();
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDialog.fxml"));
			root = (Parent) loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Edytuj zajęcia");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node) event.getSource()).getScene().getWindow());
			EditDialogController controller = (EditDialogController) loader.getController();
			controller.setGymClass(state.getSelectedClass(), true);
			controller.setEditDialogStage(stage);
			stage.showAndWait();
			CallendarEntryManager.getInstance().updateEntry(controller.getGymClass());
			DataManager.getInstance().getGymClassManager().updateClass(controller.getGymClass());
			populateCurrentWeekData();
			displayOverview(controller.getGymClass());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	protected void removeClass(MouseEvent event) {
		if (GUIHelper.confirmDeletion("Czy na pewno chcesz usunąć te zajęcia?") == MonologFXButton.Type.YES) {
			DataManager.getInstance().getGymClassManager().deleteClass(state.getSelectedClass());
			CallendarEntryManager.getInstance().remove(state.getSelectedClass());
			clearOverview();
			populateCurrentWeekData();
			btnRemoveClass.setDisable(true);
			btnEditClass.setDisable(true);
			state.setSelectedClass(null);
		}
	}

	private void clearOverview() {
		lblOverviewType.setText("");
		lblOverviewRoom.setText("");
		lblOverviewTrainer.setText("");
		lblTrainer2.setDisable(true);
		lblOverviewTrainer1.setText("");
		lblOverviewDay.setText("");
		lblOverviewHour.setText("");
		lblOverviewEnrolled.setText("");
		btnRemoveClass.setDisable(true);
		btnEditClass.setDisable(true);
	}

	@FXML
	protected void enableWidget(ActionEvent event) {

		CheckBox source = (CheckBox) event.getSource();
		if (source.isSelected()) {
			if (source.getId().compareTo("chbTrainer") == 0) {
				cmbSearchTrainer.setDisable(false);
			} else if (source.getId().compareTo("chbType") == 0) {
				cmbSearchType.setDisable(false);
			} else if (source.getId().compareTo("chbDay") == 0) {
				cmbSearchDay.setDisable(false);
			} else if (source.getId().compareTo("chbFromDate") == 0) {
				tfFromDate.setDisable(false);
			} else if (source.getId().compareTo("chbToDate") == 0) {
				tfToDate.setDisable(false);
			} else if (source.getId().compareTo("chbFromHour") == 0) {
				cmbFromHour.setDisable(false);
				cmbFromMinute.setDisable(false);
			} else if (source.getId().compareTo("chbToHour") == 0) {
				cmbToHour.setDisable(false);
				cmbToMinute.setDisable(false);
			} else if (source.getId().compareTo("chbPension") == 0) {
				tfPension.setDisable(false);
			}
		} else {
			if (source.getId().compareTo("chbTrainer") == 0) {
				cmbSearchTrainer.setDisable(true);
			} else if (source.getId().compareTo("chbType") == 0) {
				cmbSearchType.setDisable(true);
			} else if (source.getId().compareTo("chbDay") == 0) {
				cmbSearchDay.setDisable(true);
			} else if (source.getId().compareTo("chbFromDate") == 0) {
				tfFromDate.setDisable(true);
			} else if (source.getId().compareTo("chbToDate") == 0) {
				tfToDate.setDisable(true);
			} else if (source.getId().compareTo("chbFromHour") == 0) {
				cmbFromHour.setDisable(true);
				cmbFromMinute.setDisable(true);
			} else if (source.getId().compareTo("chbToHour") == 0) {
				cmbToHour.setDisable(true);
				cmbToMinute.setDisable(true);
			} else if (source.getId().compareTo("chbPension") == 0) {
				tfPension.setDisable(true);
			}
		}

	}

	@FXML
	protected void handleCalculate(ActionEvent event) {
		tfPayResult.setText("");
		ArrayList<GymClass> allClasses = (ArrayList<GymClass>) DataManager.getInstance().getGymClassManager().getClasses(null);
		ArrayList<GymClass> result = new ArrayList<GymClass>();
		if (chbTrainer.isSelected() && chbFromDate.isSelected() && chbToDate.isSelected()) {
			for (GymClass gClass : allClasses) {
				if (chbTrainer.isSelected()) {
					if (cmbSearchTrainer.getValue().getGymTrainer().getTrainerId() != gClass.getClassTrainer1().getTrainerId()
							&& (gClass.getClassTrainer2() != null && cmbSearchTrainer.getValue().getGymTrainer().getTrainerId() != gClass
									.getClassTrainer2().getTrainerId())) {
						continue;
					}

					if (chbFromDate.isSelected()) {
						String[] date = tfFromDate.getText().split("-");
						DateTime fromDate = new DateTime(Integer.valueOf(date[2]), Integer.valueOf(date[1]),
								Integer.valueOf(date[0]), 0, 0, 0, 0);
						if (!gClass.getStartTime().isAfter(fromDate.getMillis())) {
							continue;
						}
					}
					if (chbToDate.isSelected()) {
						String[] date = tfToDate.getText().split("-");
						DateTime toDate = new DateTime(Integer.valueOf(date[2]), Integer.valueOf(date[1]),
								Integer.valueOf(date[0]), 23, 59, 59, 999);
						if (!gClass.getStartTime().isBefore(toDate.getMillis())) {
							continue;
						}
					}
					result.add(gClass);
				}
			}

			int totalNumber = 0;
			for (GymClass gClass : result) {
				totalNumber += gClass.getDuration();
			}
			System.out.println("Duration: " + totalNumber);
			double rateOfPay = cmbSearchTrainer.getValue().getGymTrainer().getRateOfPay();
			if (chbPension.isSelected()) {
				rateOfPay = Double.parseDouble(tfPension.getText());
			}
			System.out.println("Rate of pay: " + rateOfPay);
			double pension = (totalNumber / 60) * rateOfPay;
			tfPayResult.setText(String.valueOf(pension));

			reportTable.setItems(FXCollections.observableList(result));
		} else {
			GUIHelper.displayWarning("Do wyliczenia wypłaty, należy podać trenera i zakres dni.");
		}
	}

	@FXML
	protected void handleSearch(ActionEvent event) {
		tfSearchResult.setText("");
		ArrayList<GymClass> allClasses = (ArrayList<GymClass>) DataManager.getInstance().getGymClassManager().getClasses(null);
		ArrayList<GymClass> result = new ArrayList<GymClass>();
		for (GymClass gClass : allClasses) {
			if (chbTrainer.isSelected()) {
				if (cmbSearchTrainer.getValue().getGymTrainer().getTrainerId() != gClass.getClassTrainer1().getTrainerId()) {
					if (gClass.getClassTrainer2() != null) {
						if (cmbSearchTrainer.getValue().getGymTrainer().getTrainerId() != gClass.getClassTrainer2()
								.getTrainerId()) {
							continue;
						}
					} else {
						continue;
					}
				}
			}
			if (chbType.isSelected()) {
				if (cmbSearchType.getValue().getClassType().getClassId() != gClass.getClassType().getClassId()) {
					continue;
				}
			}
			if (chbDay.isSelected()) {
				if (cmbSearchDay.getValue().compareToIgnoreCase(ManagerUtils.intToWeekDay(gClass.getStartTime().getDayOfWeek())) != 0) {
					continue;
				}
			}
			if (chbFromDate.isSelected()) {
				String[] date = tfFromDate.getText().split("-");
				DateTime fromDate = new DateTime(Integer.valueOf(date[2]), Integer.valueOf(date[1]), Integer.valueOf(date[0]), 0,
						0, 0, 0);
				if (!gClass.getStartTime().isAfter(fromDate.getMillis())) {
					continue;
				}
			}
			if (chbToDate.isSelected()) {
				String[] date = tfToDate.getText().split("-");
				DateTime toDate = new DateTime(Integer.valueOf(date[2]), Integer.valueOf(date[1]), Integer.valueOf(date[0]) + 1,
						0, 0, 0, 0);
				if (!gClass.getStartTime().isBefore(toDate.getMillis())) {
					continue;
				}
			}
			if (chbFromHour.isSelected()) {
				if (Integer.valueOf(cmbFromHour.getValue()) * 60 + Integer.valueOf(cmbFromMinute.getValue()) > (gClass
						.getStartTime().getHourOfDay() * 60 + gClass.getStartTime().getMinuteOfHour())) {
					continue;
				}
			}
			if (chbToHour.isSelected()) {
				if (Integer.valueOf(cmbToHour.getValue()) * 60 + Integer.valueOf(cmbToMinute.getValue()) < (gClass.getStartTime()
						.getHourOfDay() * 60 + gClass.getStartTime().getMinuteOfHour())) {
					continue;
				}
			}
			result.add(gClass);
		}
		int totalNumber = 0;
		for (GymClass gClass : result) {
			totalNumber += gClass.getParticipants();
		}
		System.out.println("Total number: " + totalNumber);
		tfSearchResult.setText(String.valueOf(totalNumber));
		ObservableList<GymClass> data = FXCollections.observableArrayList(result);
		for (GymClass gymClass : data) {
			System.out.println(gymClass.getClassId());
		}
		reportTable.setItems(data);
	}

	public void displayOverview(GymClass gymClass) {
		state.setSelectedClass(gymClass);
		lblOverviewType.setText(gymClass.getClassType().getName());
		lblOverviewRoom.setText(gymClass.getClassRoom().getName());
		System.out.println(state.getSelectedClass().getClassTrainer1().getName() + ", " + state.getSelectedClass().getClassTrainer1().getSurrname());
		lblOverviewTrainer.setText(state.getSelectedClass().getClassTrainer1().getName() + ", " + state.getSelectedClass().getClassTrainer1().getSurrname());
		if (state.getSelectedClass().getClassTrainer2() != null) {
			lblTrainer2.setDisable(false);
			System.out.println(state.getSelectedClass().getClassTrainer2().getName() + ", " + state.getSelectedClass().getClassTrainer2().getSurrname());
			lblOverviewTrainer1.setText(state.getSelectedClass().getClassTrainer2().getName() + ", " + state.getSelectedClass().getClassTrainer2().getSurrname());
		}

		lblOverviewDay.setText(ManagerUtils.intToWeekDay(gymClass.getStartTime().getDayOfWeek()));
		lblOverviewHour.setText(ManagerUtils.parseHour(gymClass.getStartTime()));
		lblOverviewEnrolled.setText(String.valueOf(gymClass.getParticipants()));
		btnEditClass.setDisable(false);
		btnRemoveClass.setDisable(false);
	}

	public AnchorPane getCallendarPane() {
		return callendarPane;
	}

	public static Object getState() {
		return state;
	}
}
