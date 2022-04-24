package facades;

import beans.Category;
import beans.Company;
import beans.Coupon;
import exception.SystemException;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade {

    private int companyId;

    public CompanyFacade() {

        super();
    }

    public CompanyFacade(int companyId) {
        super();
        this.companyId = companyId;
    }


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


    @Override
    public String toString() {
        return "CompanyFacade{" +
                "companyId=" + companyId +
                "} " + super.toString();
    }


    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {

        return this.companyDAO.isCompanyExists(email, password);
    }

    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException, SystemException {

        if (this.couponsDAO.isCompanyTitleExist(coupon.getCompany_id(), coupon.getTitle())) {
            throw new SystemException("COUPON TITLE EXIST FOR COMPANY");
        }
        this.couponsDAO.addCoupon(coupon);

    }

    public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException, SystemException {

        Coupon inputCoupon = this.couponsDAO.getOneCoupon(coupon.getId());
        if (inputCoupon == null) {
            throw new SystemException(" CAN NOT UPDATE COUPON - ID NOT EXIST");
        }
        if (inputCoupon.getCompany_id() != coupon.getCompany_id()) {
            throw new SystemException(" DO NOT UPDATE COMPANY ID IN COUPONS TABLE ");
        }
        this.couponsDAO.updateCoupon(coupon.getId(), coupon);

    }

    public void deleteCoupon(int couponId) throws SQLException, InterruptedException {
        this.couponsDAO.deleteCouponPurchaseByCouponId(couponId);
        this.couponsDAO.deleteCoupon(couponId);

    }

    public List<Coupon> getCompanyCoupons() throws SQLException, InterruptedException {

        return couponsDAO.getCompanyAllCoupons(this.companyId);
    }

    public List<Coupon> getCompanyCoupons(Category category) throws SQLException, InterruptedException {

        return couponsDAO.getCompanyCategoryAllCoupons(this.companyId, category);
    }

    public List<Coupon> getCompanyCoupons(double maxPrice) throws SQLException, InterruptedException {

        return couponsDAO.getCompanyMaxPriceAllCoupons(this.companyId, maxPrice);
    }

    public Company getCompanyDetails() throws SQLException, InterruptedException {

        return companyDAO.getOneCompany(this.companyId);
    }

    public int getCompanyIdByEmailAndPassword(String email, String password) throws SQLException, InterruptedException {
        return companyDAO.getCompanyIdByEmailAndPassword(email, password);
    }
}
