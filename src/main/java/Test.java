import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.*;
import db.ConnectionPool;
import db.DatabaseManager;
import exception.SystemException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import jobs.CouponExpirationDailyJob;
import login.ClientType;
import login.LoginManager;
import util.Art;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Test {

    public static void main(String[] args) throws InterruptedException, SQLException {

        System.out.println("START");

        CompaniesDAO companyDAO = new CompaniesDAOImp();

        Company c1 = new Company("ELIT", "ELIT@GMAIL", "1234");
        Company c2 = new Company("OSEM", "OSEM@GMAIL", "1234");
        Company c3 = new Company("WILI", "WILI@GMAIL", "1234");
        Company c4 = new Company("SHILAV", "SHILAV@GMAIL", "1234");
        Company c5 = new Company("FLYFOOT", "FLYFOOT@GMAIL", "1234");
        Company c8 = new Company("DANDESIGN", "DANDES@GMAIL", "1234");

        CustomersDAO customersDAO = new CustomersDAOImp();

        Customer cus1 = new Customer("ricki", "anvar", "ricki.anvar@gmail", "1234");
        Customer cus2 = new Customer("shoshi", "said", "shosi.saida@gmail", "1234");
        Customer cus3 = new Customer("rina", "bashan", "rina.bash@gmail", "1234");
        Customer cus4 = new Customer("mor", "anvar", "mor.anvar@gmail", "1234");
        Customer cus5 = new Customer("david", "kalif", "david.kalif@gmail", "1234");

        CouponsDAO couponsDAO = new CouponsDAOImp();
        Coupon coupon1 = new Coupon(3, Category.Books, "stimatsky", "50% discount for travel book", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(60)), 100, 30, "steimatzky.com");
        Coupon coupon2 = new Coupon(2, Category.Books, "stimatsky", "40% discount for travel book", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(60)), 30, 25, "steimatzky.com");
        Coupon coupon3 = new Coupon(2, Category.Food, "osem", "20% discount for ptitim", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(60)), 200, 500, "osem.com");
        Coupon coupon4 = new Coupon(4, Category.Fashion, "SHILAV BABY", "10% discount for DRESS", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(20)), 15, 70, "SHILAV.com");
        Coupon coupon5 = new Coupon(5, Category.Food, "LEHEM KAL", "1 + 1 ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(10)), 100, 120, "LEHEM.com");
        Coupon coupon6 = new Coupon(5, Category.Fashion, "FLY FOOT", "1 + 1 ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 5, 30, "FLYFOOT.com");
        Coupon coupon111 = new Coupon(6, Category.Food, "WILI TONA", "10%disc  ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 5, 30, "FLYFOOT.com");
        Coupon coupjob = new Coupon(6, Category.Food, "job test ", "10%disc  ", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().minusDays(10)), 5, 30, "test.com");

        try {
            DatabaseManager.databaseStrategy();

            CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
            Thread thread = new Thread(couponExpirationDailyJob);
            thread.start();

            System.out.println(Art.DAO_COMPANY);
            System.out.println("@@@@@@@@@@@@@@@@@@@@ Add COMPANY - BEFORE CHANGE @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyDAO.addCompany(c1);
            companyDAO.addCompany(c2);
            companyDAO.addCompany(c3);
            companyDAO.addCompany(c4);
            companyDAO.addCompany(c5);
            companyDAO.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ EXIST  @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(companyDAO.isCompanyExists("ELIT@GMAIL", "1234"));

            System.out.println("@@@@@@@@@@@@@@@@@@@@ upd COMPANY @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyDAO.updateCompany(2, c1);
            companyDAO.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del COMPANY @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyDAO.deleteCompany(1);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all  COMPANY @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyDAO.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ RETURN ONE COMPANY @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(companyDAO.getOneCompany(2));

            System.out.println(Art.DAO_CUSTOMER);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ Add customer + BEFORE CHANGE @@@@@@@@@@@@@@@@@@@@@@@@@");
            customersDAO.addCustomer(cus1);
            customersDAO.addCustomer(cus2);
            customersDAO.addCustomer(cus3);
            customersDAO.addCustomer(cus4);
            customersDAO.addCustomer(cus5);

            customersDAO.getAllCustomers().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ cust EXIST  @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(customersDAO.isCustomerExists("mor.anvar@gmail", "1234"));

            System.out.println("@@@@@@@@@@@@@@@@@@@@ upd CUSTOMER  @@@@@@@@@@@@@@@@@@@@@@@@@");
            customersDAO.updateCustomer(2, cus3);
            customersDAO.getAllCustomers().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del CUSTOMER @@@@@@@@@@@@@@@@@@@@@@@@@");
            customersDAO.deleteCustomer(2);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all  CUSTOMERS  @@@@@@@@@@@@@@@@@@@@@@@@@");
            customersDAO.getAllCustomers().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ RETURN ONE customer  @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(customersDAO.getOneCustomer(1));

            System.out.println(Art.DAO_COUPON);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ Add coupon BEFORE @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.addCoupon(coupon1);
            couponsDAO.addCoupon(coupon2);
            couponsDAO.addCoupon(coupon3);
            couponsDAO.addCoupon(coupon4);
            couponsDAO.addCoupon(coupon5);
            couponsDAO.getAllCoupons().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ upd COUPON  @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.updateCoupon(1, coupon6);
            couponsDAO.getAllCoupons().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del coupon @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.deleteCoupon(2);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all  COUPONS @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.getAllCoupons().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ RETURN ONE coupon @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(couponsDAO.getOneCoupon(1));

            System.out.println("@@@@@@@@@@@@@@@@@@@@ add couponvs Purchase @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.addCouponPurchase(1, 1);
            couponsDAO.addCouponPurchase(3, 1);
            couponsDAO.addCouponPurchase(1, 3);
            couponsDAO.addCouponPurchase(1, 4);
            couponsDAO.addCouponPurchase(3, 4);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del coupons Purchase @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.deleteCouponPurchase(1, 1);

            System.out.println(Art.ADMIN_FACADE);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN manager !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");
            LoginManager loginManager = LoginManager.getInstance();

            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN ADMIN FACAD wrong pass !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");
            AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "ad", ClientType.ADMINISTRATOR);

            if (adminFacade == null) {
                System.out.println("bad login as admin");
            } else {
                System.out.println("Successful login as admin");
            }
            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN ADMIN FACAD SUCC !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");

            adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            if (adminFacade == null) {
                System.out.println("bad login as admin");
            } else {
                System.out.println("Successful login as admin");
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ ADD COMPANY 5 to show exception  @@@@@@@@@@@@@@@@@@");
            try {
                adminFacade.addCompany(c5);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ ADD COMPANY 8     @@@@@@@@@@@@@@@@@@");
            try {
                adminFacade.addCompany(c8);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all  companies facad @@@@@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get one  company facad @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(adminFacade.getOneCompany(2));

            System.out.println("@@@@@@@@@@@@@@@@@@@@ update  company 5 facad & show after @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(" update id 12 not exist - execptionnnn ");
            Company com5 = adminFacade.getOneCompany(5);
            com5.setId(12);
            try {
                adminFacade.updateCompany(com5);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(" update name  exception -  cannot update comp name  ");
            com5 = adminFacade.getOneCompany(5);
            com5.setName("rickiiii");
            try {
                adminFacade.updateCompany(com5);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(" update password - should be successs   ");
            // set good data for com5 otherwise exception for id  again
            com5 = adminFacade.getOneCompany(5);
            com5.setPassword("7777");

            try {
                adminFacade.updateCompany(com5);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            adminFacade.getAllCompanies().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del coupon BY COMPANY ID FACAD @@@@@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.deleteCompany(4);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ company after delete @@@@@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.getAllCompanies().forEach(System.out::println);
            System.out.println("@@@@@@@@@@@@@@@@@@@@ coupon after delete @@@@@@@@@@@@@@@@@@@@@@@@@");
            couponsDAO.getAllCoupons().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all customer fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.getAllCustomers().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get One customer fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(adminFacade.getOneCustomer(3));

            System.out.println("@@@@@@@@@@@@@@@@@@@@ add One customer fasad @@@@@@@@@@@@@@@@@@@@@@@@@");

            System.out.println("add CAUSE EXCEPTION CUS 1 ");
            try {
                adminFacade.addCustomer(cus1);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("add SHOULD BE succcessfulll cus2 ");
            try {
                adminFacade.addCustomer(cus2);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ AFTER add one customer fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.getAllCustomers().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ update  customer 3 facad @@@@@@@@@@@@@@@@@@@@@@@@@");
            Customer cus6 = adminFacade.getOneCustomer(3);
            cus6.setId(17);
            System.out.println("UPD SHOULD BE EXCEPTION FOR ID 12  ");
            try {
                adminFacade.updateCustomer(cus6);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            cus6 = adminFacade.getOneCustomer(3);
            cus6.setPassword("33333333");
            System.out.println("UPD SHOULD BE succcessfulll cus2 ");
            try {
                adminFacade.updateCustomer(cus6);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del couponpurchase & CUSTOMER BY customer ID FACAD @@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.deleteCustomer(3);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ AFTER UPD & DEL CUSTOMER  @@@@@@@@@@@@@@@@@@@@@@@@@");
            adminFacade.getAllCustomers().forEach(System.out::println);

            System.out.println(Art.COMPANY_FACADE);
            System.out.println("@@@@@@@@@@@@@@@@@@@@ company login facad   @@@@@@@@@@@@@@@@@@@@@@@@@");

            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN COMPANY FACAD wrong pass !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");
            CompanyFacade companyFacade = (CompanyFacade) loginManager.login("ELIT@GMAIL", "12", ClientType.COMPANY);

            if (companyFacade == null) {
                System.out.println("bad login as COMPANY");
            } else {
                System.out.println("Successful login as COMPANY");
            }
            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN COMPANY FACAD SUCC !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");

            companyFacade = (CompanyFacade) loginManager.login("ELIT@GMAIL", "1234", ClientType.COMPANY);
            if (companyFacade == null) {
                System.out.println("bad login as company");
            } else {
                System.out.println("Successful login as company");
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ ADD COUPON  facad   @@@@@@@@@@@@@@@@@@@@@@@@@");

            System.out.println("UPD SHOULD BE EXCEPTION FOR COU1  ");
            try {
                companyFacade.addCoupon(coupon1);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("UPD SHOULD BE good  FOR COUPON6  ");
            try {
                companyFacade.addCoupon(coupon6);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ update  COUPON  facad @@@@@@@@@@@@@@@@@@@@@@@@@");

            System.out.println("exceptio NOT EXIST   ");
            try {
                companyFacade.updateCoupon(coupon111);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            Coupon coupon10 = couponsDAO.getOneCoupon(6);

            coupon10.setCompany_id(3);
            System.out.println("exception set company 3 NOT ALLOWED -  FOR COUPON6  ");
            try {
                companyFacade.updateCoupon(coupon10);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            coupon10 = couponsDAO.getOneCoupon(6);
            coupon10.setAmount(22);
            System.out.println("SUCCESSS set AMOUNT -  FOR COUPON6  ");
            try {
                companyFacade.updateCoupon(coupon10);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ del COUPON FASAD  @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyFacade.deleteCoupon(6);

            // add coupon for job to br deleted
            couponsDAO.addCoupon(coupjob);
            couponsDAO.addCouponPurchase(1, 7);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all company COUPONS fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyFacade.getCompanyCoupons().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all company COUPONS CATEGORY  fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyFacade.getCompanyCoupons(Category.Food).forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all company COUPONS MAX PRICE  fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            companyFacade.getCompanyCoupons(600).forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get ONE COMPANY  fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(companyFacade.getCompanyDetails());

            System.out.println(Art.CUSTOMER_FACADE);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN CUSTOMER FACAD wrong pass !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");
            CustomerFacade customerFacade = (CustomerFacade) loginManager.login("ricki.anvar@gmail", "134", ClientType.CUSTOMER);

            if (customerFacade == null) {
                System.out.println("bad login as CUSTOMER");
            } else {
                System.out.println("Successful login as CUSTOMER");
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ LOGIN CUSTOMER FACAD SUCC !!!!  @@@@@@@@@@@@@@@@@@@@@@@@@");

            customerFacade = (CustomerFacade) loginManager.login("ricki.anvar@gmail", "1234", ClientType.CUSTOMER);

            if (customerFacade == null) {
                System.out.println("bad login as customer");
            } else {
                System.out.println("Successful login as customer");
            }


            System.out.println("@@@@@@@@@@@@@@@@@@@@ buy coupon facad   @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("@@@@@@@@@@@@@@@@@@@@  BEFORE customer COUPONS fasad @@@@@@@@@@@@@@@@@@@@@@@@@");

            customerFacade.getCustomerCoupons().forEach(System.out::println);

            Coupon couponDB = couponsDAO.getOneCoupon(5);

            System.out.println("SUCCESSS ADD PURC COUPON ");
            try {
                customerFacade.purchaseCoupon(couponDB);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            couponDB = couponsDAO.getOneCoupon(3);
            try {
                customerFacade.purchaseCoupon(couponDB);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            couponDB = couponsDAO.getOneCoupon(1);
            couponDB.setAmount(0);
            couponsDAO.updateCoupon(couponDB.getId(), couponDB);

            try {
                customerFacade.purchaseCoupon(couponDB);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            //System.out.println("EXPIRATION");
            couponDB = couponsDAO.getOneCoupon(1);
            couponDB.setAmount(100);
            couponDB.setEndDate(Date.valueOf(LocalDate.now().minusDays(24)));
            couponsDAO.updateCoupon(couponDB.getId(), couponDB);

            try {
                customerFacade.purchaseCoupon(couponDB);
            } catch (SystemException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all customer COUPONS fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            customerFacade.getCustomerCoupons().forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all customer COUPONS BY CATEGORY fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            customerFacade.getCustomerCoupons(Category.Food).forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get all customer COUPONS BY MAX PRICE  fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            customerFacade.getCustomerCoupons(130).forEach(System.out::println);

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get ONE customer details  fasad @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(customerFacade.getCustomerDetails());

            System.out.println("@@@@@@@@@@@@@@@@@@@@ get customer id by email & pasww : login @@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println(customerFacade.getCustomerIdByEmailAndPassword("ricki.anvar@gmail", "1234"));

            couponExpirationDailyJob.stop();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ConnectionPool.getInstance().closeAllConnections();

        System.out.println("end");
    }
}
