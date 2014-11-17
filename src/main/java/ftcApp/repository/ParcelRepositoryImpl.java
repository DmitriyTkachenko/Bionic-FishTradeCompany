package ftcApp.repository;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ParcelRepositoryImpl extends GenericRepositoryImpl<Parcel, Integer> implements ParcelRepository {
    public ParcelRepositoryImpl() { super(Parcel.class); }

    @Override
    public void updateParcelStatus(Integer id, ParcelStatus status) {
        Parcel parcel = this.findOne(id);
        parcel.setStatus(status);
        this.update(parcel);
    }
}
