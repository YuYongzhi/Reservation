package com.reservation.pojo;

public class Preferential {
	private int pId;
	private Foods food;
	private double pPrice;
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public Foods getFood() {
		return food;
	}
	public void setFood(Foods food) {
		this.food = food;
	}
	public double getpPrice() {
		return pPrice;
	}
	public void setpPrice(double pPrice) {
		this.pPrice = pPrice;
	}
	public Preferential() {
		super();
	}
	public Preferential(Foods food, double pPrice) {
		super();
		this.food = food;
		this.pPrice = pPrice;
	}
	@Override
	public String toString() {
		return "Preferential [pId=" + pId + ", food=" + food + ", pPrice="
				+ pPrice + "]";
	}
	
}
