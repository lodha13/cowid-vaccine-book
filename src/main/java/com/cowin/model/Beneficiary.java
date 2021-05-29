package com.cowin.model;
import java.util.List;

public class Beneficiary {
	private String beneficiary_reference_id;
	private String name;
	private int birth_year;
	private String gender;
	private int mobile_number;
	private String photo_id_type;
	private String photo_id_number;
	private String comorbidity_ind;
	private String vaccination_status;
	private String vaccine;
	private String dose1_date;
	private String dose2_date;
	private List<Appointment> appointments;

	public String getBeneficiary_reference_id() {
		return beneficiary_reference_id;
	}

	public void setBeneficiary_reference_id(String beneficiary_reference_id) {
		this.beneficiary_reference_id = beneficiary_reference_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(int mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getPhoto_id_type() {
		return photo_id_type;
	}

	public void setPhoto_id_type(String photo_id_type) {
		this.photo_id_type = photo_id_type;
	}

	public String getPhoto_id_number() {
		return photo_id_number;
	}

	public void setPhoto_id_number(String photo_id_number) {
		this.photo_id_number = photo_id_number;
	}

	public String getComorbidity_ind() {
		return comorbidity_ind;
	}

	public void setComorbidity_ind(String comorbidity_ind) {
		this.comorbidity_ind = comorbidity_ind;
	}

	public String getVaccination_status() {
		return vaccination_status;
	}

	public void setVaccination_status(String vaccination_status) {
		this.vaccination_status = vaccination_status;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	public String getDose1_date() {
		return dose1_date;
	}

	public void setDose1_date(String dose1_date) {
		this.dose1_date = dose1_date;
	}

	public String getDose2_date() {
		return dose2_date;
	}

	public void setDose2_date(String dose2_date) {
		this.dose2_date = dose2_date;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
}