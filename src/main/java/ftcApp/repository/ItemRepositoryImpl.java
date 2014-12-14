package ftcApp.repository;

import ftcApp.model.Item;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.model.enums.WriteOffStatus;
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
                    "WHERE i.writeOffStatus != :writeOffStatus AND i.weightColdStore > 0 AND p.status = :parcelStatus", entityClass)
                    .setParameter("writeOffStatus", WriteOffStatus.WRITTEN_OFF)
                    .setParameter("parcelStatus", ParcelStatus.PUT_UP_FOR_SALE);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterable<Item> findItemsDesignatedForWriteOff() {
        Iterable<Item> result = null;
        try {
            TypedQuery<Item> query = em.createQuery("SELECT i FROM Item i WHERE i.writeOffStatus = :writeOffStatus", entityClass)
                    .setParameter("writeOffStatus", WriteOffStatus.DESIGNATED_FOR_WRITE_OFF);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }
}
