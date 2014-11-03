package ftcApp.service;

import ftcApp.model.Item;
import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    @Inject
    private ParcelService parcelService;

    private Parcel parcel;

    private boolean previousDataDeleted = true;

    @Override
    public void addItemsData() {
        if (!previousDataDeleted) {
            return;
        }
        parcel = new Parcel(parcelService.generatePurchaseNumber(), ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "Country of origin: Norway", parcel, 5000.0, 305.0);
        Item item2 = new Item("Tuna", 400, "Country of origin: Thailand", parcel, 3500.0, 407.0);
        Item item3 = new Item("Trout", 200, "Country of origin: Turkey", parcel, 6000.0, 202.5);
        parcelService.save(parcel);
        previousDataDeleted = false;
    }

    @Override
    public void removeItemsData() {
        parcelService.delete(parcel);
        previousDataDeleted = true;
    }


}
