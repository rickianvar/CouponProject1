package dao;

import beans.Company;
import beans.Customer;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDAOImp implements CustomersDAO {

    private static final String QUERY_IS_CUSTOMER_EXIST = "select exists(SELECT * FROM couponsys.customers where EMAIL = ? and PASSWORD = ?) as res ";
    private static final String QUERY_INSERT_CUSTOMER = "INSERT INTO `couponsys`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES ( ? , ? , ? , ?)\n";
    private static final String QUERY_UPDATE_CUSTOMER = " UPDATE `couponsys`.`customers` SET `first_name` = ? , `last_name` = ? , `email` = ? , `password` = ?  WHERE (`id` = ? )";
    private static final String QUERY_DELETE_CUSTOMER = "DELETE FROM couponsys.customers WHERE ID = ? ";
    private static final String QUERY_ALL_CUSTOMERS = " SELECT * FROM couponsys.customers ";
    private static final String QUERY_ONE_CUSTOMER = "SELECT * FROM couponsys.customers where id=?";

    private static final String QUERY_IS_CUSTOMER_EMAIL_EXIST = "select exists(SELECT * FROM couponsys.customers where EMAIL = ? ) as res ";
    private static final String QUERY_GET_CUSTOMER_ID_BY_EMAIL_PASS = "SELECT * FROM couponsys.customers where email = ? AND password = ? ";

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException {
        boolean isExist = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        List<?> res = JDBCUtils.executeResults(QUERY_IS_CUSTOMER_EXIST, map);
        for (Object row : res) {
            isExist = ResultsUtils.fromHashMapToBoolean((HashMap<String, Object>) row);
            break;
        }

        return isExist;

    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());

        JDBCUtils.execute(QUERY_INSERT_CUSTOMER, map);

    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException {
//TODO   WHAT TO PUT IN ID - UPDATE ACCORDING TO ID ?

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        map.put(5, customerId);

        JDBCUtils.execute(QUERY_UPDATE_CUSTOMER, map);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        JDBCUtils.execute(QUERY_DELETE_CUSTOMER, map);

    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, InterruptedException {

        List<Customer> customers = new ArrayList<>();

        List<?> res = JDBCUtils.executeResults(QUERY_ALL_CUSTOMERS);

        for (Object row : res) {
            customers.add(ResultsUtils.fromHashMapToCustomer((HashMap<String, Object>) row));
        }

        return customers;

    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException {

        Customer customer = null;

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, customerId);
        List<?> res = JDBCUtils.executeResults(QUERY_ONE_CUSTOMER, map);
        for (Object row : res) {
            customer = (ResultsUtils.fromHashMapToCustomer((HashMap<String, Object>) row));
            break;
        }
        return customer;

    }

    public boolean isCustomerEmailExists(String email) throws SQLException, InterruptedException {
        boolean isExist = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);

        List<?> res = JDBCUtils.executeResults(QUERY_IS_CUSTOMER_EMAIL_EXIST, map);
        for (Object row : res) {
            isExist = ResultsUtils.fromHashMapToBoolean((HashMap<String, Object>) row);
            break;
        }

        return isExist;

    }

    @Override
    public int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException, InterruptedException {
        int customerId = 0;

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, email);
        map.put(2, password);

        List<?> res = JDBCUtils.executeResults(QUERY_GET_CUSTOMER_ID_BY_EMAIL_PASS, map);
        for (Object row : res) {
            customerId = (ResultsUtils.fromHashMapToInt((HashMap<String, Object>) row));
            break;
        }
        return customerId;
    }
}
