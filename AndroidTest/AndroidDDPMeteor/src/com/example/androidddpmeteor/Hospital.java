package com.example.androidddpmeteor;

import java.io.Serializable;

public class Hospital implements Serializable{
	public String hospital_id;
	public String hospital_name;
	public String exam_part_id;
	@Override
	public String toString() {
		return "Hospital [hospital_id=" + hospital_id + ", hospital_name="
				+ hospital_name + ", exam_part_id=" + exam_part_id + "]";
	}
	public Hospital() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Hospital(String hospital_id, String hospital_name,
			String exam_part_id) {
		super();
		this.hospital_id = hospital_id;
		this.hospital_name = hospital_name;
		this.exam_part_id = exam_part_id;
	}
	public String getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(String hospital_id) {
		this.hospital_id = hospital_id;
	}
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	public String getExam_part_id() {
		return exam_part_id;
	}
	public void setExam_part_id(String exam_part_id) {
		this.exam_part_id = exam_part_id;
	}

}
