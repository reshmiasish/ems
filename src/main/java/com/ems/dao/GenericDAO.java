package com.ems.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Generic DAO
 * @author reshmivn
 * @since 0.0.1
 */
@Repository
public class GenericDAO<T> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		Type genericSuperClass = this.getClass().getGenericSuperclass();
		if(genericSuperClass instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)genericSuperClass;
			Type type = pt.getActualTypeArguments()[0];
			clazz = (Class<T>) type;
		}
	}
	
	@SuppressWarnings("unchecked")
    public T listById(Object id) throws Exception {
        T object = null;
        object = (T) getSession().get(clazz, (Serializable) id);
        return object;
    }

    public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
    
	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}
	
	public void flush() {
		Session session = getSession();
		session.flush();
		session.clear();
	}

}
