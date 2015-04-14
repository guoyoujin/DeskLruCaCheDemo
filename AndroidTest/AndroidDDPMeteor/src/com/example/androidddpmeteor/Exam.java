package com.example.androidddpmeteor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exam implements Serializable{
	public String name;
	public String _id;
	public List<Hospital>hospital=new ArrayList<Hospital>();
	public List<Hospital> getHospital() {
		return hospital;
	}
	public void setHospital(List<Hospital> hospital) {
		this.hospital = hospital;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Exam(String name, String _id, Hospital hospital) {
		super();
		this.name = name;
		this._id = _id;
	}
	public Exam() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
