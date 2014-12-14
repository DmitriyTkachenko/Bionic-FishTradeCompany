package ftcApp.repository;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ParcelRepositoryImpl extends GenericRepositoryImpl<Parcel, Integer> implements ParcelRepository {
    public ParcelRepositoryImpl() { super(Parcel.class); }

    @Override
    public void updateParcelStatus(Integer id, ParcelStatus status) {
        Parcel parcel = this.findOne(id);
        parcel.setStatus(status);
        this.update(parcel);
    }

    @Override
    public Iterable<Parcel> findParcelsNotRegisteredInColdStore() {
        Iterable<Parcel> result = null;
        try {
            TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p WHERE p.status = :parcelStatus", entityClass)
                    .setParameter("parcelStatus", ParcelStatus.REGISTERED_BY_GM);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }
}
