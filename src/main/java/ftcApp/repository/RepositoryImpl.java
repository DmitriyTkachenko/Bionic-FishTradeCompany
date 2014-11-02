package ftcApp.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@org.springframework.stereotype.Repository
@Transactional
public class RepositoryImpl<T, ID extends Serializable> implements Repository<T, ID> {
    @PersistenceContext
    EntityManager em;

    protected Class<T> entityClass;

    public RepositoryImpl() { }

    public RepositoryImpl(Class<T> entityClass) {
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
    public void delete(ID id) {
        em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e.id = :identifier").setParameter("identifier", id).executeUpdate();
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM " + entityClass.getName() + " e").executeUpdate();
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