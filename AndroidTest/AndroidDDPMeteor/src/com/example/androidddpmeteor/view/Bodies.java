package com.example.androidddpmeteor.view;

import java.io.Serializable;

public class Bodies implements Serializable{
	public String name;
	public int price;
	public boolean ischecked;
	
	public boolean getisIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	
	public Bodies() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Bodies [name=" + name + ", price=" + price + ", ischecked="
				+ ischecked + "]";
	}
	public Bodies(String name, int price, boolean ischecked) {
		super();
		this.name = name;
		this.price = price;
		this.ischecked = ischecked;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
