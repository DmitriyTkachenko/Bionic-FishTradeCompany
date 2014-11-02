package ftcApp.repository;

import ftcApp.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.io.Serializable;

@Repository
public class UserRepositoryImpl<T extends User, ID extends Serializable> extends GenericRepositoryImpl<T, ID> implements UserRepository<T, ID> {
    public UserRepositoryImpl() { }

    public UserRepositoryImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public T findByLogin(String login) {
        T result = null;
        try {
            TypedQuery<T> query = em.createQuery("SELECT u FROM " + entityClass.getName() + " u WHERE u.login = :li", entityClass).setParameter("li", login);
            result = query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean existsWithLogin(String login) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM " + entityClass.getName() + " e WHERE e.login = :li", Long.class).setParameter("li", login);
        return query.getSingleResult() == 1;
    }
}
