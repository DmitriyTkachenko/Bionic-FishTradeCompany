package ftcApp.repository;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;

public interface ParcelRepository extends Repository<Parcel, Integer> {
    Integer generatePurchaseNumber();
    void updateParcelStatus(Integer id, ParcelStatus status);
}
