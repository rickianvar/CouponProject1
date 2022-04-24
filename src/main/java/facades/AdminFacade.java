package facades;

import beans.Company;
import beans.Customer;
import dao.CompaniesDAO;
import dao.CompaniesDAOImp;
import exception.SystemException;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
        super();
    }


    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws SQLException, InterruptedException, SystemException {

        if (this.companyDAO.isCompNameEmailExists(company.getName(), company.getEmail())) {
            throw new SystemException("COMPANY NAME OR EMAIL ALREADY EXIST");
        }
        this.companyDAO.addCompany(company);

    }

    public void updateCompany(Company company) throws SQLException, InterruptedException, SystemException {

        Company inputCompany = this.companyDAO.getOneCompany(company.getId());
        if (inputCompany == null) {
            throw new SystemException(" CAN NOT UPDATE - ID NOT EXIST");
        }
        if (!inputCompany.getName().equals(company.getName())) {
            throw new SystemException(" CAN NOT UPDATE name");
        }
        this.companyDAO.updateCompany(company.getId(), company);

    }

    public void deleteCompany(int companyId) throws SQLException, InterruptedException {
        this.couponsDAO.deleteCouponPurchaseByCompId(companyId);
        this.couponsDAO.deleteCoupon(companyId);
        this.companyDAO.deleteCompany(companyId);

    }

    public List<Company> getAllCompanies() throws SQLException, InterruptedException {

        return this.companyDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws SQLException, InterruptedException {
        return this.companyDAO.getOneCompany(companyId);

    }

    public void addCustomer(Customer customer) throws SQLException, InterruptedException, SystemException {

        if (this.customersDAO.isCustomerEmailExists(customer.getEmail())) {
            throw new SystemException(" CUSTOMER EMAIL EXIST - NO ADD CUSTOMER ");
        }
        this.customersDAO.addCustomer(customer);

    }

    public void updateCustomer(Customer customer) throws SQLException, InterruptedException, SystemException {

        Customer inputCustomer = this.customersDAO.getOneCustomer(customer.getId());

        if (inputCustomer == null) {
            throw new SystemException(" CAN NOT UPDATE customer- ID NOT EXIST");
        }

        this.customersDAO.updateCustomer(customer.getId(), customer);

    }

    public void deleteCustomer(int customerId) throws SQLException, InterruptedException {

        this.couponsDAO.deleteCouponPurchaseByCustId(customerId);
        this.customersDAO.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() throws SQLException, InterruptedException {

        return this.customersDAO.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException {

        return this.customersDAO.getOneCustomer(customerId);
    }


}
