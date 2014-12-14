package ftcApp.repository;

import ftcApp.model.Item;
import ftcApp.model.enums.ParcelStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item, Integer> implements ItemRepository {
    public ItemRepositoryImpl() { super(Item.class); }

    @Override
    public Iterable<Item> findItemsOnSale() {
        Iterable<Item> result = null;
        try {
            TypedQuery<Item> query = em.createQuery("SELECT i FROM Item i JOIN Parcel p ON i.parcel = p " +
                    "WHERE i.writtenOff = :writtenOff AND i.weightColdStore > 0 AND p.status = :parcelStatus", entityClass)
                    .setParameter("writtenOff", false)
                    .setParameter("parcelStatus", ParcelStatus.PUT_UP_FOR_SALE);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }
}
