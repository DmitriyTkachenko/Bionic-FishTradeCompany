package ftcApp.service;

import ftcApp.model.Item;

public interface ItemService extends GenericService<Item, Integer> {
    Iterable<Item> findItemsOnSaleAndRefresh();
    Iterable<Item> findItemsDesignatedForWriteOff();
}
