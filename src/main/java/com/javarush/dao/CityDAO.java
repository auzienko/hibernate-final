package com.javarush.dao;

import com.javarush.domain.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CityDAO {
    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public CityDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<City> getItems(int offset, int limit) {
        return getSession()
                .createQuery("select c from City c", City.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public int getTotalCount() {
        Long cityCount = getSession()
                .createQuery("select count(c) from City c", Long.class)
                .uniqueResult();

        return Math.toIntExact(cityCount);
    }

    public City getById(Integer id) {
        return getSession()
                .createQuery("select c from City c join fetch c.country where c.id = :ID", City.class)
                .setParameter("ID", id)
                .getSingleResult();
    }
}
