package com.reservation.pojo;

public class Managers {

	private int mId;
	private String mName;
	private String mPassword;
	
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmPassword() {
		return mPassword;
	}
	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}
	/**
	 * @param mName
	 * @param mPassword
	 * 
	 * @author 2014-1-20
	 */
	public Managers(String mName, String mPassword) {
		super();
		this.mName = mName;
		this.mPassword = mPassword;
	}
	/**
	 * 
	 * 
	 * @author 2014-1-20
	 */
	public Managers() {
		super();
	}
	@Override
	public String toString() {
		return "Managers [mId=" + mId + ", mName=" + mName + ", mPassword="
				+ mPassword + "]";
	}
	
}
