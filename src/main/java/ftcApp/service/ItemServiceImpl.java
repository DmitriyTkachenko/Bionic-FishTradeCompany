package ftcApp.service;

import ftcApp.model.Item;
import ftcApp.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class ItemServiceImpl extends GenericServiceImpl<Item, Integer> implements ItemService {
    @Inject
    public ItemServiceImpl(ItemRepository repository) {
        super(repository, Item.class);
    }

    @Override
    public Iterable<Item> findItemsOnSaleAndRefresh() {
        Iterable<Item> items = ((ItemRepository) repository).findItemsOnSale();
        return repository.refreshAll(items);
    }

    @Override
    public Iterable<Item> findItemsDesignatedForWriteOff() {
        return ((ItemRepository) repository).findItemsDesignatedForWriteOff();
    }
}
