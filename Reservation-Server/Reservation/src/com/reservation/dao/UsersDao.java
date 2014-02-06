package com.reservation.dao;


import java.util.List;

import com.reservation.pojo.Users;

public interface UsersDao {
	public int insertUser(Users u);
	public int updateUser(Users u);
	public int deleteUser(int uId);
	public Users getUserById(int id);
	public List<Users> getAllUsers();
}
