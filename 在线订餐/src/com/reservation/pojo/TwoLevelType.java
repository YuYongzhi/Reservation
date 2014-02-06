package com.reservation.pojo;

public class TwoLevelType {

	private int tlId;
	private String tlName;
	private OneLevelType oneType;
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
	public OneLevelType getOneType() {
		return oneType;
	}
	public void setOneType(OneLevelType oneType) {
		this.oneType = oneType;
	}
	/**
	 * @param tlName
	 * @param oneType
	 * 
	 * @author 2014-1-20
	 */
	public TwoLevelType(String tlName, OneLevelType oneType) {
		super();
		this.tlName = tlName;
		this.oneType = oneType;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public TwoLevelType() {
		super();
	}

	@Override
	public String toString() {
		return "TwoLevelType [tlId=" + tlId + ", tlName=" + tlName
				+ ", oneType=" + oneType + "]";
	}
}
