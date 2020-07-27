package com.roy.facade;

import java.util.ArrayList;
import com.roy.beans.Company;
import com.roy.beans.Customer;
import com.roy.dao.interfaces.CompaniesDAO;
import com.roy.dao.interfaces.CouponsDAO;
import com.roy.dao.interfaces.CustomersDAO;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import java.lang.Override;

public class AdminFacade extends ClientFacade {

	private static final String ADMIN_EMAIL = "admin@admin.com";
	private static final String ADMIN_PASSWORD = "admin";

	public AdminFacade(CustomersDAO customerDao, CouponsDAO couponsDao, CompaniesDAO companiesDao) {
		super(customerDao, couponsDao, companiesDao);
	}

	public void addCompany(Company company) throws CouponSystemException {
		if (super.companiesDao.isCompanyNameExists(company.getName())) {
			throw new CouponSystemException(ErrorType.NAME_ALREADY_EXISTS,
					"error while running addCompany in AdminFacade, chosen name is already exists");
		}
		if (super.companiesDao.isCompanyEmailExists(company.getEmail())) {
			throw new CouponSystemException(ErrorType.EMAIL_ALREADY_EXISTS,
					"error while running addCompany in AdminFacade, chosen email is already exists");
		}
		super.companiesDao.addCompany(company);
	}

	public void updateCompany(Company company) throws CouponSystemException {
		Company currentCompanyInDb = super.companiesDao.getOneCompany(company.getId());
		if(currentCompanyInDb != null) {
			if (company.getId() != currentCompanyInDb.getId()) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCompany in AdminFacade, field 'id' is not allowed to be updated");
			}
			if (!company.getName().equals(currentCompanyInDb.getName())) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCompany in AdminFacade, field 'name' is not allowed to be updated");
			}
			super.companiesDao.updateCompany(company);
		} else {
			throw new CouponSystemException(ErrorType.INVALID_UPDATE,
					"error while running updateCompany in AdminFacade, company id does not exist in the db");
		}
	}

	public void deleteCompany(int companyID) throws CouponSystemException {
		boolean isCompanyExists = super.companiesDao.getOneCompany(companyID) != null;
		if (isCompanyExists) {
			super.couponsDao.deletePurchasedCouponsByCompanyID(companyID);
			super.couponsDao.deleteCouponByCompanyId(companyID);
			super.companiesDao.deleteCompany(companyID);
		}
	}

	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		return super.companiesDao.getAllCompanies();
	}

	public Company getOneCompany(int companyID) throws CouponSystemException {
		return super.companiesDao.getOneCompany(companyID);
	}

	public void addCustomer(Customer customer) throws CouponSystemException {
		if (super.customersDao.isCustomerEmailExists(customer.getEmail())) {
			throw new CouponSystemException(ErrorType.EMAIL_ALREADY_EXISTS,
					"error while running addCompany in addCustomer, chosen email is already exists");
		}
		super.customersDao.addCustomer(customer);
	}

	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer currentCustomerInDb = super.customersDao.getOneCustomer(customer.getId());
		if(currentCustomerInDb != null) {
			if (customer.getId() != currentCustomerInDb.getId()) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCustomer in AdminFacade, field 'id' is not allowed to be updated");
			}
			super.customersDao.updateCustomer(customer);
		} else {
			throw new CouponSystemException(ErrorType.INVALID_UPDATE,
					"error while running updateCustomer in AdminFacade, customer id does not exist in the db");
		}
		
	}

	public void deleteCustomer(int customerID) throws CouponSystemException {
		super.couponsDao.deletePurchasedCouponsByCustomerID(customerID);
		super.customersDao.deleteCustomer(customerID);
	}

	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		return super.customersDao.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		return super.customersDao.getOneCustomer(customerID);
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		return email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
	}

}
