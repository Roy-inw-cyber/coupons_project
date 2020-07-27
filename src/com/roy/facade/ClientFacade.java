package com.roy.facade;

import com.roy.dao.interfaces.CompaniesDAO;
import com.roy.dao.interfaces.CouponsDAO;
import com.roy.dao.interfaces.CustomersDAO;
import com.roy.exception.CouponSystemException;

public abstract class ClientFacade {

	protected CustomersDAO customersDao;
	protected CouponsDAO couponsDao;
	protected CompaniesDAO companiesDao;
	
	public ClientFacade(CustomersDAO customersDao, CouponsDAO couponsDao, CompaniesDAO companiesDao) {
		this.customersDao = customersDao;
		this.couponsDao = couponsDao;
		this.companiesDao = companiesDao;
	}
	
	public abstract boolean login(String email, String password) throws CouponSystemException;
	
	
}
