package com.example.androidddpmeteor.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExamPart implements Serializable{
	public String name;
	public ArrayList<Bodies> boides;
	@Override
	public String toString() {
		return "ExamPart [name=" + name + ", boides=" + boides + "]";
	}
	public ExamPart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamPart(String name, ArrayList<Bodies> boides) {
		super();
		this.name = name;
		this.boides = boides;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Bodies> getBoides() {
		return boides;
	}
	public void setBoides(ArrayList<Bodies> boides) {
		this.boides = boides;
	}

}
