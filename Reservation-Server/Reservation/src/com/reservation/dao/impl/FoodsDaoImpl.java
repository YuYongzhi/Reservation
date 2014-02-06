package com.reservation.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.reservation.dao.FoodsDao;
import com.reservation.pojo.Foods;
import com.reservation.util.ConnectionUtil;


public class FoodsDaoImpl implements FoodsDao{
	private Connection conn = ConnectionUtil.getConnection();
	private Statement stat = null;
	private PreparedStatement pstat = null;
	public int insertFoods(Foods food) {
		int i=-1;
		String sql = "insert into foods(f_id,f_name,f_price,f_img,f_features,ol_id,tl_id,f_new_date," +
				"f_praise_count,f_marks,f_views_count,f_hot_cold,f_taste_id," +
				"f_ingredient) values(foodseq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, food.getfName());
			pstat.setDouble(2, food.getfPrice());
			pstat.setString(3, food.getfImage());
			pstat.setString(4, food.getfFeatures());
			pstat.setInt(5, food.getOneType().getOlId());
			pstat.setInt(6, food.getTwoType().getTlId());
			pstat.setDate(7, new java.sql.Date(food.getfNewDate().getTime()));
			pstat.setInt(8, food.getfPraiseCount());
			pstat.setDouble(9, food.getfMarks());
			pstat.setInt(10, food.getfViewsCount());
			pstat.setInt(11, food.getfHotCold());
			pstat.setInt(12, food.getFoodTaste().getfTasteId());
			pstat.setString(13, food.getfIngredient());
			i = pstat.executeUpdate();
			if(i>0){
				stat = conn.createStatement();
				String sql1 = "select foodseq.currval from dual";
				pstat = conn.prepareStatement(sql1);
				ResultSet rs = pstat.executeQuery();
				while(rs.next()){
					int j = rs.getInt(1);
					food.setfId(j);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return i; 
	}
	//更新时，食物的上架时间也会随之改变，变成当前时间
	public int updateFoods(Foods food) {
		int i=-1;
		String sql = "update foods set f_name=?,f_price=?,f_img=?,f_features=?,ol_id=?,tl_id=?,f_new_date=?," +
				"f_praise_count=?,f_marks=?,f_views_count=?,f_hot_cold=?,f_taste_id=?," +
				"f_ingredient=? where f_id=? ";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, food.getfName());
			pstat.setDouble(2, food.getfPrice());
			pstat.setString(3, food.getfImage());
			pstat.setString(4, food.getfFeatures());
			pstat.setInt(5, food.getOneType().getOlId());
			pstat.setInt(6, food.getTwoType().getTlId());
			pstat.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
			pstat.setInt(8, food.getfPraiseCount());
			pstat.setDouble(9, food.getfMarks());
			pstat.setInt(10, food.getfViewsCount());
			pstat.setInt(11, food.getfHotCold());
			pstat.setInt(12, food.getFoodTaste().getfTasteId());
			pstat.setString(13, food.getfIngredient());
			pstat.setInt(14, food.getfId());
			i = pstat.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return i; 
	}

	public int deleteFoods(int fid) {
		int i=-1;
		String sql = "delete from foods where f_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, fid);
			i = pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return i;
	}

	public Foods getFoodById(int id) {
		Foods f = null;
		String sql ="select * from foods where f_id=?";
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				f = new Foods();
				f.setfId(rs.getInt("f_id"));
				f.setfName(rs.getString("f_name"));
				f.setfImage(rs.getString("f_img"));
				f.setfPrice(rs.getDouble("f_price"));
				f.setfFeatures(rs.getString("f_features"));
				f.setOneType(OneLevelTypeDaoImpl.getOneLevelType(rs.getInt("ol_id")));
				f.setTwoType(TwoLevelTypeDaoImpl.getTwoLevelType(rs.getInt("tl_id")));
				f.setfNewDate(new java.util.Date(rs.getDate("f_new_date").getTime()));
				f.setfPraiseCount(rs.getInt("f_praise_count"));
				f.setfMarks(rs.getDouble("f_marks"));
				f.setfViewsCount(rs.getInt("f_views_count"));
				f.setfHotCold(rs.getInt("f_hot_cold"));
				f.setFoodTaste(FoodTasteDaoImpl.getFoodTaste(rs.getInt("F_TASTE_ID")));
				f.setfIngredient(rs.getString("f_ingredient"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return f;
	}

	public List<Foods> getAllFoods() {
		List<Foods> list = null;
		String sql = "select * from foods";
		try {
			pstat = conn.prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();
			list = new ArrayList<Foods>();
			while(rs.next()){
				Foods f = new Foods();
				f.setfId(rs.getInt("f_id"));
				f.setfName(rs.getString("f_name"));
				f.setfImage(rs.getString("f_img"));
				f.setfPrice(rs.getDouble("f_price"));
				f.setfFeatures(rs.getString("f_features"));
				f.setOneType(OneLevelTypeDaoImpl.getOneLevelType(rs.getInt("ol_id")));
				f.setTwoType(TwoLevelTypeDaoImpl.getTwoLevelType(rs.getInt("tl_id")));
				f.setfNewDate(new java.util.Date(rs.getDate("f_new_date").getTime()));
				f.setfPraiseCount(rs.getInt("f_praise_count"));
				f.setfMarks(rs.getDouble("f_marks"));
				f.setfViewsCount(rs.getInt("f_views_count"));
				f.setfHotCold(rs.getInt("f_hot_cold"));
				f.setFoodTaste(FoodTasteDaoImpl.getFoodTaste(rs.getInt("F_TASTE_ID")));
				f.setfIngredient(rs.getString("f_ingredient"));
				list.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.CloseConnection();
		}
		return list;
	}
	public List<Foods> getSomeFoodsByType(int olid,int tlid,int currPage,int pageSize){
		int fromPage = (currPage-1)*pageSize;
		int toPage = (currPage)*pageSize;
		List<Foods> list = new ArrayList<Foods>();
		String sql=null;
		if(olid!=1){
			 sql= "select * from (select foods.*,rownum nm from foods where ol_id=? and  tl_id=? and rownum < ?) where nm>=?";
			 try {
				pstat = conn.prepareStatement(sql);
				pstat.setInt(1, olid);
				pstat.setInt(2,tlid);
				pstat.setInt(3, toPage);
				pstat.setInt(4, fromPage);
				ResultSet rs = pstat.executeQuery();
				while(rs.next()){
					Foods f = new Foods();
					f.setfId(rs.getInt("f_id"));
					f.setfName(rs.getString("f_name"));
					f.setfImage(rs.getString("f_img"));
					f.setfPrice(rs.getDouble("f_price"));
					f.setfFeatures(rs.getString("f_features"));
					f.setOneType(OneLevelTypeDaoImpl.getOneLevelType(rs.getInt("ol_id")));
					f.setTwoType(TwoLevelTypeDaoImpl.getTwoLevelType(rs.getInt("tl_id")));
					f.setfNewDate(new java.util.Date(rs.getDate("f_new_date").getTime()));
					f.setfPraiseCount(rs.getInt("f_praise_count"));
					f.setfMarks(rs.getDouble("f_marks"));
					f.setfViewsCount(rs.getInt("f_views_count"));
					f.setfHotCold(rs.getInt("f_hot_cold"));
					f.setFoodTaste(FoodTasteDaoImpl.getFoodTaste(rs.getInt("F_TASTE_ID")));
					f.setfIngredient(rs.getString("f_ingredient"));
					list.add(f);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			sql="select * from (select foods.*,rownum rn from foods, preferential where foods.f_id =preferential.f_id and rownum <?) where rn>=?";
			try {
				pstat = conn.prepareStatement(sql);
				pstat.setInt(1, toPage);
				pstat.setInt(2,fromPage);
				ResultSet rs = pstat.executeQuery();
				while(rs.next()){
					Foods f = new Foods();
					f.setfId(rs.getInt("f_id"));
					f.setfName(rs.getString("f_name"));
					f.setfImage(rs.getString("f_img"));
					f.setfPrice(rs.getDouble("f_price"));
					f.setfFeatures(rs.getString("f_features"));
					f.setOneType(OneLevelTypeDaoImpl.getOneLevelType(rs.getInt("ol_id")));
					f.setTwoType(TwoLevelTypeDaoImpl.getTwoLevelType(rs.getInt("tl_id")));
					f.setfNewDate(new java.util.Date(rs.getDate("f_new_date").getTime()));
					f.setfPraiseCount(rs.getInt("f_praise_count"));
					f.setfMarks(rs.getDouble("f_marks"));
					f.setfViewsCount(rs.getInt("f_views_count"));
					f.setfHotCold(rs.getInt("f_hot_cold"));
					f.setFoodTaste(FoodTasteDaoImpl.getFoodTaste(rs.getInt("F_TASTE_ID")));
					f.setfIngredient(rs.getString("f_ingredient"));
					list.add(f);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/*public static void main(String[] args) {
		FoodsDaoImpl impl  = new FoodsDaoImpl();
		System.out.println(impl.getFoodById(1));
		System.out.println(impl.getSomeFoodsByType(2, 3, 1, 4));
	}*/
}
