package ftcApp.service;

import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.repository.ParcelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class ParcelServiceImpl extends GenericServiceImpl<Parcel, Integer> implements ParcelService {
    @Inject
    ParcelServiceImpl(ParcelRepository repository) {
        super(repository, Parcel.class);
    }

    @Override
    public Integer generatePurchaseNumber() {
        return ((ParcelRepository)repository).generatePurchaseNumber();
    }

    @Override
    public void updateParcelStatus(Integer id, ParcelStatus status) {
        ((ParcelRepository)repository).updateParcelStatus(id, status);
    }
}
