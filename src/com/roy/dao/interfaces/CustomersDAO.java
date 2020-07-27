package com.roy.dao.interfaces;

import java.util.ArrayList;
import com.roy.beans.Customer;
import com.roy.exception.CouponSystemException;

public interface CustomersDAO {

	boolean isCustomerExists(String email, String password) throws CouponSystemException;
	void addCustomer(Customer customer) throws CouponSystemException;
	void updateCustomer(Customer customer) throws CouponSystemException;
	void deleteCustomer(int customerID) throws CouponSystemException;
	ArrayList<Customer> getAllCustomers() throws CouponSystemException;
	Customer getOneCustomer(int customerID) throws CouponSystemException;
	boolean isCustomerEmailExists(String email) throws CouponSystemException;
	int getCustomerIdByCredentials(String email, String password) throws CouponSystemException;

}
