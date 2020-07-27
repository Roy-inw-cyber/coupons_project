package com.roy.dao.interfaces;

import java.util.ArrayList;

import com.roy.beans.Company;
import com.roy.exception.CouponSystemException;

public interface CompaniesDAO {
	
	boolean isCompanyExists(String email, String password) throws CouponSystemException;
	void addCompany(Company company) throws CouponSystemException;
	void updateCompany(Company company) throws CouponSystemException;
	void deleteCompany(int companyID) throws CouponSystemException;
	ArrayList<Company> getAllCompanies() throws CouponSystemException;
	Company getOneCompany(int companyID) throws CouponSystemException;
	boolean isCompanyNameExists(String name) throws CouponSystemException;
	boolean isCompanyEmailExists(String email) throws CouponSystemException;
	int getCompanyIdByCredentials(String email, String password) throws CouponSystemException;
	
}
