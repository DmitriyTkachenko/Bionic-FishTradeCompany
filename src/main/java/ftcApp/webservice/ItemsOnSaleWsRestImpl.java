package ftcApp.webservice;

import ftcApp.model.Item;
import ftcApp.model.Items;
import ftcApp.service.ItemService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@WebService(endpointInterface = "ftcApp.webservice.ItemsOnSaleWs")
@Named
public class ItemsOnSaleWsRestImpl implements ItemsOnSaleWs {
    @Inject
    private ItemService itemService;

    @Override
    @GET
    @Path("/onsale")
    @Produces("application/json")
    public Items getItemsOnSaleList() {
            Iterable<Item> itemsOnSale = itemService.findItemsOnSaleAndRefresh();
            Items items = new Items();
            items.setItems((List<Item>) itemsOnSale);
            return items;
    }
}
