package ftcApp.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ItemsOnSaleWs {
    @WebMethod(action = "onsale")
    Object getItemsOnSaleList();
}
