package ftcApp.repository;

import ftcApp.model.OrderedItem;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public class OrderedItemRepositoryImpl extends RepositoryImpl<OrderedItem, Integer> implements OrderedItemRepository {
    public OrderedItemRepositoryImpl() { super(OrderedItem.class); }
}
