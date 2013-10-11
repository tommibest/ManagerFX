package pl.tzaras.fitness.manager;

public class WeekEntry {
	
	private String hour;
	private String monday;
	private String tuesday;
	private String wendesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;
	
	public WeekEntry(String hour, String monday, String tuesday,
			String wendesday, String thursday, String friday, String saturday,
			String sunday) {
		super();
		this.hour = hour;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wendesday = wendesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.monday = monday;
	}
	public String getTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	public String getWendesday() {
		return wendesday;
	}
	public void setWendesday(String wendesday) {
		this.wendesday = wendesday;
	}
	public String getThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.thursday = thursday;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}
	public String getSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}
	public String getSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.sunday = sunday;
	} 

}
