package com.reservation.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.reservation.pojo.FoodTaste;
import com.reservation.util.ConnectionUtil;

public class FoodTasteDaoImpl {
	private static Connection conn = ConnectionUtil.getConnection();
	public static FoodTaste getFoodTaste(int id){
		FoodTaste ft = null;
		String sql ="select * from foodtaste where F_TASTE_ID=?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				ft = new FoodTaste();
				ft.setfTasteId(rs.getInt("F_TASTE_ID"));
				ft.setfTasteName(rs.getString("F_TASTE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return ft;
	}
	/*public static void main(String[] args) {
		System.out.println(FoodTasteDaoImpl.getFoodTaste(1));
	}*/
}
