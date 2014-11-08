package ftcApp.service;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;

public interface ParcelService extends GenericService<Parcel, Integer> {
    Integer generatePurchaseNumber();
    void updateParcelStatus(Integer id, ParcelStatus status);
    void updateParcels(Iterable<Parcel> parcels);
    void updateParcelColdStore(Parcel parcel);
}
