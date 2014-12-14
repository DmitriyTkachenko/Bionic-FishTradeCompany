package ftcApp.repository;

import ftcApp.model.Item;

public interface ItemRepository extends GenericRepository<Item, Integer> {
    Iterable<Item> findItemsOnSale();
}
