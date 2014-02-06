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
	/**
	 * @param fTasteName
	 * 
	 * @author 2014-1-20
	 */
	public FoodTaste(String fTasteName) {
		super();
		this.fTasteName = fTasteName;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public FoodTaste() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FoodTaste [fTasteId=" + fTasteId + ", fTasteName=" + fTasteName
				+ "]";
	}
	
	
}
