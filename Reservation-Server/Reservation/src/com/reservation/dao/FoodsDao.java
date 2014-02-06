package com.reservation.dao;

import java.util.List;

import com.reservation.pojo.Foods;

public interface FoodsDao {
	public int insertFoods(Foods food);
	public int updateFoods(Foods food);
	public int deleteFoods(int food);
	public Foods getFoodById(int id);
	public List<Foods> getAllFoods();
}
