package com.reservation.pojo;

public class OrderType {

	private int oTypeId;
	private String oTypeName;
	public int getoTypeId() {
		return oTypeId;
	}
	public void setoTypeId(int oTypeId) {
		this.oTypeId = oTypeId;
	}
	public String getoTypeName() {
		return oTypeName;
	}
	public void setoTypeName(String oTypeName) {
		this.oTypeName = oTypeName;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public OrderType() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param oTypeName
	 * 
	 * @author 2014-1-20
	 */
	public OrderType(String oTypeName) {
		super();
		this.oTypeName = oTypeName;
	}
	@Override
	public String toString() {
		return "OrderType [oTypeId=" + oTypeId + ", oTypeName=" + oTypeName
				+ "]";
	}
}
