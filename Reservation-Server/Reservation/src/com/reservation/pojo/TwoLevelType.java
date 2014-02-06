package com.reservation.pojo;

public class TwoLevelType {
	private int tlId;
	private String tlName;
	private OneLevelType onetype;
	public int getTlId() {
		return tlId;
	}
	public void setTlId(int tlId) {
		this.tlId = tlId;
	}
	public String getTlName() {
		return tlName;
	}
	public void setTlName(String tlName) {
		this.tlName = tlName;
	}
	public OneLevelType getOnetype() {
		return onetype;
	}
	public void setOnetype(OneLevelType onetype) {
		this.onetype = onetype;
	}
	public TwoLevelType(String tlName, OneLevelType onetype) {
		super();
		this.tlName = tlName;
		this.onetype = onetype;
	}
	public TwoLevelType() {
		super();
	}
	@Override
	public String toString() {
		return "TwoLevelType [tlId=" + tlId + ", tlName=" + tlName
				+ ", onetype=" + onetype + "]";
	}
	
}
