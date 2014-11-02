package ftcApp.repository;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ParcelRepositoryImpl extends GenericRepositoryImpl<Parcel, Integer> implements ParcelRepository {
    public ParcelRepositoryImpl() { super(Parcel.class); }

    @Override
    public Integer generatePurchaseNumber() {
        TypedQuery<Integer> query = em.createQuery("SELECT COALESCE(MAX(p.purchaseNumber), 0) FROM Parcel p", Integer.class);
        return query.getSingleResult() + 1;
    }

    @Override
    public void updateParcelStatus(Integer id, ParcelStatus status) {
        Parcel parcel = this.findOne(id);
        parcel.setStatus(status);
        this.update(parcel);
    }
}
