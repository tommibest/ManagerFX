/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tzaras.fitness.manager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import pl.tzaras.fitness.manager.db.DataManager;
import pl.tzaras.fitness.manager.db.GymClassManager;
import pl.tzaras.fitness.manager.db.data.GymClass;
import pl.tzaras.fitness.manager.db.data.GymClassType;
import pl.tzaras.fitness.manager.db.data.GymRoom;
import pl.tzaras.fitness.manager.db.data.GymTrainer;
import pl.tzaras.fitness.manager.utils.ManagerUtils;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author tommi
 */
public class ManagerController implements Initializable {
    
	@FXML private TextField txtFieldInstructorName;
	@FXML private TextField txtFieldInstructorSurrname;
	@FXML private TextField tfClassTypeName;
	@FXML private TextField tfRoomName;
	@FXML private TextField tfDuration;
	
	@FXML private Button btnAddClass;
	@FXML private Button btnAddInstructor;
	@FXML private Button btnAddRoom;
	
	@FXML private ComboBox<TrainerWrapper> instructorCombo;
	@FXML private ComboBox<TypeWrapper> classTypeCombo;
	@FXML private ComboBox<String> daysCombo;
	@FXML private ComboBox<RoomWrapper> roomCombo;
	@FXML private ComboBox<String> cmbHours;
	@FXML private ComboBox<String> cmbMinutes;
	
	@FXML private TableView<GymRoom> tblRoom;
	@FXML private TableColumn<GymRoom, String> colRoomName;
	
	@FXML private TableView<GymClassType> tblClassType;
	@FXML private TableColumn<GymClassType, String> colClassType;
	
	@FXML private TableView<GymTrainer> tblTrainers;
	@FXML private TableColumn<GymTrainer, String> colName;
	@FXML private TableColumn<GymTrainer, String> colSurrname;
	
	@FXML private TableView<GymClass> tblClasses;
	@FXML private TableColumn<GymClass, Long> colClassId;
	@FXML private TableColumn<GymClass, String> colCType;
	@FXML private TableColumn<GymClass, String> colClassTrainer;
	@FXML private TableColumn<GymClass, String> colClassRoom;
	@FXML private TableColumn<GymClass, String> colClassDay;
	@FXML private TableColumn<GymClass, String> colClassHour;
	@FXML private TableColumn<GymClass, Integer> colParticipants;
	@FXML private TableColumn<GymClass, Long> colClassDuration; 
	
	@FXML private TableView<WeekEntry> tblCalendar;
	@FXML private TableColumn<WeekEntry, String> colHours;
	@FXML private TableColumn<WeekEntry, String> colMonday;
	@FXML private TableColumn<WeekEntry, String> colTuesday;
	@FXML private TableColumn<WeekEntry, String> colWendesday;
	@FXML private TableColumn<WeekEntry, String> colThursday;
	@FXML private TableColumn<WeekEntry, String> colFriday;
	@FXML private TableColumn<WeekEntry, String> colSaturday;
	@FXML private TableColumn<WeekEntry, String> colSunday;
	
	@FXML private Button btnNextWeek;
	@FXML private Button btnPrevWeek;
	@FXML private Label lblCurrentWeek;
	
	@FXML private CheckBox chbTrainer;
	@FXML private CheckBox chbType;
	@FXML private CheckBox chbDay;
	@FXML private CheckBox chbFromDate;
	@FXML private CheckBox chbToDate;
	@FXML private CheckBox chbFromHour;
	@FXML private CheckBox chbToHour;
	
	@FXML private ComboBox<TrainerWrapper> cmbSearchTrainer;
    @FXML private ComboBox<TypeWrapper> cmbSearchType;
    @FXML private ComboBox<String> cmbSearchDay;
    @FXML private TextField tfFromDate;
    @FXML private TextField tfToDate;
    @FXML private ComboBox<String> cmbFromHour;
    @FXML private ComboBox<String> cmbFromMinute;
    @FXML private ComboBox<String> cmbToHour;
    @FXML private ComboBox<String> cmbToMinute;
    @FXML private TextField tfToHour;
    @FXML private Button btnSearch;
    
    @FXML private TextField tfSearchResult;
	
	DateTime monday;
	DateTime sunday;
	
	private boolean populate = false;
	
	@FXML protected void addInstructor(MouseEvent arg0) {
    	if ( !txtFieldInstructorName.getText().isEmpty() && !txtFieldInstructorSurrname.getText().isEmpty() ) {
    		DataManager.getInstance().getGymTrainerManager().saveTrainer(txtFieldInstructorName.getText(), txtFieldInstructorSurrname.getText());
            System.out.println("Adding instructor: "+ txtFieldInstructorName.getText()  + ", " + txtFieldInstructorSurrname.getText() );
            txtFieldInstructorName.setText("");
            txtFieldInstructorSurrname.setText("");
            DataManager.getInstance().getGymTrainerManager().initializeCombo(instructorCombo);
            
            ObservableList<GymTrainer> data = FXCollections.observableArrayList(DataManager
    				.getInstance().getGymTrainerManager().getInstructors());
    		for (GymTrainer trainer : data) {
    			System.out.println(trainer.getName() + ", " + trainer.getSurrname());
    		}
    		tblTrainers.setItems(data);
    	} else {
        	System.err.println("Name or surrname of instructor is empty");
    	}
    }
	
	@FXML protected void removeInstructor(MouseEvent arg0) {
		DataManager.getInstance().getGymTrainerManager().delete(tblTrainers.getSelectionModel().getSelectedItem());
        DataManager.getInstance().getGymTrainerManager().initializeCombo(instructorCombo);
        
        ObservableList<GymTrainer> data = FXCollections.observableArrayList(DataManager
				.getInstance().getGymTrainerManager().getInstructors());
		for (GymTrainer trainer : data) {
			System.out.println(trainer.getName() + ", " + trainer.getSurrname());
		}
		tblTrainers.setItems(data);
	}

	@FXML protected void removeClassType(MouseEvent arg0) {
		DataManager.getInstance().getGymClassTypeManager().delete(tblClassType.getSelectionModel().getSelectedItem());
        DataManager.getInstance().getGymClassTypeManager().initializeCombo(classTypeCombo);
        
        ObservableList<GymClassType> data = FXCollections.observableArrayList(DataManager
				.getInstance().getGymClassTypeManager().getClassTypes());
		for (GymClassType cType : data) {
			System.out.println(cType.getName());
		}
		tblClassType.setItems(data);
	}	

	@FXML protected void removeRoom(MouseEvent arg0) {
		DataManager.getInstance().getGymRoomManager().delete(tblRoom.getSelectionModel().getSelectedItem());
        DataManager.getInstance().getGymRoomManager().initializeCombo(roomCombo);
        
        ObservableList<GymRoom> data = FXCollections.observableArrayList(DataManager
				.getInstance().getGymRoomManager().getRooms());
		for (GymRoom room : data) {
			System.out.println(room.getName());
		}
		tblRoom.setItems(data);
	}
	
    @FXML protected void addClassType(MouseEvent arg0) {
    	if ( !tfClassTypeName.getText().isEmpty() ) {
    		System.out.println("Adding class: " + tfClassTypeName.getText());
    		DataManager.getInstance().getGymClassTypeManager().saveClass(tfClassTypeName.getText());
    		tfClassTypeName.setText("");
    		DataManager.getInstance().getGymClassTypeManager().initializeCombo(classTypeCombo);
    		
    		ObservableList<GymClassType> classTypes = FXCollections.observableArrayList(DataManager
    				.getInstance().getGymClassTypeManager().getClassTypes());
    		for (GymClassType classType : classTypes) {
    			System.out.println(classType.getName());
    		}
    		tblClassType.setItems(classTypes);
    		
    	} else {
    		System.err.println("Cannot add empty class type.");
    	}
    }
    
    @FXML protected void addRoom(MouseEvent arg0) {
    	if ( !tfRoomName.getText().isEmpty() ) {
    		System.out.println("Adding class: " + tfRoomName.getText());
    		DataManager.getInstance().getGymRoomManager().saveRoom(tfRoomName.getText());
    		tfRoomName.setText("");
    		DataManager.getInstance().getGymRoomManager().initializeCombo(roomCombo);
    		
    		ObservableList<GymRoom> rooms = FXCollections.observableArrayList(DataManager
    				.getInstance().getGymRoomManager().getRooms());
    		for (GymRoom room : rooms) {
    			System.out.println(room.getName());
    		}
    		tblRoom.setItems(rooms);
    		
    	} else {
    		System.err.println("Cannot add empty class type.");
    	}
    }    
    
    @FXML protected void addClass(MouseEvent arg0) {
    	GymClassManager mngr = DataManager.getInstance().getGymClassManager();
    	System.out.println("Start time: "+ cmbHours.getValue() + ":" + cmbMinutes.getValue());
    	
    	DateTime startTime = monday
    			.withDayOfWeek(ManagerUtils.dayToInt(daysCombo.getValue()))
    			.withHourOfDay(Integer.valueOf(cmbHours.getValue()))
    			.withMinuteOfHour(Integer.valueOf(cmbMinutes.getValue()));
    	
    	System.out.println("Start time: " + startTime.toString());
    	mngr.saveClass( roomCombo.getValue().getRoom(), 
    			startTime, 
    			0, 
    			instructorCombo.getValue().getGymTrainer(), 
    			classTypeCombo.getValue().getClassType(), 
    			Long.valueOf(tfDuration.getText()));

    	populateCurrentWeekData();
    	
    }

	public void initialize(URL location, ResourceBundle resources) {
		if (populate) {
			populateTestData();
		}
		initializeMainTab();
		initializeManagementTab();
	}

	private void initializeMainTab() {
		DateTime now = new DateTime();
		monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
		sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
		displaySelectedWeek();
		colHours.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("hour"));
		colMonday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("monday"));
		colTuesday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("tuesday"));
		colWendesday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("wendesday"));
		colThursday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("thursday"));
		colFriday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("friday"));
		colSaturday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("saturday"));
		colSunday.setCellValueFactory(new PropertyValueFactory<WeekEntry, String>("sunday"));
		ObservableList<WeekEntry> entries = FXCollections.observableArrayList();
		for (int i=0; i<24;i++) {
			entries.add(new WeekEntry(i+":00","","","","","","",""));
			entries.add(new WeekEntry(i+":30","","","","","","",""));
		}
		tblCalendar.setItems(entries);
		
		colClassId.setCellValueFactory(new PropertyValueFactory<GymClass, Long>("classId"));
		colCType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {

		    public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
		        if (p.getValue() != null) {
		            return new SimpleStringProperty(p.getValue().getClassType().getName());
		        } else {
		            return new SimpleStringProperty("<no name>");
		        }
		    }
		});
		
		colClassTrainer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
		
			public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
		        if (p.getValue() != null) {
		        	System.out.println(p.getValue().getStartTime());
		            return new SimpleStringProperty(p.getValue().getClassTrainer().getName() + ", " + p.getValue().getClassTrainer().getSurrname());
		        } else {
		            return new SimpleStringProperty("<no name>");
		        }
		    }
		});
		
		colClassRoom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {

		    public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
		        if (p.getValue() != null) {
		            return new SimpleStringProperty(p.getValue().getClassRoom().getName());
		        } else {
		            return new SimpleStringProperty("<no name>");
		        }
		    }
		});
		
		colClassDay.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
		    
			public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
		        if (p.getValue() != null) {
		    		return new SimpleStringProperty( ManagerUtils.intToWeekDay(p.getValue().getStartTime().getDayOfWeek()) );
		        } else {
		            return new SimpleStringProperty("<no name>");
		        }
		    }
		});
		
		colClassHour.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GymClass, String>, ObservableValue<String>>() {
		    
			public ObservableValue<String> call(TableColumn.CellDataFeatures<GymClass, String> p) {
		        if (p.getValue() != null) {
		        	DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
		    		return new SimpleStringProperty( fmt.print(p.getValue().getStartTime()) );
		        } else {
		            return new SimpleStringProperty("<no name>");
		        }
		    }
		});

		colClassDuration.setCellValueFactory(new PropertyValueFactory<GymClass, Long>("duration"));
		colParticipants.setCellValueFactory(new PropertyValueFactory<GymClass, Integer>("participants"));
		
		populateCurrentWeekData();
		
		ManagerUtils.fillWithWeekDays(daysCombo);
		ManagerUtils.fillWithHours(cmbHours);
		ManagerUtils.fillWithMinutes(cmbMinutes);
		
		DataManager.getInstance().getGymTrainerManager().initializeCombo(instructorCombo);
		DataManager.getInstance().getGymClassTypeManager().initializeCombo(classTypeCombo);
		DataManager.getInstance().getGymRoomManager().initializeCombo(roomCombo);
		initializeSearchPanel();
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

	private void populateCurrentWeekData() {
		ObservableList<GymClass> data = FXCollections.observableArrayList(DataManager
				.getInstance().getGymClassManager().getClasses(monday,sunday));
		for (GymClass gymClass : data) {
			System.out.println("Getting: " + gymClass.getClassId() + ", " + gymClass.getClassTrainer().getName());
		}
		tblClasses.setItems(data);
	}

	private void setColumnsHeaders() {
		colMonday.setText("Pon "+monday.getDayOfMonth()+"/"+monday.getMonthOfYear());
		colTuesday.setText("Wt "+monday.plusDays(1).getDayOfMonth()+"/"+monday.plusDays(1).getMonthOfYear());
		colWendesday.setText("Śr "+monday.plusDays(2).getDayOfMonth()+"/"+monday.plusDays(2).getMonthOfYear());
		colThursday.setText("Czw "+monday.plusDays(3).getDayOfMonth()+"/"+monday.plusDays(3).getMonthOfYear());
		colFriday.setText("Pt "+monday.plusDays(4).getDayOfMonth()+"/"+monday.plusDays(4).getMonthOfYear());
		colSaturday.setText("Sb "+monday.plusDays(5).getDayOfMonth()+"/"+monday.plusDays(5).getMonthOfYear());
		colSunday.setText("Niedz "+monday.plusDays(6).getDayOfMonth()+"/"+monday.plusDays(6).getMonthOfYear());
	}

	private void initializeManagementTab() {
		colName.setCellValueFactory(new PropertyValueFactory<GymTrainer, String>("name"));
		colSurrname.setCellValueFactory(new PropertyValueFactory<GymTrainer, String>("surrname"));
		ObservableList<GymTrainer> data = FXCollections.observableArrayList(DataManager
				.getInstance().getGymTrainerManager().getInstructors());
		for (GymTrainer trainer : data) {
			System.out.println(trainer.getName() + ", " + trainer.getSurrname());
		}
		tblTrainers.setItems(data);
		
		colClassType.setCellValueFactory(new PropertyValueFactory<GymClassType, String>("name"));
		ObservableList<GymClassType> classTypes = FXCollections.observableArrayList(DataManager
				.getInstance().getGymClassTypeManager().getClassTypes());
		for (GymClassType classType : classTypes) {
			System.out.println(classType.getName());
		}
		tblClassType.setItems(classTypes);
		
		colRoomName.setCellValueFactory(new PropertyValueFactory<GymRoom, String>("name"));
		ObservableList<GymRoom> rooms = FXCollections.observableArrayList(DataManager.getInstance().getGymRoomManager().getRooms());
		for (GymClassType classType : classTypes) {
			System.out.println(classType.getName());
		}
		tblRoom.setItems(rooms);
	}

	private void populateTestData() {
		DataManager.getInstance().getGymTrainerManager().saveTrainer("Szymon", "Wesołowski");
		DataManager.getInstance().getGymTrainerManager().saveTrainer("Trener", "Fitness");
		DataManager.getInstance().getGymTrainerManager().saveTrainer("Trener", "Mental");
		
		DataManager.getInstance().getGymClassTypeManager().saveClass("FITNESS");
		DataManager.getInstance().getGymClassTypeManager().saveClass("ZUMBA");
		DataManager.getInstance().getGymClassTypeManager().saveClass("GRID");
		DataManager.getInstance().getGymClassTypeManager().saveClass("HAPPY Hours");
		
		DataManager.getInstance().getGymRoomManager().saveRoom("Sala FITNESS");
		DataManager.getInstance().getGymRoomManager().saveRoom("Sala ROWEROWA");
		DataManager.getInstance().getGymRoomManager().saveRoom("Sala MENTAL");
		DataManager.getInstance().getGymRoomManager().saveRoom("Siłownia");
		DataManager.getInstance().getGymRoomManager().saveRoom("OUTODOOR");
	}
       
	@FXML protected void prevWeekAction(MouseEvent arg0) {
		monday = monday.minusWeeks(1);
		sunday = sunday.minusWeeks(1);
		displaySelectedWeek();
		setColumnsHeaders();
		populateCurrentWeekData();
	}
	
	@FXML protected void nextWeekAction(MouseEvent arg0) {
		monday = monday.plusWeeks(1);
		sunday = sunday.plusWeeks(1);
		displaySelectedWeek();
		setColumnsHeaders();
		populateCurrentWeekData();
	}

	private void displaySelectedWeek() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		lblCurrentWeek.setText(fmt.print(monday) + " - " + fmt.print(sunday));
	}
	
	@FXML protected void editClass(MouseEvent event) {
		Stage stage = new Stage();
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDialog.fxml"));
			root = (Parent) loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Edytuj zajęcia");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(
					((Node)event.getSource()).getScene().getWindow() );
			EditDialogController controller = (EditDialogController)loader.getController();
			controller.setGymClass(tblClasses.getSelectionModel().getSelectedItem());
			controller.setEditDialogStage(stage);
			stage.showAndWait();
			DataManager.getInstance().getGymClassManager().updateClass(controller.getGymClass());
			populateCurrentWeekData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML protected void removeClass(MouseEvent event) {
		DataManager.getInstance().getGymClassManager().deleteClass(tblClasses.getSelectionModel().getSelectedItem());
		populateCurrentWeekData();
	}
	
	@FXML protected void enableWidget(ActionEvent event){
		
		CheckBox source = (CheckBox)event.getSource();
		if ( source.isSelected() ){
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
			}
		}
		
	}
	
	@FXML protected void handleSearch(MouseEvent event) {
		tfSearchResult.setText( "" );
		ArrayList<GymClass> allClasses = (ArrayList<GymClass>) DataManager.getInstance().getGymClassManager().getClasses();
		ArrayList<GymClass> result = new ArrayList<GymClass>();
		for (GymClass gClass : allClasses) { 
			if ( chbTrainer.isSelected() ) {
				if ( cmbSearchTrainer.getValue().getGymTrainer().getTrainerId() != gClass.getClassTrainer().getTrainerId() ) {
					continue;
				}
			} 
			if (chbType.isSelected()) {
				if (cmbSearchType.getValue().getClassType().getClassId() != gClass.getClassType().getClassId()) {
					continue;
				}
			}
			if (chbDay.isSelected()) {
				if (cmbSearchDay.getValue().compareToIgnoreCase(ManagerUtils.intToWeekDay(gClass.getStartTime().getDayOfWeek())) != 0){
					continue;
				}
			}
			if (chbFromDate.isSelected()) {
				String [] date = tfFromDate.getText().split("-");
				DateTime fromDate = new DateTime(Integer.valueOf(date[2]),Integer.valueOf(date[1]),Integer.valueOf(date[0]),0,0,0,0);
				if ( !gClass.getStartTime().isAfter(fromDate.getMillis()) ) {
					continue;
				}	
			}
			if (chbToDate.isSelected()) {
				String [] date = tfToDate.getText().split("-");
				DateTime toDate = new DateTime(Integer.valueOf(date[2]),Integer.valueOf(date[1]),Integer.valueOf(date[0])+1,0,0,0,0);
				if ( !gClass.getStartTime().isBefore(toDate.getMillis()) ) {
					continue;
				}
			}
			if ( chbFromHour.isSelected() ) {
				if ( Integer.valueOf(cmbFromHour.getValue())*60+Integer.valueOf(cmbFromMinute.getValue()) >
						(gClass.getStartTime().getHourOfDay()*60+gClass.getStartTime().getMinuteOfHour()) ) {
					continue;
				}
			}
			if ( chbToHour.isSelected() ) {
				if ( Integer.valueOf(cmbToHour.getValue())*60+Integer.valueOf(cmbToMinute.getValue()) <
				(gClass.getStartTime().getHourOfDay()*60+gClass.getStartTime().getMinuteOfHour()) ) {
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
		tfSearchResult.setText( String.valueOf(totalNumber) );
	}
}
