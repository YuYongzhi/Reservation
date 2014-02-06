package com.reservation.pojo;

public class Users {

	private int uId;
	private String uName;
	private String uPassword;
	private String uTelephone;
	private String uEmail;
	private int uVipLevel;
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	public String getuTelephone() {
		return uTelephone;
	}
	public void setuTelephone(String uTelephone) {
		this.uTelephone = uTelephone;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public int getuVipLevel() {
		return uVipLevel;
	}
	public void setuVipLevel(int uVipLevel) {
		this.uVipLevel = uVipLevel;
	}
	/**
	 * @param uName
	 * @param uPassword
	 * @param uTelephone
	 * @param uEmail
	 * @param uVipLevel
	 * 
	 * @author 2014-1-20
	 */
	public Users(String uName, String uPassword, String uTelephone,
			String uEmail, int uVipLevel) {
		super();
		this.uName = uName;
		this.uPassword = uPassword;
		this.uTelephone = uTelephone;
		this.uEmail = uEmail;
		this.uVipLevel = uVipLevel;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public Users() {
		super();
	}
	@Override
	public String toString() {
		return "Users [uId=" + uId + ", uName=" + uName + ", uPassword="
				+ uPassword + ", uTelephone=" + uTelephone + ", uEmail="
				+ uEmail + ", uVipLevel=" + uVipLevel + "]";
	}
	
}
