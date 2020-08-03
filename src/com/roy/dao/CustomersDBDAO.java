package com.roy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.roy.beans.Customer;
import com.roy.dao.interfaces.CustomersDAO;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import com.roy.handlers.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {

	private CouponsDBDAO couponsDBDAO;
	
	public CustomersDBDAO() {
		couponsDBDAO = new CouponsDBDAO();
	}
	
	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();
			
			String sqlStatement= "select * from customers where email = ? and password = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			result = preparedStatement.executeQuery();
			
			if(result.next()) {
				return true;
			}
			
		} catch(Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCustomerExists in CustomersDBDAO");
		} finally {
			if(connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return false;
	}

	@Override
	public void addCustomer(Customer customer) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();
			
			String sqlStatement= "insert into customers (first_name, last_name, email, password) "
					+ "values (?,?,?,?)";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getPassword());
			
			preparedStatement.executeUpdate();
			
		} catch(Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method addCustomer in CustomersDBDAO");
		} finally {
			if(connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}		
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();
			
			String sqlStatement= "update customers set first_name=?, last_name=?, email=?, password=? where id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getPassword());
			preparedStatement.setInt(5, customer.getId());
			
			preparedStatement.executeUpdate();
			
		} catch(Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method updateCustomer in CustomersDBDAO");
		} finally {
			if(connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();
			
			String sqlStatement= "delete from customers where id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			preparedStatement.executeUpdate();
			
		} catch(Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method deleteCustomer in CustomersDBDAO");
		} finally {
			if(connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}

	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Customer> customersList = new ArrayList<Customer>();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select * from customers";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			result = preparedStatement.executeQuery();

			if(!result.next()) {
				return customersList;
			}
			
			do {
				//extracting the data from db into a list
				customersList.add(extractCustomerFromResult(result));
			} while(result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getAllCustomers in CustomersDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return customersList;
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Customer customer = null;

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select * from customers where id = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractCustomerFromResult(result);
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getOneCustomer in CustomersDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return customer;
	}
	
	@Override
	public boolean isCustomerEmailExists(String email) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select * from customers where email = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setString(1, email);
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return true;
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCustomerEmailExists in CustomersDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return false;
	}
	
	@Override
	public int getCustomerIdByCredentials(String email, String password) throws CouponSystemException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select id from customers where email = ? and password = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return result.getInt("id");
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getCustomerIdByCredentials in CustomersDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return 0;
	}

	private Customer extractCustomerFromResult(ResultSet result) throws CouponSystemException{
		Customer customer= null;
		try {
			//extracting the data from db into a Customer object
			customer = new Customer();
			customer.setId(result.getInt("id"));
			customer.setFirstName(result.getString("first_name"));
			customer.setLastName(result.getNString("last_name"));
			customer.setEmail(result.getString("email"));
			customer.setPassword(result.getString("password"));
		customer.setCoupons(couponsDBDAO.getAllCouponsByCustomerID(result.getInt("id")));
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method extractCustomerFromResult in CustomersDBDAO");
		}
		return customer;
	}
	
}
