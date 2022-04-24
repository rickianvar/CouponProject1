package db;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.sql.Date;
import java.util.HashMap;

public class ResultsUtils {

    public static boolean fromHashMapToBoolean(HashMap<String, Object> map) {
        long res = (long) map.get("res");
        return (res == 1);
    }

    // colom name as created capital \ small letter
    public static Company fromHashMapToCompany(HashMap<String, Object> map) {

        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");

        Company company = new Company(id, name, email, password);
        return company;
    }

    public static Customer fromHashMapToCustomer(HashMap<String, Object> map) {

        int id = (int) map.get("id");
        String first_name = (String) map.get("first_name");
        String last_name = (String) map.get("last_name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");

        Customer customer = new Customer(id, first_name, last_name, email, password);
        return customer;
    }

    public static Coupon fromHashMapToCoupons(HashMap<String, Object> map) {

        int id = (int) map.get("ID");
        int company_id = (int) map.get("COMPANY_ID");
        int category_id = (int) map.get("CATEGORY_ID");
        String title = (String) map.get("TITLE");
        String description = (String) map.get("DESCRIPTION");
        Date start_date = (Date) map.get("START_DATE");
        Date end_date = (Date) map.get("END_DATE");
        int amount = (int) map.get("AMOUNT");
        double price = (double) map.get("PRICE");
        String image = (String) map.get("IMAGE");


        Coupon coupon = new Coupon(id, company_id, Category.values()[category_id - 1], title, description, start_date, end_date, amount, price, image);
        return coupon;
    }

    public static int fromHashMapToInt(HashMap<String, Object> map) {
        return (int) map.get("id");

    }

}
