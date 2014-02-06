package com.reservation.pojo;

import java.util.Date;

public class Orders {
	private int oId;
	private Date oDate;
	private Users user;
	private OrderStatus status;
	private OrderType type;
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
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
	}
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Orders [oId=" + oId + ", oDate=" + oDate + ", user=" + user
				+ ", status=" + status + ", type=" + type + "]";
	}
	public Orders(Date oDate, Users user, OrderStatus status, OrderType type) {
		super();
		this.oDate = oDate;
		this.user = user;
		this.status = status;
		this.type = type;
	}
	
}
