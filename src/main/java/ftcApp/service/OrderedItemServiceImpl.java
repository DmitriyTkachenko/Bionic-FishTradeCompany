package ftcApp.service;


import ftcApp.model.OrderedItem;
import ftcApp.repository.OrderedItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class OrderedItemServiceImpl extends GenericServiceImpl<OrderedItem, Integer> implements OrderedItemService {
    @Inject
    OrderedItemServiceImpl(OrderedItemRepository repository) {
        super(repository, OrderedItem.class);
    }
}
