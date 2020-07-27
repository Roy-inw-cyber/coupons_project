package com.roy.handlers;

import com.roy.dao.CompaniesDBDAO;
import com.roy.dao.CouponsDBDAO;
import com.roy.dao.CustomersDBDAO;
import com.roy.dao.interfaces.CompaniesDAO;
import com.roy.dao.interfaces.CouponsDAO;
import com.roy.dao.interfaces.CustomersDAO;
import com.roy.enums.ClientType;
import com.roy.exception.CouponSystemException;
import com.roy.facade.AdminFacade;
import com.roy.facade.ClientFacade;
import com.roy.facade.CompanyFacade;
import com.roy.facade.CustomerFacade;

public class LoginManager {

    private static LoginManager instance = new LoginManager();
    private CustomersDAO customersDAO;
    private CompaniesDAO companiesDAO;
    private CouponsDAO couponsDAO;

    public static LoginManager getInstance() {
        return instance;
    }

    private LoginManager() {
        customersDAO = new CustomersDBDAO();
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
        switch (clientType) {
            case COMPANY:
                CompanyFacade companyFacade = new CompanyFacade(customersDAO, couponsDAO, companiesDAO);
                if (companyFacade.login(email, password)) {
                    return companyFacade;
                }
                return null;
            case CUSTOMER:
                CustomerFacade customerFacade = new CustomerFacade(customersDAO, couponsDAO, companiesDAO);
                if (customerFacade.login(email, password)) {
                    return customerFacade;
                }
                return null;
            case ADMINISTRATOR:
                AdminFacade adminFacade = new AdminFacade(customersDAO, couponsDAO, companiesDAO);
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
                return null;
        }
        return null;
    }
}
