package com.ems.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ems.model.Registration;

/**
 * Registration DAO
 * @author reshmivn
 * @since 0.0.1
 */
@Repository
public class RegistrationDAO extends GenericDAO<Registration>{
	@Transactional
	public void persist(Registration registration) {
		getSession().save(registration);
	}

	@Transactional
	public Registration get(String apiKey) {
		Criteria criteria = getSession().createCriteria(Registration.class);
		criteria.add(Restrictions.eq("apiKey", apiKey));
		return (Registration) criteria.uniqueResult();
	}
}
