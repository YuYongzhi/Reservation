package com.reservation.pojo;

import java.util.Date;

public class Foods {
	private int fId;
	private String fName;
	private double fPrice;
	private String fImage;
	private String fFeatures;
	private OneLevelType oneType;
	private TwoLevelType twoType;
	private Date fNewDate;
	private int fPraiseCount;
	private double fMarks;
	private int fViewsCount;
	private int fHotCold;
	private FoodTaste foodTaste;
	private String fIngredient;
	public int getfId() {
		return fId;
	}
	public void setfId(int fId) {
		this.fId = fId;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public double getfPrice() {
		return fPrice;
	}
	public void setfPrice(double fPrice) {
		this.fPrice = fPrice;
	}
	public String getfImage() {
		return fImage;
	}
	public void setfImage(String fImage) {
		this.fImage = fImage;
	}
	public String getfFeatures() {
		return fFeatures;
	}
	public void setfFeatures(String fFeatures) {
		this.fFeatures = fFeatures;
	}
	public OneLevelType getOneType() {
		return oneType;
	}
	public void setOneType(OneLevelType oneType) {
		this.oneType = oneType;
	}
	public TwoLevelType getTwoType() {
		return twoType;
	}
	public void setTwoType(TwoLevelType twoType) {
		this.twoType = twoType;
	}
	public Date getfNewDate() {
		return fNewDate;
	}
	public void setfNewDate(Date fNewDate) {
		this.fNewDate = fNewDate;
	}
	public int getfPraiseCount() {
		return fPraiseCount;
	}
	public void setfPraiseCount(int fPraiseCount) {
		this.fPraiseCount = fPraiseCount;
	}
	public double getfMarks() {
		return fMarks;
	}
	public void setfMarks(double fMarks) {
		this.fMarks = fMarks;
	}
	public int getfViewsCount() {
		return fViewsCount;
	}
	public void setfViewsCount(int fViewsCount) {
		this.fViewsCount = fViewsCount;
	}
	public int getfHotCold() {
		return fHotCold;
	}
	public void setfHotCold(int fHotCold) {
		this.fHotCold = fHotCold;
	}
	public FoodTaste getFoodTaste() {
		return foodTaste;
	}
	public void setFoodTaste(FoodTaste foodTaste) {
		this.foodTaste = foodTaste;
	}
	public String getfIngredient() {
		return fIngredient;
	}
	public void setfIngredient(String fIngredient) {
		this.fIngredient = fIngredient;
	}
	public Foods(String fName, double fPrice, String fImage, String fFeatures,
			OneLevelType oneType, TwoLevelType twoType, Date fNewDate,
			int fPraiseCount, double fMarks, int fViewsCount, int fHotCold,
			FoodTaste foodTaste, String fIngredient) {
		super();
		this.fName = fName;
		this.fPrice = fPrice;
		this.fImage = fImage;
		this.fFeatures = fFeatures;
		this.oneType = oneType;
		this.twoType = twoType;
		this.fNewDate = fNewDate;
		this.fPraiseCount = fPraiseCount;
		this.fMarks = fMarks;
		this.fViewsCount = fViewsCount;
		this.fHotCold = fHotCold;
		this.foodTaste = foodTaste;
		this.fIngredient = fIngredient;
	}
	public Foods() {
		super();
	}
	@Override
	public String toString() {
		return "Foods [fId=" + fId + ", fName=" + fName + ", fPrice=" + fPrice
				+ ", fImage=" + fImage + ", fFeatures=" + fFeatures
				+ ", oneType=" + oneType + ", twoType=" + twoType
				+ ", fNewDate=" + fNewDate + ", fPraiseCount=" + fPraiseCount
				+ ", fMarks=" + fMarks + ", fViewsCount=" + fViewsCount
				+ ", fHotCold=" + fHotCold + ", foodTaste=" + foodTaste
				+ ", fIngredient=" + fIngredient + "]";
	}
}
