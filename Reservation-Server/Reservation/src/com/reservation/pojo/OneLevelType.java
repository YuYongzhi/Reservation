package com.reservation.pojo;

public class OneLevelType {
	private int olId;
	private String olName;
	public int getOlId() {
		return olId;
	}
	public void setOlId(int olId) {
		this.olId = olId;
	}
	public String getOlName() {
		return olName;
	}
	public void setOlName(String olName) {
		this.olName = olName;
	}
	public OneLevelType(String olName) {
		super();
		this.olName = olName;
	}
	public OneLevelType() {
		super();
	}
	@Override
	public String toString() {
		return "OneLevelType [olId=" + olId + ", olName=" + olName + "]";
	}
	
}
