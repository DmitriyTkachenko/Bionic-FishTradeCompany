package ftcApp.repository;

import ftcApp.model.OrderedItem;
import org.springframework.stereotype.Repository;

@Repository
public class OrderedItemRepositoryImpl extends GenericRepositoryImpl<OrderedItem, Integer> implements OrderedItemRepository {
    public OrderedItemRepositoryImpl() { super(OrderedItem.class); }
}
