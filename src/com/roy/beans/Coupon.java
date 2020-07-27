package com.roy.beans;

import java.sql.Date;
import com.roy.enums.Category;

public class Coupon {

	private int id;
	private int companyID;
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String image;

	public Coupon() {
	};

	public Coupon(int companyID, Category category, String title, String description, Date startDate, Date endDate,
			int amount, double price, String image) {
		this.companyID = companyID;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon(int id, int companyID, Category category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		this(companyID, category, title, description, startDate, endDate, amount, price, image);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getCompanyID() {
		return companyID;
	}

	public Category getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getAmount() {
		return amount;
	}

	public double getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return String.format(
				"Coupon [id=%s, companyID=%s, category=%s, title=%s, description=%s, startDate=%s, endDate=%s,"
						+ " amount=%s, price=%s, image=%s]",
				id, companyID, category, title, description, startDate, endDate, amount, price, image);
	}

}
