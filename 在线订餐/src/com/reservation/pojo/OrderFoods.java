package com.reservation.pojo;

public class OrderFoods {

	private Orders order;
	private Foods food;
	private int ofNum;
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public Foods getFood() {
		return food;
	}
	public void setFood(Foods food) {
		this.food = food;
	}
	public int getOfNum() {
		return ofNum;
	}
	public void setOfNum(int ofNum) {
		this.ofNum = ofNum;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public OrderFoods() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param order
	 * @param food
	 * @param ofNum
	 * 
	 * @author 2014-1-20
	 */
	public OrderFoods(Orders order, Foods food, int ofNum) {
		super();
		this.order = order;
		this.food = food;
		this.ofNum = ofNum;
	}
	@Override
	public String toString() {
		return "OrderFoods [order=" + order + ", food=" + food + ", ofNum="
				+ ofNum + "]";
	}
	
	
	
}
