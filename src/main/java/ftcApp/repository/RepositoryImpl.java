package ftcApp.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class RepositoryImpl<T, ID extends Serializable> implements Repository<T, ID> {
    EntityManager em = Persistence.createEntityManagerFactory("FTC").createEntityManager();
    protected Class<T> entityClass;
    private EntityTransaction etx;

    public RepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        etx = em.getTransaction();
    }

    @Override
    public T save(T entity) {
        if (!etx.isActive()) {
            etx.begin();
        }
        em.persist(entity);
        etx.commit();
        return entity;
    }

    @Override
    public void delete(ID id) {
        if (!etx.isActive()) {
            etx.begin();
        }
        em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e.id = :identifier").setParameter("identifier", id).executeUpdate();
        etx.commit();
    }

    @Override
    public void deleteAll() {
        if (!etx.isActive()) {
            etx.begin();
        }
        em.createQuery("DELETE FROM " + entityClass.getName() + " e").executeUpdate();
        etx.commit();
    }

    @Override
    public Iterable<T> findAll() {
        Iterable<T> result = null;
        try {
            TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
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
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM " + entityClass.getName() + " e WHERE e.id = :identifier", Long.class).setParameter("identifier", id);
        return query.getSingleResult() == 1;
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM " + entityClass.getName() + " e", Long.class);
        return query.getSingleResult();
    }

}
