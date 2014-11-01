package ftcApp.repository;

import ftcApp.model.Item;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public class ItemRepositoryImpl extends RepositoryImpl<Item, Integer> implements ItemRepository {
    public ItemRepositoryImpl() { super(Item.class); }
}
