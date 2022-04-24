package dao;

import beans.Company;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDAOImp implements CompaniesDAO {

    private static final String QUERY_IS_COMPANY_EXIST = "select exists(SELECT * FROM couponsys.companies where EMAIL = ? and PASSWORD = ?) as res ";
    private static final String QUERY_INSERT_COMPANY = "INSERT INTO `couponsys`.`companies` (`NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE_COMPANY = " UPDATE `couponsys`.`companies` SET `NAME` = ?, `EMAIL` = ?, `PASSWORD` = ? WHERE (`ID` = ?);\n";
    private static final String QUERY_DELETE_COMPANY = "DELETE FROM couponsys.companies WHERE ID = ? ";
    private static final String QUERY_ALL_COMPANIES = " SELECT * FROM couponsys.companies ";
    private static final String QUERY_ONE_COMPANY = "SELECT * FROM couponsys.companies where id=?";

    private static final String QUERY_IS_COMPNAMEEMAIL_EXIST = "select exists(SELECT * FROM couponsys.companies where NAME =? or EMAIL = ?) as res ;";
    private static final String QUERY_GET_COMPANY_ID_BY_EMAIL_PASS = "SELECT id FROM couponsys.companies where email = ? AND password = ? ; ";

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException {

        boolean isExist = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        List<?> res = JDBCUtils.executeResults(QUERY_IS_COMPANY_EXIST, map);
        for (Object row : res) {
            isExist = ResultsUtils.fromHashMapToBoolean((HashMap<String, Object>) row);
            break;
        }

        return isExist;

    }

    @Override
    public void addCompany(Company company) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());

        JDBCUtils.execute(QUERY_INSERT_COMPANY, map);

    }

    @Override
    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException {
//TODO   WHAT TO PUT IN ID - UPDATE ACCORDING TO ID ?

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, companyId);

        JDBCUtils.execute(QUERY_UPDATE_COMPANY, map);

    }

    @Override
    public void deleteCompany(int companyId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        JDBCUtils.execute(QUERY_DELETE_COMPANY, map);

    }

    @Override
    public List<Company> getAllCompanies() throws SQLException, InterruptedException {

        List<Company> companies = new ArrayList<>();

        List<?> res = JDBCUtils.executeResults(QUERY_ALL_COMPANIES);

        for (Object row : res) {
            companies.add(ResultsUtils.fromHashMapToCompany((HashMap<String, Object>) row));
        }

        return companies;
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException, InterruptedException {
        Company company = null;

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, companyId);
        List<?> res = JDBCUtils.executeResults(QUERY_ONE_COMPANY, map);
        for (Object row : res) {
            company = (ResultsUtils.fromHashMapToCompany((HashMap<String, Object>) row));
            break;
        }
        return company;

    }


    public boolean isCompNameEmailExists(String name, String email) throws SQLException, InterruptedException {

        boolean isExist = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, name);
        map.put(2, email);

        List<?> res = JDBCUtils.executeResults(QUERY_IS_COMPNAMEEMAIL_EXIST, map);
        for (Object row : res) {
            isExist = ResultsUtils.fromHashMapToBoolean((HashMap<String, Object>) row);
            break;
        }

        return isExist;

    }

    @Override
    public int getCompanyIdByEmailAndPassword(String email, String password) throws SQLException, InterruptedException {
        int companyId = 0;

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, email);
        map.put(2, password);

        List<?> res = JDBCUtils.executeResults(QUERY_GET_COMPANY_ID_BY_EMAIL_PASS, map);
        for (Object row : res) {
            companyId = (ResultsUtils.fromHashMapToInt((HashMap<String, Object>) row));
            break;
        }
        return companyId;
    }

}
