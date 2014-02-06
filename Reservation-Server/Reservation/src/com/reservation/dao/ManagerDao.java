package com.reservation.dao;

import java.util.List;

import com.reservation.pojo.Managers;

public interface ManagerDao {
	public List<Managers> getAllManagers();
	public Managers getManagerById();
		
}
