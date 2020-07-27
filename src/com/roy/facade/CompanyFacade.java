package com.roy.facade;

import java.util.ArrayList;

import com.roy.beans.Company;
import com.roy.beans.Coupon;
import com.roy.dao.interfaces.CompaniesDAO;
import com.roy.dao.interfaces.CouponsDAO;
import com.roy.dao.interfaces.CustomersDAO;
import com.roy.enums.Category;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;

public class CompanyFacade extends ClientFacade {
	
	private int companyID;
	
	public CompanyFacade(CustomersDAO customersDao, CouponsDAO couponsDao, CompaniesDAO companiesDao) {
		super(customersDao, couponsDao, companiesDao);
		this.companyID = 0;
	}
	
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		boolean isValidLogin = super.companiesDao.isCompanyExists(email, password);
		if (isValidLogin) {
			this.companyID = super.companiesDao.getCompanyIdByCredentials(email, password);
		}
		return isValidLogin;
	}
	
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (super.couponsDao.isCouponTitleExistsByCompanyID(coupon.getTitle(), companyID)) {
			throw new CouponSystemException(ErrorType.NAME_ALREADY_EXISTS,
					"error while running addCoupon in CompanyFacade, chosen coupon title is already exists");
		}
		super.couponsDao.addCoupon(coupon);
	}
	
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon currentCouponInDb = super.couponsDao.getOneCoupon(coupon.getId());
		if(currentCouponInDb != null) {
			if (coupon.getId() != currentCouponInDb.getId()) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCoupon in CompanyFacade, field 'id' is not allowed to be updated");
			}
			if (coupon.getCompanyID() != currentCouponInDb.getCompanyID()) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCoupon in CompanyFacade, field 'company_id' is not allowed to be updated");
			}
			super.couponsDao.updateCoupon(coupon);
		} else {
			throw new CouponSystemException(ErrorType.INVALID_UPDATE,
					"error while running updateCoupon in CompanyFacade, coupon id does not exist in the db");
		}
	}
	
	public void deleteCoupon(Coupon coupon) throws CouponSystemException {
		super.couponsDao.deletePurchasedCouponsByCouponID(coupon.getId());
		super.couponsDao.deleteCoupon(coupon.getId());
	}
	
	public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemException{
		return super.couponsDao.getAllCouponsByCompanyID(companyID);
	}
	
	public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemException{
		return super.couponsDao.getAllCouponsByCompanyIDAndCategoryID(companyID, category);
	}
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException{
		return super.couponsDao.getAllCouponsByCompanyIDAndMaxPrice(companyID, maxPrice);
	}
	
	public Company getCompanyDetails() throws CouponSystemException{
		return super.companiesDao.getOneCompany(companyID);
	}

}
