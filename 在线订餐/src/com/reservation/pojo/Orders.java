package com.reservation.pojo;

import java.util.Date;

public class Orders {

	private int oId;
	private Date oDate;
	private Users user;
	private OrderType type;
	private OrderStatus status;
	public int getoId() {
		return oId;
	}
	public void setoId(int oId) {
		this.oId = oId;
	}
	public Date getoDate() {
		return oDate;
	}
	public void setoDate(Date oDate) {
		this.oDate = oDate;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param oDate
	 * @param user
	 * @param type
	 * @param status
	 * 
	 * @author 2014-1-20
	 */
	public Orders(Date oDate, Users user, OrderType type, OrderStatus status) {
		super();
		this.oDate = oDate;
		this.user = user;
		this.type = type;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Orders [oId=" + oId + ", oDate=" + oDate + ", user=" + user
				+ ", type=" + type + ", status=" + status + "]";
	}
	
	
}
