package ftcApp.service;

import ftcApp.model.Employee;
import ftcApp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, Integer> implements EmployeeService {
    @Inject
    EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository, Employee.class);
    }
}
