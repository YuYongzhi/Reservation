package com.reservation.pojo;

import java.util.Date;

public class Comments {

	private int cId;
	private Users user;
	private Date cDate;
	private Foods food;
	private String comment;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	public Foods getFood() {
		return food;
	}
	public void setFood(Foods food) {
		this.food = food;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param user
	 * @param cDate
	 * @param food
	 * @param comment
	 * 
	 * @author 2014-1-20
	 */
	public Comments(Users user, Date cDate, Foods food, String comment) {
		super();
		this.user = user;
		this.cDate = cDate;
		this.food = food;
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "Comments [cId=" + cId + ", user=" + user + ", cDate=" + cDate
				+ ", food=" + food + ", comment=" + comment + "]";
	}
	
	
}
