package com.reservation.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.reservation.pojo.OneLevelType;
import com.reservation.util.ConnectionUtil;

public class OneLevelTypeDaoImpl {
	private static Connection conn = ConnectionUtil.getConnection();
	private  static PreparedStatement pstat = null;
	public static OneLevelType getOneLevelType(int id){
		OneLevelType ot = null;
		String sql ="select * from oneleveltype where ol_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				ot = new OneLevelType();
				ot.setOlId(rs.getInt(1));
				ot.setOlName(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return ot;
	}
	public static List<OneLevelType> getAllTypes(){
		List<OneLevelType> list = new ArrayList<OneLevelType>();
		String sql ="select * from ONELEVELTYPE";
		try {
			pstat = conn.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				OneLevelType	ot = new OneLevelType();
				ot.setOlId(rs.getInt(1));
				ot.setOlName(rs.getString(2));
				list.add(ot);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return list;
	}
	/*public static void main(String[] args) {
		List<OneLevelType> list = OneLevelTypeDaoImpl.getAllTypes();
		System.out.println(list);
		
		
	}*/
}
