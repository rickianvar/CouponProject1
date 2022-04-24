package dao;

import db.JDBCUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoriesDAOImp implements CategoriesDAO {

    private static final String QUERY_INSERT_CATEGORY = "INSERT INTO `couponsys`.`categories` (`name`) VALUES (?);\n";

    @Override
    public void addCategory(String name) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, name);

        JDBCUtils.execute(QUERY_INSERT_CATEGORY, map);
    }
}
