package ftcApp.service;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;

public interface ParcelService extends GenericService<Parcel, Integer> {
    void updateParcelStatus(Integer id, ParcelStatus status);
    void updateParcelColdStore(Parcel parcel);
}
