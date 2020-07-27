package com.roy;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.roy.beans.Company;
import com.roy.beans.Coupon;
import com.roy.beans.Customer;
import com.roy.enums.Category;
import com.roy.enums.ClientType;
import com.roy.exception.CouponSystemException;
import com.roy.facade.AdminFacade;
import com.roy.facade.CompanyFacade;
import com.roy.facade.CustomerFacade;
import com.roy.handlers.ConnectionPool;
import com.roy.handlers.LoginManager;
import com.roy.jobs.CouponExpirationDailyJob;

public class Test {

	public static void main(String[] args) throws Exception {
		Thread couponExpirationDailyJob = new Thread(new CouponExpirationDailyJob());
		couponExpirationDailyJob.start();
		
		Company company = new Company("Takamine", "takamine@guitars.com", "guitars");
		Company updatedCompany = new Company(7, "Takamine", "takamine@guitars.com", "gui7462mj");
		Customer customer = new Customer("guy", "gold", "guygo@gmail.com", "hghgjk");
		Customer updatedCustomer = new Customer(12, "guy", "gold", "guygo@gmail.com", "154585");
		Coupon coupon = new Coupon(4, Category.ELECTRICITY, "70% off classic guitars", 
				"best collections of electic guitars with prices that you won't find", 
				new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-27").getTime()), 
				new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01").getTime()), 
				20, 1600, "https://i.ebayimg.com/images/g/eewAAOSwbO5e6Ya9/s-l640.jpg");
		Coupon updatedCoupon = new Coupon(5,7, Category.ELECTRICITY, "30% off electric guitars", 
		"best collections of electic guitars with prices that you won't find", 
		new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-27").getTime()), 
		new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01").getTime()), 
		20, 1600, "https://i.ebayimg.com/images/g/eewAAOSwbO5e6Ya9/s-l640.jpg");
		
		LoginManager loginManager = LoginManager.getInstance();
		
		AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
//		adminFacade.addCompany(company);
//		adminFacade.updateCompany(updatedCompany);
//		adminFacade.deleteCompany(7);
//		System.out.println(adminFacade.getAllCompanies());
//		System.out.println(adminFacade.getOneCompany(1));
//		adminFacade.addCustomer(customer);
//		adminFacade.updateCustomer(updatedCustomer);
//		adminFacade.deleteCustomer(9);
//		System.out.println(adminFacade.getAllCustomers());
//		System.out.println(adminFacade.getOneCustomer(11));
//		
		CompanyFacade companyFacade = (CompanyFacade) loginManager.login("elite@manage.com", "eli122", ClientType.COMPANY);
//		companyFacade.addCoupon(coupon);
//		companyFacade.updateCoupon(updatedCoupon);
//		companyFacade.deleteCoupon(updatedCoupon);
//		System.out.println(companyFacade.getCompanyCoupons());
//		System.out.println(companyFacade.getCompanyCoupons(Category.FOOD));
//		System.out.println(companyFacade.getCompanyCoupons(1601));
//		System.out.println(companyFacade.getCompanyDetails());
//		
		CustomerFacade customerFacade = (CustomerFacade) loginManager.login("binya1950@gmail.com", "17668fkdk", ClientType.CUSTOMER);
//		customerFacade.purchaseCoupon(updatedCoupon);
//		System.out.println(customerFacade.getCustomerCoupons());
//		System.out.println(customerFacade.getCustomerCoupons(Category.FOOD));
//		System.out.println(customerFacade.getCustomerCoupons(1605));
//		System.out.println(customerFacade.getCustomerDetails());
		
		couponExpirationDailyJob.stop();
		ConnectionPool.getInstance().closeAllConnections();
	}
	
}
