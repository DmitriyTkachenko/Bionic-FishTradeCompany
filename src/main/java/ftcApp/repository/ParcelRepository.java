package ftcApp.repository;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;

public interface ParcelRepository extends GenericRepository<Parcel, Integer> {
    void updateParcelStatus(Integer id, ParcelStatus status);
    Iterable<Parcel> findParcelsNotRegisteredInColdStore();
}
