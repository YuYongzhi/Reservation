package com.reservation.pojo;

public class FoodTaste {
	private int fTasteId;
	private String fTasteName;
	public int getfTasteId() {
		return fTasteId;
	}
	public void setfTasteId(int fTasteId) {
		this.fTasteId = fTasteId;
	}
	public String getfTasteName() {
		return fTasteName;
	}
	public void setfTasteName(String fTasteName) {
		this.fTasteName = fTasteName;
	}
	@Override
	public String toString() {
		return "FoodTaste [fTasteId=" + fTasteId + ", fTasteName=" + fTasteName
				+ "]";
	}
	public FoodTaste(String fTasteName) {
		super();
		this.fTasteName = fTasteName;
	}
	public FoodTaste() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
