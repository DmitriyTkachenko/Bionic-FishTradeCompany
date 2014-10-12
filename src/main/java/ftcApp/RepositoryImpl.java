package ftcApp;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class RepositoryImpl<T, ID extends Serializable> implements Repository<T, ID> {
    EntityManager em = Persistence.createEntityManagerFactory("FTC").createEntityManager();
    protected Class<T> entityClass;

    public RepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(ID id) {
        try {
            em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e.id = :identifier").setParameter("identifier", id).executeUpdate();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<T> findAll() {
        Iterable<T> results = null;
        try {
            TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass);
            results = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public T findOne(ID id) {
        T result = null;
        try {
            TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getName() + " e WHERE e.id = :identifier", entityClass).setParameter("identifier", id);
            result = query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean exists(ID id) {
        return this.findOne(id) != null;
    }

    @Override
    public long count() {
        long count = 0;
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM " + entityClass.getName() + " e", Long.class);
            count = query.getSingleResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return count;
    }
}
