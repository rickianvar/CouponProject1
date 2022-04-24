package dao;

import beans.Company;

import java.sql.SQLException;

public interface CategoriesDAO {

    void addCategory(String name) throws SQLException, InterruptedException;

}
