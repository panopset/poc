package com.panopset.demo.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.panopset.demo.bean.Store;

/**
 * @see PersonDaoImpl.java.
 *
 */
@Repository
@Transactional(readOnly = true)
public class StoreDAOImpl extends HibernateDaoSupport implements StoreDAO {

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void insertStore(Store store) {
        getHibernateTemplate().saveOrUpdate(store);
    }

    @Override
    public Store getStoreById(int storeId) {
        return (Store) getHibernateTemplate().get(Store.class, storeId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Store getStore(String storeName) {
        List<Store> r = getHibernateTemplate().execute(
                new HibernateCallback<List<Store>>() {
                    public List<Store> doInHibernate(Session session)
                            throws HibernateException {
                        Query q = session
                                .createQuery("from Store where name = :name");
                        q.setParameter("name", storeName);
                        q.setMaxResults(1);
                        return q.list();
                    }
                });
        // Collection<Store> r = (Collection<Store>)
        // getHibernateTemplate().find(
        // "from Store where name = :name", storeName);
        if (r == null || r.isEmpty()) {
            return null;
        }
        for (Store s : r) {
            return s;
        }
        return null;
    }

    /**
     * <a href=
     * "http://stackoverflow.com/questions/8130964/with-springs-gethibernatetemplate-how-can-i-get-a-list-of-users-and-limit-the-r"
     * > Reference</a>.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Store> getAllStores() {
        return getHibernateTemplate().execute(
                new HibernateCallback<List<Store>>() {
                    public List<Store> doInHibernate(Session session)
                            throws HibernateException {
                        Query q = session.createQuery("from Store");
                        q.setMaxResults(100);
                        return q.list();
                    }
                });
    }

}
