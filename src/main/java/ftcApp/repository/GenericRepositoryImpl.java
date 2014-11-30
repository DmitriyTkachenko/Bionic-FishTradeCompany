package ftcApp.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Repository
public class GenericRepositoryImpl<T, ID extends Serializable> implements GenericRepository<T, ID> {
    @PersistenceContext
    EntityManager em;

    protected Class<T> entityClass;

    public GenericRepositoryImpl() { }

    public GenericRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public void delete(ID id) {
        em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e.id = :identifier").setParameter("identifier", id).executeUpdate();
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM " + entityClass.getName() + " e").executeUpdate();
    }

    @Override
    public void refresh(T entity) {
        em.refresh(entity);
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
        return em.find(entityClass, id);
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
