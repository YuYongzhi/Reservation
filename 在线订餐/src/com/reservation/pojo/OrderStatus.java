package com.reservation.pojo;

public class OrderStatus {

	private int oStatusId;
	private String oStatusName;
	public int getoStatusId() {
		return oStatusId;
	}
	public void setoStatusId(int oStatusId) {
		this.oStatusId = oStatusId;
	}
	public String getoStatusName() {
		return oStatusName;
	}
	public void setoStatusName(String oStatusName) {
		this.oStatusName = oStatusName;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public OrderStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param oStatusName
	 * 
	 * @author 2014-1-20
	 */
	public OrderStatus(String oStatusName) {
		super();
		this.oStatusName = oStatusName;
	}

	@Override
	public String toString() {
		return "OrderStatus [oStatusId=" + oStatusId + ", oStatusName="
				+ oStatusName + "]";
	}
	
	
}
