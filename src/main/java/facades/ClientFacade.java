package facades;

import dao.*;

import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompaniesDAO companyDAO ;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    public ClientFacade() {

        couponsDAO = new CouponsDAOImp();
        customersDAO = new CustomersDAOImp();
        companyDAO = new CompaniesDAOImp();
    }

    public abstract boolean login(String email , String password) throws SQLException, InterruptedException;

}
