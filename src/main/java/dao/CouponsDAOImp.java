package dao;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDAOImp implements CouponsDAO {

    private static final String QUERY_INSERT_COUPONS = "INSERT INTO `couponsys`.`coupons` (`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES ( ? , ? , ? , ? , ? , ?, ?,  ?, ?);\n";
    private static final String QUERY_UPDATE_COUPON = "UPDATE `couponsys`.`coupons` SET `COMPANY_ID` = ?,`CATEGORY_ID` = ?, `TITLE` = ?, `DESCRIPTION` = ?, `START_DATE` = ? , `END_DATE` = ? , `AMOUNT` = ?, `PRICE` = ? , `IMAGE` = ?  WHERE (`ID` = ?);";
    private static final String QUERY_DELETE_COUPON = "DELETE FROM couponsys.coupons WHERE ID = ? ";

    private static final String QUERY_ALL_COUPONS = " SELECT * FROM couponsys.coupons ";
    private static final String QUERY_ONE_COUPON = "SELECT * FROM couponsys.coupons where id=?";

    private static final String QUERY_INSERT_CUSTOMER_VS_COUPONS = "INSERT INTO `couponsys`.`customers_vs_coupons` (`CUSTOMER_ID`, `COUPON_ID`) VALUES (? , ?);";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS = "DELETE FROM `couponsys`.`customers_vs_coupons` WHERE (`CUSTOMER_ID` = ? ) and (`COUPON_ID` = ? );";
    private static final String QUERY_DELETE_COUPONS_BY_COMP_ID = "DELETE FROM couponsys.coupons WHERE COMPANY_ID = ? ";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS_BY_COMPID = "delete from couponsys.customers_vs_coupons where customer_id <> 0 and COUPON_ID in ( select id  from couponsys.coupons where COMPANY_ID =?) ;";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS_BY_CUSTID = " delete from couponsys.customers_vs_coupons where customer_id  = ? ";
    private static final String QUERY_DELETE_CUSTOMER_VS_COUPONS_BY_COUPNID = " delete from couponsys.customers_vs_coupons where coupon_id  = ? ";

    private static final String QUERY_IS_COMPANY_TITLE_EXIST = "select exists(SELECT * FROM couponsys.coupons where COMPANY_ID = ? and TITLE = ? ) as res ";
    private static final String QUERY_ALL_COMPANY_COUPONS = " SELECT * FROM couponsys.coupons WHERE COMPANY_ID = ? ";
    private static final String QUERY_ALL_COMPANY_CATEGORY_COUPONS = " SELECT * FROM couponsys.coupons WHERE COMPANY_ID = ? and CATEGORY_ID = ?";
    private static final String QUERY_ALL_COMPANY_MAX_PRICE_COUPONS = "SELECT * FROM couponsys.coupons WHERE COMPANY_ID = ? and price <= ? ";

    private static final String QUERY_IS_CUST_COUPON_CUSTOMER_VS_COUP_EXIST = "select exists(SELECT * FROM couponsys.customers_vs_coupons WHERE CUSTOMER_ID = ?  AND COUPON_ID = ?) as res ;";
    private static final String QUERY_GET_ALL_CUSTOMER_COUPONS = "SELECT *  FROM couponsys.coupons where id in ( SELECT COUPON_ID FROM couponsys.customers_vs_coupons  where CUSTOMER_ID = ? ) ;\n";
    private static final String QUERY_GET_ALL_CUSTOMER_COUPONS_BY_CATGORY = "SELECT *  FROM couponsys.coupons where CATEGORY_ID = ? and id in ( SELECT COUPON_ID FROM couponsys.customers_vs_coupons  where CUSTOMER_ID = ? ) ;\n";
    private static final String QUERY_GET_ALL_CUSTOMER_COUPONS_BY_MAXPRICE = "SELECT *  FROM couponsys.coupons where price <= ?  and id in ( SELECT COUPON_ID FROM couponsys.customers_vs_coupons  where CUSTOMER_ID = ? ) ;\n";
    private static final String QUERY_GET_ALL_COUPONS_EXPIRED = "SELECT * FROM couponsys.coupons   where END_DATE < ? ;\n";

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException {


        Map<Integer, Object> map = new HashMap<>();

        map.put(1, coupon.getCompany_id());
        map.put(2, coupon.getCategory().ordinal() + 1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());

        JDBCUtils.execute(QUERY_INSERT_COUPONS, map);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, coupon.getCompany_id());
        map.put(2, coupon.getCategory().ordinal() + 1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        map.put(10, couponId);

        JDBCUtils.execute(QUERY_UPDATE_COUPON, map);
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, couponId);
        JDBCUtils.execute(QUERY_DELETE_COUPON, map);

    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException, InterruptedException {

        List<Coupon> coupons = new ArrayList<>();

        List<?> res = JDBCUtils.executeResults(QUERY_ALL_COUPONS);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;

    }

    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException {

        Coupon coupon = null;

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, couponId);
        List<?> res = JDBCUtils.executeResults(QUERY_ONE_COUPON, map);
        for (Object row : res) {
            coupon = (ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
            break;
        }
        return coupon;

    }


    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException {


        Map<Integer, Object> map = new HashMap<>();

        map.put(1, customerId);
        map.put(2, couponId);

        JDBCUtils.execute(QUERY_INSERT_CUSTOMER_VS_COUPONS, map);

    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, customerId);
        map.put(2, couponId);

        JDBCUtils.execute(QUERY_DELETE_CUSTOMER_VS_COUPONS, map);

    }

    public void deleteCouponByCompId(int companyId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, companyId);
        JDBCUtils.execute(QUERY_DELETE_COUPONS_BY_COMP_ID, map);

    }

    public void deleteCouponPurchaseByCompId(int companyId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, companyId);
        JDBCUtils.execute(QUERY_DELETE_CUSTOMER_VS_COUPONS_BY_COMPID, map);

    }

    public void deleteCouponPurchaseByCustId(int customerId) throws SQLException, InterruptedException {

        Map<Integer, Object> map = new HashMap<>();

        map.put(1, customerId);
        JDBCUtils.execute(QUERY_DELETE_CUSTOMER_VS_COUPONS_BY_CUSTID, map);

    }

    @Override
    public void deleteCouponPurchaseByCouponId(int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> map = new HashMap<>();

        map.put(1, couponId);
        JDBCUtils.execute(QUERY_DELETE_CUSTOMER_VS_COUPONS_BY_COUPNID, map);

    }

    @Override

    public boolean isCompanyTitleExist(int companyId, String title) throws SQLException, InterruptedException {
        boolean isExist = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, title);

        List<?> res = JDBCUtils.executeResults(QUERY_IS_COMPANY_TITLE_EXIST, map);
        for (Object row : res) {
            isExist = ResultsUtils.fromHashMapToBoolean((HashMap<String, Object>) row);
            break;
        }

        return isExist;

    }

    @Override
    public List<Coupon> getCompanyAllCoupons(int companyId) throws SQLException, InterruptedException {

        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);

        List<?> res = JDBCUtils.executeResults(QUERY_ALL_COMPANY_COUPONS, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;

    }

    @Override
    public List<Coupon> getCompanyCategoryAllCoupons(int companyId, Category category) throws SQLException, InterruptedException {

        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, category.ordinal() + 1);

        List<?> res = JDBCUtils.executeResults(QUERY_ALL_COMPANY_CATEGORY_COUPONS, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;
    }

    @Override
    public List<Coupon> getCompanyMaxPriceAllCoupons(int companyId, double maxPrice) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, maxPrice);

        List<?> res = JDBCUtils.executeResults(QUERY_ALL_COMPANY_MAX_PRICE_COUPONS, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;
    }

    @Override
    public boolean isCouponPurchaseCustCouponExist(int customerId, int couponId) throws SQLException, InterruptedException {
        boolean isExist = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);

        List<?> res = JDBCUtils.executeResults(QUERY_IS_CUST_COUPON_CUSTOMER_VS_COUP_EXIST, map);
        for (Object row : res) {
            isExist = ResultsUtils.fromHashMapToBoolean((HashMap<String, Object>) row);
            break;
        }

        return isExist;
    }

    @Override
    public List<Coupon> getCustomerAllCoupons(int customerId) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);

        List<?> res = JDBCUtils.executeResults(QUERY_GET_ALL_CUSTOMER_COUPONS, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;
    }

    @Override
    public List<Coupon> getCustomerAllCouponsByCategory(int customerId, Category category) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, category.ordinal() + 1);

        List<?> res = JDBCUtils.executeResults(QUERY_GET_ALL_CUSTOMER_COUPONS_BY_CATGORY, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;
    }

    @Override
    public List<Coupon> getCustomerAllCouponsByMaxPrice(int customerId, double maxPrice) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, maxPrice);
        map.put(2, customerId);

        List<?> res = JDBCUtils.executeResults(QUERY_GET_ALL_CUSTOMER_COUPONS_BY_MAXPRICE, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponsExpired(Date date) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, date);

        List<?> res = JDBCUtils.executeResults(QUERY_GET_ALL_COUPONS_EXPIRED, map);

        for (Object row : res) {
            coupons.add(ResultsUtils.fromHashMapToCoupons((HashMap<String, Object>) row));
        }

        return coupons;
    }


}
