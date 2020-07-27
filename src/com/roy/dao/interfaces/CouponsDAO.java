package com.roy.dao.interfaces;

import java.util.ArrayList;

import com.roy.beans.Coupon;
import com.roy.enums.Category;
import com.roy.exception.CouponSystemException;

public interface CouponsDAO {
	
	boolean isCouponExists(int couponID) throws CouponSystemException;
	boolean isAlreadyPurchasedCoupon(int couponID, int customerID) throws CouponSystemException;
	void addCoupon(Coupon coupon) throws CouponSystemException;
	void updateCoupon(Coupon coupon) throws CouponSystemException;
	void decreaseCouponAmount(int couponID) throws CouponSystemException;
	void deleteCoupon(int couponID) throws CouponSystemException;
	ArrayList<Coupon> getAllCoupons() throws CouponSystemException;
	Coupon getOneCoupon(int couponID) throws CouponSystemException;
	void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	void deleteCouponByCompanyId(int companyID) throws CouponSystemException;
	void deletePurchasedCouponsByCompanyID(int companyID) throws CouponSystemException;
	void deletePurchasedCouponsByCustomerID(int customerID) throws CouponSystemException;
	void deletePurchasedCouponsByCouponID(int couponID) throws CouponSystemException;
	void deleteExpiredCoupons() throws CouponSystemException;
	boolean isCouponTitleExistsByCompanyID(String couponTitle, int companyID) throws CouponSystemException;
	ArrayList<Coupon> getAllCouponsByCompanyID(int companyID) throws CouponSystemException;
	ArrayList<Coupon> getAllCouponsByCompanyIDAndCategoryID(int companyID, Category category) throws CouponSystemException;
	ArrayList<Coupon> getAllCouponsByCompanyIDAndMaxPrice(int companyID, double maxPrice) throws CouponSystemException;
	ArrayList<Coupon> getAllCouponsByCustomerID(int customerID) throws CouponSystemException;
	ArrayList<Coupon> getAllCouponsByCustomerIDAndCategoryID(int customerID, Category category) throws CouponSystemException;
	ArrayList<Coupon> getAllCouponsByCustomerIDAndMaxPrice(int customerID, double maxPrice) throws CouponSystemException;

}
