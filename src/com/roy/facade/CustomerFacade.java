package com.roy.facade;

import java.util.ArrayList;
import java.util.Date;
import com.roy.beans.Coupon;
import com.roy.beans.Customer;
import com.roy.dao.interfaces.CompaniesDAO;
import com.roy.dao.interfaces.CouponsDAO;
import com.roy.dao.interfaces.CustomersDAO;
import com.roy.enums.Category;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;

public class CustomerFacade extends ClientFacade {

private int customerID;
	
	public CustomerFacade(CustomersDAO customersDao, CouponsDAO couponsDao, CompaniesDAO companiesDao) {
		super(customersDao, couponsDao, companiesDao);
		this.customerID = 0;
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		boolean isValidLogin = super.customersDao.isCustomerExists(email, password);
		if (isValidLogin) {
			this.customerID = super.customersDao.getCustomerIdByCredentials(email, password);
		}
		return isValidLogin;
	}
	
	/* purchasing a new coupon and updating it's amount in db, if:
	 * the coupon wasn't already purchased
	 * the coupon is in stock
	 * the coupon is not expired
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		if (super.couponsDao.isCouponExists(coupon.getId())) {
			if (super.couponsDao.isAlreadyPurchasedCoupon(coupon.getId(), customerID)) {
				throw new CouponSystemException(ErrorType.COUPON_ALREADY_PURCHASED,
						"error while running purchaseCoupon in CustomerFacade, chosen coupon was already purchased");
			}
			if (coupon.getAmount()<1){
				throw new CouponSystemException(ErrorType.OUT_OF_STOCK,
						"error while running purchaseCoupon in CustomerFacade, chosen coupon is out of stock");
			}
			if (new Date().after(coupon.getEndDate())){
				throw new CouponSystemException(ErrorType.EXPIRED,
						"error while running purchaseCoupon in CustomerFacade, chosen coupon is expired");
			}
			super.couponsDao.addCouponPurchase(customerID, coupon.getId());
			super.couponsDao.decreaseCouponAmount(coupon.getId());
		} else {
			throw new CouponSystemException(ErrorType.INVALID_UPDATE,
					"error while running purchaseCoupon in CustomerFacade, coupon id does not exist");
		}
	}
	
	public ArrayList<Coupon> getCustomerCoupons() throws CouponSystemException{
		return super.couponsDao.getAllCouponsByCustomerID(customerID);
	}
	
	// get customer coupons by chosen category
	public ArrayList<Coupon> getCustomerCoupons(Category category) throws CouponSystemException{
		return super.couponsDao.getAllCouponsByCustomerIDAndCategoryID(customerID, category);
	}
	
	// get customer coupons by max price
	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException{
		return super.couponsDao.getAllCouponsByCustomerIDAndMaxPrice(customerID, maxPrice);
	}
	
	public Customer getCustomerDetails() throws CouponSystemException {
		return super.customersDao.getOneCustomer(customerID);
	}
	
}
