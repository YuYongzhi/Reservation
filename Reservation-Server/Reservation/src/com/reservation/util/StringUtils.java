package com.reservation.util;

public class StringUtils {

	public final static String PREFS_GUIDE_STRING = "guide_file";
	
	public final static class PrefsGuideItem{
		public final static String IS_FIRST_GUIDE = "is_first_guide";
	}
	
	public final static class Reservation {
		
		/**
		 * 表-管理员表
		 */
		public final static String TAB_MANAGERS = "managers";
		/**
		 * 管理员表
		 * @author 2014-1-20
		 */
		public final static class Managers {
			/**
			 * ID
			 */
			public final static String ID = "m_id";
			/**
			 * 用户名
			 */
			public final static String NAME = "m_name";
			/**
			 * 用户密码
			 */
			public final static String PASSWORD = "m_password";
		}

		/**
		 * 表-用户表
		 */
		public final static String TAB_USERS = "users";
		/**
		 * 用户表
		 * @author 2014-1-20
		 */
		public final static class Users {
			/**
			 * ID
			 */
			public final static String ID = "u_id";
			/**
			 * 用户名
			 */
			public final static String NAME = "u_name";
			/**
			 * 用户密码
			 */
			public final static String PASSWORD = "u_password";
			/**
			 * 用户手机
			 */
			public final static String TELEPHONE = "u_telephone";
			/**
			 * 邮箱
			 */
			public final static String EMAIL = "u_email";
			/**
			 * VIP等级
			 */
			public final static String VIP_LEVEL = "u_vip_level";
		}
		
		/**
		 * 表-一级类型表
		 */
		public final static String TAB_ONE_LEVEL_TYPE = "oneleveltype";
		/**
		 * 一级类型表
		 * @author 2014-1-20
		 */
		public final static class OneLevelType {
			/**
			 * ID
			 */
			public final static String ID = "ol_id";
			/**
			 * 一级类型名称
			 */
			public final static String NAME = "ol_name";
		}
		
		/**
		 * 表-二级类型表
		 */
		public final static String TAB_TWO_LEVEL_TYPE = "twoleveltype";
		/**
		 * 二级类型表
		 * @author 2014-1-20
		 */
		public final static class TwoLevelType {
			/**
			 * ID
			 */
			public final static String ID = "tl_id";
			/**
			 * 二级类型名称
			 */
			public final static String NAME = "tl_name";
			/**
			 * 对应的一级类型ID
			 */
			public final static String ID_ONE_LEVEL = "ol_id";
		}
		
		/**
		 * 表-食品表
		 */
		public final static String TAB_FOODS = "foods";
		/**
		 * 食品表
		 * @author 2014-1-20
		 */
		public final static class Foods {
			/**
			 * 食品 ID
			 */
			public final static String ID = "f_id";
			/**
			 * 食品名称
			 */
			public final static String NAME = "f_name";
			/**
			 * 价格
			 */
			public final static String PRICE = "f_price";
			/**
			 * 图片
			 */
			public final static String IMAGE = "f_img";
			/**
			 * 特色
			 */
			public final static String FEATURES = "f_features";
			/**
			 * 一级类型
			 */
			public final static String ID_ONE_TYPE = "ol_id";
			/**
			 * 二级类型
			 */
			public final static String ID_TWO_TYPE = "tl_id";
			/**
			 * 更新日期
			 */
			public final static String NEW_DATE = "f_new_date";
			/**
			 * 点赞统计量
			 */
			public final static String PRAISE_COUNT = "f_praise_count";
			/**
			 * 评分
			 */
			public final static String MARKS = "f_marks";
			/**
			 * 浏览量
			 */
			public final static String VIEWS_COUNT = "f_views_count";
			/**
			 * 热菜/凉菜
			 */
			public final static String HOT_COLD = "f_hot_cold";
			/**
			 * 口味
			 */
			public final static String TASTE = "f_taste";
			/**
			 * 材料
			 */
			public final static String INGREDIENT = "f_ingredient";
		}
	
		/**
		 * 表-食品口味表
		 */
		public final static String TAB_FOOD_TASTE = "foodtaste";
		/**
		 * 食品口味
		 * @author 2014-1-20_Evan.Yu
		 */
		public final static class FoodTaste {
			/**
			 * ID
			 */
			public final static String ID = "f_taste_id";
			/**
			 * 口味名称
			 */
			public final static String NAME = "f_taste_name";
		}
		
		/**
		 * 表-优惠食品表
		 */
		public final static String TAB_PREFERENTIAL = "preferential";
		/**
		 * 优惠食品表
		 */
		public final static class Preferential {
			/**
			 * ID 
			 */
			public final static String ID = "p_id";
			/**
			 * 食品 ID
			 */
			public final static String ID_FOODS = "f_id";
			/**
			 * 优惠价格
			 */
			public final static String PRICE = "p_price";
		}
		
		/**
		 * 表-订单表
		 */
		public final static String TAB_ORDERS = "orders";
		/**
		 * 订单表
		 */
		public final static class Orders {
			/**
			 * ID
			 */
			public final static String ID = "o_id";
			/**
			 * 订单日期
			 */
			public final static String DATE = "o_date";
			/**
			 * 用户ID
			 */
			public final static String ID_USERS = "u_id";
			/**
			 * 订单类别
			 */
			public final static String TYPE = "o_type";
			/**
			 * 订单状态
			 */
			public final static String STATUS = "o_status";
		}
		
		/**
		 * 表-订单类型表
		 */
		public final static String TAB_ORDER_TYPE = "ordertype";
		/**
		 * 订单类型表
		 */
		public final static class OrderType {
			/**
			 * ID
			 */
			public final static String ID = "o_type_id";
			/**
			 * 订单类型名称
			 */
			public final static String NAME = "o_type_name";
		}
		
		/**
		 * 表-订单状态表
		 */
		public final static String TAB_ORDER_STATUS = "orderstatus";
		/**
		 * 订单状态
		 * @author 2014-1-20
		 */
		public final static class OrderStatus {
			/**
			 * ID
			 */
			public final static String ID = "o_status_id";
			/**
			 * 订单状态名称
			 */
			public final static String NAME = "o_status_name";
		}
		
		/**
		 * 表-订菜表
		 */
		public final static String TAB_ORDER_FOODS = "orderfoods";
		/**
		 * 订菜表
		 * @author 2014-1-20
		 */
		public final static class OrderFoods {
			/**
			 * ID
			 */
			public final static String ID_ORDER = "o_id";
			/**
			 * 食品ID
			 */
			public final static String ID_FOOD = "f_id";
			/**
			 * 食品数量
			 */
			public final static String NUM = "of_num";
		}
		
		/**
		 * 表-评论表
		 */
		public final static String TAB_COMMENTS = "comments";
		/**
		 * 评论表
		 * @author 2014-1-20_Evan.Yu
		 */
		public final static class Comments {
			/**
			 * ID
			 */
			public final static String ID = "c_id";
			/**
			 * 用户ID
			 */
			public final static String ID_USERS = "u_id";
			/**
			 * 评论时间
			 */
			public final static String DATE = "c_date";
			/**
			 * 评论的食品的ID
			 */
			public final static String ID_FOOD = "f_id";
			/**
			 * 评论内容
			 */
			public final static String COMMENT = "c_comment";
		}
	
	
	
	}
}
