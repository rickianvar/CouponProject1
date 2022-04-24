package jobs;

import beans.Coupon;
import dao.CouponsDAO;
import dao.CouponsDAOImp;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {

    private boolean quit;
    private CouponsDAO couponsDAO = new CouponsDAOImp();

    private static final int DAY = 1000 * 60 * 60 * 24;
    private static final int SECOND = 1000;


    public CouponExpirationDailyJob() {
    }


    @Override
    public void run() {

        while (!quit) {

            System.out.println("Daily job execution - deletion of expired coupons - #");

            List<Coupon> expiredCoupons = null;

            try {
                expiredCoupons = couponsDAO.getAllCouponsExpired(Date.valueOf(LocalDate.now()));

            } catch (Exception e) {
                e.getMessage();
            }

            System.out.println("Daily job execution - coupons list before deletion:");

            try {
                couponsDAO.getAllCoupons().forEach(System.out::println);
            } catch (Exception e) {
                e.getMessage();
            }

            for (Coupon coupon : expiredCoupons) {
                System.out.println(" Daily job coupon before delete coupons" + coupon);
                try {
                    couponsDAO.deleteCouponPurchaseByCouponId(coupon.getId());
                } catch (Exception e) {
                    e.getMessage();
                }

                try {
                    couponsDAO.deleteCoupon(coupon.getId());
                    System.out.println("Daily job!!!!!!!! coupon NUMBER " + coupon.getId() + " DELETED JOB ");
                } catch (Exception e) {
                    e.getMessage();
                }

            }
            System.out.println("Daily job execution - coupons list AFTER   deletion:");

            try {
                couponsDAO.getAllCoupons().forEach(System.out::println);
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                Thread.sleep(DAY);
            } catch (Exception e) {
                e.getMessage();
            }
        }   // while

    }

    public void stop() {
        System.out.println("job stopppppppppppppppppppp");
        this.quit = true;
    }
}
