package dao;

import beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomersDAO {

    boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;

    void addCustomer(Customer customer) throws SQLException, InterruptedException;

    void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException;

    void deleteCustomer(int customerId) throws SQLException, InterruptedException;

    List<Customer> getAllCustomers() throws SQLException, InterruptedException;

    Customer getOneCustomer(int customerId) throws SQLException, InterruptedException;

    boolean isCustomerEmailExists(String email) throws SQLException, InterruptedException;

    int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException, InterruptedException;
}
