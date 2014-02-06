package com.reservation.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.reservation.pojo.OneLevelType;
import com.reservation.pojo.TwoLevelType;
import com.reservation.util.ConnectionUtil;

public class TwoLevelTypeDaoImpl {
	private static Connection conn = ConnectionUtil.getConnection();
	private static PreparedStatement pstat = null;
	public static TwoLevelType getTwoLevelType(int id){
		TwoLevelType ot = null;
		String sql ="select * from twoleveltype where tl_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				ot = new TwoLevelType();
				ot.setTlId(rs.getInt(1));
				ot.setTlName(rs.getString(2));
				ot.setOnetype(OneLevelTypeDaoImpl.getOneLevelType(rs.getInt("ol_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return ot;
	}
	public static List<TwoLevelType> getAllType(){
		List<TwoLevelType> list = new ArrayList<TwoLevelType>();
		String sql ="select * from TwoLEVELTYPE";
		try {
			pstat = conn.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				TwoLevelType	ot = new TwoLevelType();
				ot.setTlId(rs.getInt(1));
				ot.setTlName(rs.getString(2));
				ot.setOnetype(OneLevelTypeDaoImpl.getOneLevelType(rs.getInt("ol_id")));
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
		System.out.println(TwoLevelTypeDaoImpl.getAllType());
	}*/
}
