package com.example.androidddpmeteor.view;

import java.io.Serializable;

public class IsChecked implements Serializable{
	private String name;
	private boolean isChecked;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public IsChecked(String name, boolean isChecked) {
		super();
		this.name = name;
		this.isChecked = isChecked;
	}
	public IsChecked() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "IsChecked [name=" + name + ", isChecked=" + isChecked + "]";
	}
	

}
