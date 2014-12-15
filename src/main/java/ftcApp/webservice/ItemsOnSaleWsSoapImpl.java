package ftcApp.webservice;

import ftcApp.model.Item;
import ftcApp.model.Items;
import ftcApp.service.ItemService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

@WebService(endpointInterface = "ftcApp.webservice.ItemsOnSaleWs")
@Named
public class ItemsOnSaleWsSoapImpl implements ItemsOnSaleWs {
    @Inject
    private ItemService itemService;

    @Override
    public String getItemsOnSaleList() {
        try {
            Iterable<Item> itemsOnSale = itemService.findItemsOnSaleAndRefresh();
            Items items = new Items();
            items.setItems((List<Item>) itemsOnSale);
            JAXBContext jc = JAXBContext.newInstance(Items.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            m.marshal(items, sw);
            return sw.toString();
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
