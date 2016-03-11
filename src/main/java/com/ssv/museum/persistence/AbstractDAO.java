package com.ssv.museum.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Base class for any persisting collection DAO 
 *
 * @param <T> Type
 * @param <K> Primary key (id)
 * @author simonarneson
 */
public abstract class AbstractDAO<T, K> implements IDAO<T, K> {

    private final Class<T> clazz;

    protected abstract EntityManager getEntityManager();

    protected AbstractDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void create(T t) {
       getEntityManager().persist(t);
    }

    @Override
    public void delete(K id) {
         T t = getEntityManager().getReference(clazz,id);
        getEntityManager().remove(t);
    }

    /**
     * Using out parameter
     *
     * @param t
     */
    @Override
    public void update(T t) {
        getEntityManager().merge(t);
        getEntityManager().flush();
    }

    @Override
    public T find(K id) {
        return getEntityManager().find(clazz,id);
    }

    @Override
    public List<T> findAll() {
        return get(true, -1, -1);
    }

    @Override
    public List<T> findRange(int first, int n) {
        return get(false, first, n);
    }

    //@SuppressWarnings("")
    private List<T> get(boolean all, int first, int n) {
        EntityManager em = getEntityManager();
        List<T> found = new ArrayList<>();
        TypedQuery<T> q = em.createQuery("select t from " + clazz.getSimpleName() + " t", clazz);
        if (!all) {
            q.setFirstResult(first);
            q.setMaxResults(n);
        }
        found.addAll(q.getResultList());
        return found;
    }

    @Override
    public int count() {
        EntityManager em = getEntityManager();
        Long n = em.createQuery("select count(t) from " + clazz.getSimpleName() + " t", Long.class)
                .getSingleResult();
        return n.intValue();
    } 
}
