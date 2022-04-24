package login;

import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {

    private static LoginManager instance = null;

    private AdminFacade adminFacade;
    private CompanyFacade companyFacade;
    private CustomerFacade customerFacade;

    private LoginManager() {
    }


    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }


    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, InterruptedException {
        switch (clientType) {
            case ADMINISTRATOR:
                adminFacade = new AdminFacade();
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
                break;
            case COMPANY:
                companyFacade = new CompanyFacade();
                if (companyFacade.login(email, password)) {
                    int companyId = companyFacade.getCompanyIdByEmailAndPassword(email, password);
                    companyFacade.setCompanyId(companyId);
                    return companyFacade;
                }
                break;
            case CUSTOMER:
                customerFacade = new CustomerFacade();
                if (customerFacade.login(email, password)) {
                    int customerId = customerFacade.getCustomerIdByEmailAndPassword(email, password);
                    customerFacade.setCustomerId(customerId);
                    return customerFacade;
                }
                break;
            default:
                break;
        }

        return null;
    }

}
