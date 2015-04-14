package com.example.androidddpmeteor.view;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Order [doctor_id=" + doctor_id + ", name=" + name + ", exam="
				+ exam + ", patient_name=" + patient_name + ", patient_phone="
				+ patient_phone + ", remark=" + remark + ", price=" + price
				+ ", hospital=" + hospital + ", bodies="
				+ bodies + "]";
	}

	public String getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getPatient_phone() {
		return patient_phone;
	}
	public void setPatient_phone(String patient_phone) {
		this.patient_phone = patient_phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String doctor_id;
	public String name;
	public String exam;
	public String patient_name;
	public String patient_phone;
	public String remark;
	public int price;
	public Order(String doctor_id, String name, String exam,
			String patient_name, String patient_phone, String remark,
			int price, int state, String hospital, ArrayList<Bodies> bodies) {
		super();
		this.doctor_id = doctor_id;
		this.name = name;
		this.exam = exam;
		this.patient_name = patient_name;
		this.patient_phone = patient_phone;
		this.remark = remark;
		this.price = price;
		this.hospital = hospital;
		this.bodies = bodies;
	}
	public String hospital;
	public ArrayList<Bodies> bodies=new ArrayList<Bodies>();
	public ArrayList<Bodies> getBodies() {
		return bodies;
	}

	public void setBodies(ArrayList<Bodies> bodies) {
		this.bodies = bodies;
	}
}
