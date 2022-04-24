package dao;

import beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO {

    boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException;

    void addCompany(Company company) throws SQLException, InterruptedException;

    void updateCompany(int companyId, Company company) throws SQLException, InterruptedException;

    void deleteCompany(int companyID) throws SQLException, InterruptedException;

    List<Company> getAllCompanies() throws SQLException, InterruptedException;

    Company getOneCompany(int companyID) throws SQLException, InterruptedException;

    boolean isCompNameEmailExists(String name, String email) throws SQLException, InterruptedException;

    int getCompanyIdByEmailAndPassword(String email, String password) throws SQLException, InterruptedException;

}
