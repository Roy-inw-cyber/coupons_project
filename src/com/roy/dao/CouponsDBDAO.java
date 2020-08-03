package com.roy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.roy.beans.Coupon;
import com.roy.dao.interfaces.CouponsDAO;
import com.roy.enums.Category;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import com.roy.handlers.ConnectionPool;

public class CouponsDBDAO implements CouponsDAO {

	@Override
	public boolean isCouponExists(int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select * from coupons where id = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, couponID);
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return true;
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method isCouponExists in CouponsDBDAO");
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
	public boolean isAlreadyPurchasedCoupon(int couponID, int customerID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select * from customers_vs_coupons where customer_id=? and coupon_id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, couponID);
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return true;
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method isAlreadyPurchasedCoupon in CouponsDBDAO");
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
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			//coupon id is defined as primary key and auto increment
			String sqlStatement = "insert into coupons (company_id, category_id, title, description, start_date, end_date, amount, price, image) "
					+ "values (?,?,?,?,?,?,?,?,?)";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, coupon.getCompanyID());
			preparedStatement.setInt(2, getCategoryIdByName(coupon.getCategory()));
			preparedStatement.setString(3, coupon.getTitle());
			preparedStatement.setString(4, coupon.getDescription());
			preparedStatement.setDate(5, coupon.getStartDate());
			preparedStatement.setDate(6, coupon.getEndDate());
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());

			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method addCoupon in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "update coupons set category_id=?, title=?, description=?, start_date=?, end_date=?, "
					+ "amount=?, price=?, image=? where id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, getCategoryIdByName(coupon.getCategory()));
			preparedStatement.setString(2, coupon.getTitle());
			preparedStatement.setString(3, coupon.getDescription());
			preparedStatement.setDate(4, coupon.getStartDate());
			preparedStatement.setDate(5, coupon.getEndDate());
			preparedStatement.setInt(6, coupon.getAmount());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.setInt(9, coupon.getId());

			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method updateCoupon in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public void decreaseCouponAmount(int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "update coupons set amount=amount-1 where id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, couponID);
			
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method decreaseCouponAmount in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "delete from coupons where id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, couponID);
			
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deleteCoupon in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from coupons left join categories on coupons.category_id = categories.id";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}

			do {
				//extracting the data from db into a list
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCoupons in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Coupon coupon = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from coupons left join categories on coupons.category_id = categories.id where coupons.id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, couponID);
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractCouponFromResult(result);
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getOneCoupon in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return coupon;
	}

	private int getCategoryIdByName(Category category) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select id from categories where name=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setString(1,category.name().toLowerCase());
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return result.getInt("id");
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getCategoryIdByName in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return 0;
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "insert into customers_vs_coupons values (?,?)";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, couponID);
			
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method addCouponPurchase in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "delete from customers_vs_coupons where customer_id=? and coupon_id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(1, couponID);
			
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deleteCouponPurchase in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}

	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCustomerID(int customerID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from customers_vs_coupons left join coupons on coupons.id = customers_vs_coupons.coupon_id "
					+ "left join categories on coupons.category_id = categories.id "
					+ "where customers_vs_coupons.customer_id = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}

			do {
				//extracting the data from db into a list
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCouponsByCustomerID in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCustomerIDAndCategoryID(int customerID, Category category)
			throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from customers_vs_coupons left join coupons on coupons.id = customers_vs_coupons.coupon_id "
					+ "left join categories on coupons.category_id = categories.id "
					+ "where customers_vs_coupons.customer_id = ? and coupons.category_id = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, getCategoryIdByName(category));
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}

			do {
				//extracting the data from db into a list
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCouponsByCustomerIDAndCategoryID in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCustomerIDAndMaxPrice(int customerID, double maxPrice)
			throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from customers_vs_coupons left join coupons on coupons.id = customers_vs_coupons.coupon_id "
					+ "left join categories on coupons.category_id = categories.id "
					+ "where customers_vs_coupons.customer_id = ? and coupons.price < ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			preparedStatement.setDouble(2, maxPrice);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}

			do {
				//extracting the data from db into a list
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCouponsByCustomerIDAndMaxPrice in CouponsDBDAO");
		} finally {
			if (connection != null) {
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from coupons left join categories on coupons.category_id = categories.id where company_id = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, companyID);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}

			do {
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCouponsByCompanyID in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCompanyIDAndCategoryID(int companyID, Category category)
			throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from coupons left join categories on coupons.category_id = categories.id "
					+ "where company_id = ? and coupons.category_id = ? ";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, companyID);
			preparedStatement.setInt(2, getCategoryIdByName(category));
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}
			do {
				//extracting the data from db into a list
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCouponsByCompanyIDAndCategoryID in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public ArrayList<Coupon> getAllCouponsByCompanyIDAndMaxPrice(int companyID, double maxPrice)
			throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select coupons.*, categories.name category_name "
					+ "from coupons left join categories on coupons.category_id = categories.id "
					+ "where company_id = ? and coupons.price < ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, companyID);
			preparedStatement.setDouble(2, maxPrice);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return couponsList;
			}
			do {
				//extracting the data from db into a list
				couponsList.add(extractCouponFromResult(result));
			} while (result.next());

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getAllCouponsByCompanyIDAndMaxPrice in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList;
	}

	@Override
	public void deleteCouponByCompanyId(int companyID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "delete from coupons where company_id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, companyID);
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deleteCouponByCompanyId in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}

	@Override
	public void deleteExpiredCoupons() throws CouponSystemException {
		try {
			//get a list of the expired coupons comma delimited
			String expiredcouponsListCommaDelimited = getExpiredCouponIDs();
			deletePurchasedCouponsByCouponsList(expiredcouponsListCommaDelimited);
			deleteCouponsByList(expiredcouponsListCommaDelimited);
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deleteExpiredCoupons in CouponsDBDAO");
		}
	}
	
	@Override
	public void deletePurchasedCouponsByCompanyID(int companyID) throws CouponSystemException {
		try {
			deletePurchasedCouponsByCouponsList(getCouponIDsByCompanyIDCommaDelimited(companyID));
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deleteExpiredPurchasedCoupons in CouponsDBDAO");
		}
	}
	
	private void deletePurchasedCouponsByCouponsList(String couponsListCommaDelimited) throws CouponSystemException {
		if (couponsListCommaDelimited != null) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				// get connection from the pool
				connection = ConnectionPool.getInstance().getConnection();

				String sqlStatement = String.format("delete from customers_vs_coupons where coupon_id in (%s)",
						couponsListCommaDelimited);
				
				//combine between the syntax and the connection
				preparedStatement = connection.prepareStatement(sqlStatement);
				
				preparedStatement.executeUpdate();

			} catch (Exception exception) {
				throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
						"error running method deletePurchasedCouponsByCouponsList in CouponsDBDAO");
			} finally {
				if (connection != null) {
					//closing all resources
					ConnectionPool.closeResources(preparedStatement);
					ConnectionPool.getInstance().restoreConnection(connection);
				}
			}
		}
	}
	
	private void deleteCouponsByList(String couponsListCommaDelimited) throws CouponSystemException {
		if (couponsListCommaDelimited != null) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				//get connection from the pool
				connection = ConnectionPool.getInstance().getConnection();

				String sqlStatement = String.format("delete from coupons where id in (%s)",
						couponsListCommaDelimited);
				
				//combine between the syntax and the connection
				preparedStatement = connection.prepareStatement(sqlStatement);
				
				preparedStatement.executeUpdate();

			} catch (Exception exception) {
				throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
						"error running method deleteCouponsByList in CouponsDBDAO");
			} finally {
				if (connection != null) {
					//closing all resources
					ConnectionPool.closeResources(preparedStatement);
					ConnectionPool.getInstance().restoreConnection(connection);
				}
			}
		}
	}

	private String getCouponIDsByCompanyIDCommaDelimited(int companyID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		StringBuilder couponsList = new StringBuilder();

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select id from coupons where company_id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, companyID);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return null;
			}

			do {
				couponsList.append(String.format("%s,", result.getInt("id")));
			} while (result.next());
			//delete last unnecessary comma 
			couponsList.deleteCharAt(couponsList.length() - 1);

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getCouponIDsByCompanyIDCommaDelimited in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList.toString();
	}
	
	
	private String getExpiredCouponIDs() throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		StringBuilder couponsList = new StringBuilder();

		try {
			//get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select id from coupons where end_date<date(now())";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			result = preparedStatement.executeQuery();

			if (!result.next()) {
				return null;
			}

			do {
				couponsList.append(String.format("%s,", result.getInt("id")));
			} while (result.next());
			//delete last unnecessary comma
			couponsList.deleteCharAt(couponsList.length() - 1);

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method getExpiredCouponIDs in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement, result);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
		return couponsList.toString();
	}

	@Override
	public void deletePurchasedCouponsByCustomerID(int customerID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "delete from customers_vs_coupons where customer_id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, customerID);
			
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deletePurchasedCouponsByCustomerID in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}
	

	@Override
	public boolean isCouponTitleExistsByCompanyID(String couponTitle, int companyID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "select * from coupons where company_id = ? and title = ?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, companyID);
			preparedStatement.setString(2, couponTitle);
			
			result = preparedStatement.executeQuery();

			if (result.next()) {
				return true;
			}

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
					"error running method isCouponNameExistsByCompanyID in CouponsDBDAO");
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
	public void deletePurchasedCouponsByCouponID(int couponID) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// get connection from the pool
			connection = ConnectionPool.getInstance().getConnection();

			String sqlStatement = "delete from customers_vs_coupons where coupon_id=?";
			
			//combine between the syntax and the connection
			preparedStatement = connection.prepareStatement(sqlStatement);
			
			//replace the question marks in the statement above with the relevant data
			preparedStatement.setInt(1, couponID);
			
			preparedStatement.executeUpdate();

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method deletePurchasedCouponsByCouponID in CouponsDBDAO");
		} finally {
			if (connection != null) {
				//closing all resources
				ConnectionPool.closeResources(preparedStatement);
				ConnectionPool.getInstance().restoreConnection(connection);
			}
		}
	}
	

	private Coupon extractCouponFromResult(ResultSet result) throws CouponSystemException {
		Coupon coupon = null;
		try {
			//extracting the data from db into a Coupon object
			coupon = new Coupon();
			coupon.setId(result.getInt("id"));
			coupon.setCompanyID(result.getInt("company_id"));
			coupon.setCategory(Category.valueOf(result.getString("category_name").toUpperCase()));
			coupon.setTitle(result.getString("title"));
			coupon.setDescription(result.getString("description"));
			coupon.setStartDate(result.getDate("start_date"));
			coupon.setEndDate(result.getDate("end_date"));
			coupon.setAmount(result.getInt("amount"));
			coupon.setPrice(result.getDouble("price"));
			coupon.setImage(result.getString("image"));

		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
					"error running method extractCouponFromResult in CouponsDBDAO");
		}
		return coupon;
	}

}
