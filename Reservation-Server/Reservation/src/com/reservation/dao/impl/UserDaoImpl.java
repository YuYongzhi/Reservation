package com.reservation.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.reservation.dao.UsersDao;
import com.reservation.pojo.Users;
import com.reservation.util.ConnectionUtil;

public class UserDaoImpl implements UsersDao{
	private Connection conn = ConnectionUtil.getConnection();
	private PreparedStatement pstat = null; 
	public int insertUser(Users u) {
		int i=-1;
		String sql = "insert into users(u_id,u_name,u_password,u_telephone,u_email,u_vip_level) values(userseq.nextval,?,?,?,?,?)";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, u.getuName());
			pstat.setString(2, u.getuPassword());
			pstat.setString(3, u.getuTelephone());
			pstat.setString(4, u.getuEmail());
			pstat.setInt(5, u.getuVipLevel());
			i = pstat.executeUpdate();
			if(i>0){
				String sql2 = "select userseq.currval from dual";
				PreparedStatement pstat1 = conn.prepareStatement(sql2);
				ResultSet rs = pstat1.executeQuery();
				while(rs.next()){
					int j = rs.getInt(1);
					u.setuId(j);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return i;
	}

	public int updateUser(Users u) {
		int i=-1;
		String sql = "update users set u_name=?,u_password=?,u_telephone=?,u_email=?,u_vip_level=? where u_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, u.getuName());
			pstat.setString(2, u.getuPassword());
			pstat.setString(3, u.getuTelephone());
			pstat.setString(4, u.getuEmail());
			pstat.setInt(5, u.getuVipLevel());
			pstat.setInt(6, u.getuId());
			i = pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return i;
	}

	public int deleteUser(int uId) {
		int i=-1;
		String sql ="delete from users where u_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, uId);
			i = pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		
		return i;
	}

	public Users getUserById(int id) {
		Users u = null;
		String sql ="select * from users where u_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				u = new Users();
				u.setuId(rs.getInt("u_id"));
				u.setuName(rs.getString("u_name"));
				u.setuPassword(rs.getString("u_password"));
				u.setuTelephone(rs.getString("u_telephone"));
				u.setuEmail(rs.getString("u_email"));
				u.setuVipLevel(rs.getInt("U_VIP_LEVEL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return u;
	}

	public List<Users> getAllUsers() {
		List<Users> list= null;
		String sql ="select * from users";
		try {
			pstat = conn.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();
			list = new ArrayList<Users>();
			while(rs.next()){
				Users u = new Users();
				u.setuId(rs.getInt("u_id"));
				u.setuName(rs.getString("u_name"));
				u.setuPassword(rs.getString("u_password"));
				u.setuTelephone(rs.getString("u_telephone"));
				u.setuEmail(rs.getString("u_email"));
				u.setuVipLevel(rs.getInt("U_VIP_LEVEL"));
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return list;
	}
/*public static void main(String[] args) {
	UserDaoImpl impl = new UserDaoImpl();
	System.out.println(impl.getUserById(1));
}*/
}
