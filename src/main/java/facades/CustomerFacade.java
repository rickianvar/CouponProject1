package facades;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import exception.SystemException;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerFacade extends ClientFacade {

    private int customerId;

    public CustomerFacade() {
        super();
    }

    public CustomerFacade(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    @Override
    public String toString() {
        return "CustomerFacade{" +
                "customerId=" + customerId +
                "} " + super.toString();
    }


    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {

        return this.customersDAO.isCustomerExists(email, password);
    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, InterruptedException, SystemException {

        if (couponsDAO.isCouponPurchaseCustCouponExist(this.customerId, coupon.getId())) {
            throw new SystemException(" CUSTOMER HAS THIS COUPON - CANNOT PURCHASE MORE THAN 1");
        }

        Coupon couponInput = couponsDAO.getOneCoupon(coupon.getId());
        System.out.println("coupon.getId()" + coupon.getId());
        if (couponInput.getAmount() == 0) {
            throw new SystemException(" NO coupon left ");
        }
        if (couponInput.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new SystemException("COUPON DATE EXPIRED  ");
        }

        this.couponsDAO.addCouponPurchase(this.customerId, coupon.getId());

        couponInput.setAmount(couponInput.getAmount() - 1);
        this.couponsDAO.updateCoupon(coupon.getId(), couponInput);
    }

    public List<Coupon> getCustomerCoupons() throws SQLException, InterruptedException {

        return this.couponsDAO.getCustomerAllCoupons(this.customerId);
    }

    public List<Coupon> getCustomerCoupons(Category category) throws SQLException, InterruptedException {

        return this.couponsDAO.getCustomerAllCouponsByCategory(this.customerId, category);
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) throws SQLException, InterruptedException {

        return this.couponsDAO.getCustomerAllCouponsByMaxPrice(this.customerId, maxPrice);
    }

    public Customer getCustomerDetails() throws SQLException, InterruptedException {
        return this.customersDAO.getOneCustomer(this.customerId);
    }

    public int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException, InterruptedException {
        return customersDAO.getCustomerIdByEmailAndPassword(email, password);
    }
}
