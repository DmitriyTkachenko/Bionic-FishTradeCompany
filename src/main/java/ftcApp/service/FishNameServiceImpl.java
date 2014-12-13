package ftcApp.service;

import ftcApp.model.FishName;
import ftcApp.repository.FishNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class FishNameServiceImpl extends GenericServiceImpl<FishName, Integer> implements FishNameService {
    @Inject
    public FishNameServiceImpl(FishNameRepository repository) {
        super(repository, FishName.class);
    }
}
