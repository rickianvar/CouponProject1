package dao;

import beans.Category;
import beans.Coupon;
import beans.Customer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO {

    void addCoupon(Coupon coupon) throws SQLException, InterruptedException;

    void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException;

    void deleteCoupon(int couponId) throws SQLException, InterruptedException;

    List<Coupon> getAllCoupons() throws SQLException, InterruptedException;

    Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException;

    void addCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException;

    void deleteCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException;

    void deleteCouponByCompId(int companyId) throws SQLException, InterruptedException;

    void deleteCouponPurchaseByCompId(int companyId) throws SQLException, InterruptedException;

    void deleteCouponPurchaseByCustId(int customerId) throws SQLException, InterruptedException;

    void deleteCouponPurchaseByCouponId(int couponId) throws SQLException, InterruptedException;

    boolean isCompanyTitleExist(int companyId, String title) throws SQLException, InterruptedException;

    List<Coupon> getCompanyAllCoupons(int companyId) throws SQLException, InterruptedException;

    List<Coupon> getCompanyCategoryAllCoupons(int companyId, Category category) throws SQLException, InterruptedException;

    List<Coupon> getCompanyMaxPriceAllCoupons(int companyId, double maxPrice) throws SQLException, InterruptedException;

    boolean isCouponPurchaseCustCouponExist(int customerId, int couponId) throws SQLException, InterruptedException;

    List<Coupon> getCustomerAllCoupons(int customerId) throws SQLException, InterruptedException;

    List<Coupon> getCustomerAllCouponsByCategory(int customerId, Category category) throws SQLException, InterruptedException;

    List<Coupon> getCustomerAllCouponsByMaxPrice(int customerId, double maxPrice) throws SQLException, InterruptedException;

    List<Coupon> getAllCouponsExpired(Date date) throws SQLException, InterruptedException;


}
