package ftcApp.repository;

import ftcApp.model.FishName;
import org.springframework.stereotype.Repository;

@Repository
public class FishNameRepositoryImpl extends GenericRepositoryImpl<FishName, Integer> implements FishNameRepository  {
    public FishNameRepositoryImpl() {
        super(FishName.class);
    }
}
