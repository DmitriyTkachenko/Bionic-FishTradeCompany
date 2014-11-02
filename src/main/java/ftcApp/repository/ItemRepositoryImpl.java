package ftcApp.repository;

import ftcApp.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item, Integer> implements ItemRepository {
    public ItemRepositoryImpl() { super(Item.class); }
}
