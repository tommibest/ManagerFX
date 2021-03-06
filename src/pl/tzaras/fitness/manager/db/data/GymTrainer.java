package pl.tzaras.fitness.manager.db.data;

// Generated Nov 17, 2013 7:59:56 PM by Hibernate Tools 4.0.0

/**
 * GymTrainer generated by hbm2java
 */
public class GymTrainer implements java.io.Serializable {

	private long trainerId;
	private String name;
	private String surrname;
	private double rateOfPay;

	public GymTrainer() {
	}

	public GymTrainer(String name, String surrname, double rateOfPay) {
		this.name = name;
		this.surrname = surrname;
		this.rateOfPay = rateOfPay;
	}

	public long getTrainerId() {
		return this.trainerId;
	}

	public void setTrainerId(long trainerId) {
		this.trainerId = trainerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurrname() {
		return this.surrname;
	}

	public void setSurrname(String surrname) {
		this.surrname = surrname;
	}

	public double getRateOfPay() {
		return this.rateOfPay;
	}

	public void setRateOfPay(double rateOfPay) {
		this.rateOfPay = rateOfPay;
	}

}
