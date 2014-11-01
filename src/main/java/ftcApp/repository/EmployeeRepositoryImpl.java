package ftcApp.repository;

import ftcApp.model.Employee;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public class EmployeeRepositoryImpl extends UserRepositoryImpl<Employee, Integer> implements EmployeeRepository {
    public EmployeeRepositoryImpl() { super(Employee.class); }
}
