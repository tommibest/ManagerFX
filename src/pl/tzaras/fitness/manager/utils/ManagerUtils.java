package pl.tzaras.fitness.manager.utils;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import javafx.scene.control.ComboBox;

public class ManagerUtils {

	public static final String SUNDAY_pl = "Niedziela";

	public static final String SATURDAY_pl = "Sobota";

	public static final String FRIDAY_pl = "Piatek";

	public static final String THURSDAY_pl = "Czwartek";

	public static final String WEDNESDAY_pl = "Sroda";

	public static final String TUESDAY_pl = "Wtorek";

	public static final String MONDAY_pl = "Poniedzialek";

	public static final int CONSTRAINT_VIOLATTION = 1;
	public static final int SUCCESS = 0;
	public static final int UNKNOWN_FAILURE = 2;

	public static int startHour = 8;
	public static int endHour = 22;

	public static void fillWithHours(ComboBox<String> combo) {
		ArrayList<String> hours = new ArrayList<String>();
		for (int i=startHour; i<endHour ; i++) {
			if (i<10) hours.add("0"+String.valueOf(i));
			else hours.add(String.valueOf(i));
		}
		combo.getItems().setAll(hours);
	}

	public static void fillWithMinutes(ComboBox<String> combo) {
		ArrayList<String> minutes = new ArrayList<String>();
		for (int i=0; i<60; i+=5) {
			if (i<10) minutes.add("0"+String.valueOf(i));
			else minutes.add(String.valueOf(i));
		}	
		combo.getItems().setAll(minutes);
	}

	public static void fillWithWeekDays(ComboBox<String> combo) {
		combo.getItems().setAll(ManagerUtils.MONDAY_pl, 
				ManagerUtils.TUESDAY_pl, 
				ManagerUtils.WEDNESDAY_pl, 
				ManagerUtils.THURSDAY_pl, 
				ManagerUtils.FRIDAY_pl, 
				ManagerUtils.SATURDAY_pl, 
				ManagerUtils.SUNDAY_pl);
	}

	public static String intToWeekDay(int dayOfWeek) {
		switch (dayOfWeek) {
		case DateTimeConstants.MONDAY    : return MONDAY_pl;
		case DateTimeConstants.TUESDAY   : return TUESDAY_pl;
		case DateTimeConstants.WEDNESDAY : return WEDNESDAY_pl;
		case DateTimeConstants.THURSDAY  : return THURSDAY_pl;
		case DateTimeConstants.FRIDAY    : return FRIDAY_pl;
		case DateTimeConstants.SATURDAY  : return SATURDAY_pl;
		case DateTimeConstants.SUNDAY    : return SUNDAY_pl;
		}
		return "";
	}
	
	public static int dayToInt(String dayOfWeek) {
		if (dayOfWeek.compareToIgnoreCase(MONDAY_pl) == 0) return DateTimeConstants.MONDAY;
		else if (dayOfWeek.compareToIgnoreCase(TUESDAY_pl) == 0) return DateTimeConstants.TUESDAY;
		else if (dayOfWeek.compareToIgnoreCase(WEDNESDAY_pl) == 0) return DateTimeConstants.WEDNESDAY;
		else if (dayOfWeek.compareToIgnoreCase(THURSDAY_pl) == 0) return DateTimeConstants.THURSDAY;
		else if (dayOfWeek.compareToIgnoreCase(FRIDAY_pl) == 0) return DateTimeConstants.FRIDAY;
		else if (dayOfWeek.compareToIgnoreCase(SATURDAY_pl) == 0) return DateTimeConstants.SATURDAY;
		else if (dayOfWeek.compareToIgnoreCase(SUNDAY_pl) == 0) return DateTimeConstants.SUNDAY;
		return -1;
	}

	public static String parseHour(DateTime dateTime) {
		return (dateTime.getHourOfDay()<10?"0"+dateTime.getHourOfDay():dateTime.getHourOfDay())+
				":"+
				(dateTime.getMinuteOfHour()<10?"0"+dateTime.getMinuteOfHour():dateTime.getMinuteOfHour());
	}

}
