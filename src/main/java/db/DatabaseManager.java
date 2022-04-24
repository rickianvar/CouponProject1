package db;

import beans.Category;
import dao.CategoriesDAO;
import dao.CategoriesDAOImp;

import java.sql.SQLException;

public class DatabaseManager {

    private static CategoriesDAO categoriesDAO = new CategoriesDAOImp();

    private static final String CREATE_SCHEMA = "create schema couponsys";
    private static final String DROP_SCHEMA = "drop schema couponsys";
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE `couponsys`.`companies` (\n" +
            " `id` INT NOT NULL AUTO_INCREMENT, \n" +
            "`name` VARCHAR(20) NOT NULL,\n" +
            "`email` VARCHAR(40) NOT NULL,\n" +
            "`password` VARCHAR(10) NOT NULL,\n" +
            " PRIMARY KEY (`ID`));";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE `couponsys`.`customers` (\n" +
            "`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "`first_name` VARCHAR(10) NOT NULL,\n" +
            "`last_name` VARCHAR(15) NOT NULL,\n" +
            "`email` VARCHAR(40) NOT NULL,\n" +
            "`password` VARCHAR(10) NOT NULL,\n" +
            " PRIMARY KEY (`id`)); ";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE `couponsys`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(15) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));\n";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE `couponsys`.`coupons` (\n" +
            "  `ID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `COMPANY_ID` INT NOT NULL,\n" +
            "  `CATEGORY_ID` INT NOT NULL,\n" +
            "  `TITLE` VARCHAR(20) NOT NULL,\n" +
            "  `DESCRIPTION` VARCHAR(45) NOT NULL,\n" +
            "  `START_DATE` DATE NOT NULL,\n" +
            "  `END_DATE` DATE NOT NULL,\n" +
            "  `AMOUNT` INT NOT NULL,\n" +
            "  `PRICE` DOUBLE NOT NULL,\n" +
            "  `IMAGE` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`),\n" +
            "  INDEX `company_id_idx` (`COMPANY_ID` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`CATEGORY_ID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`COMPANY_ID`)\n" +
            "    REFERENCES `couponsys`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`CATEGORY_ID`)\n" +
            "    REFERENCES `couponsys`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    private static final String CREATE_TABLE_CUST_VS_COUPON = "CREATE TABLE `couponsys`.`customers_vs_coupons` (\n" +
            "  `CUSTOMER_ID` INT NOT NULL,\n" +
            "  `COUPON_ID` INT NOT NULL,\n" +
            "  PRIMARY KEY (`CUSTOMER_ID`, `COUPON_ID`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customer_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `couponsys`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `couponsys`.`coupons` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    public static void databaseStrategy() throws SQLException, InterruptedException {
        try {
            JDBCUtils.execute(DROP_SCHEMA);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JDBCUtils.execute(CREATE_SCHEMA);
        JDBCUtils.execute(CREATE_TABLE_COMPANIES);
        JDBCUtils.execute(CREATE_TABLE_CUSTOMERS);
        JDBCUtils.execute(CREATE_TABLE_CATEGORIES);
        JDBCUtils.execute(CREATE_TABLE_COUPONS);
        JDBCUtils.execute(CREATE_TABLE_CUST_VS_COUPON);

        for (Category c : Category.values()) {
            categoriesDAO.addCategory(c.name());
        }
    }

}
