package com.bellaryinfotech.DAO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bellaryinfotech.common.BaseEntity;

@Repository
public class GenericDAOImpl<E, I extends Serializable> implements GenericDAO<E, I> {

	private Class<E> entityClass;

	@Autowired
	private SessionFactory sessionFactory;

	 

	private Long tenant;

	private Long createdBy = -1l;

	private Long lastUpdatedBy = -1l;

	private Date creationDate = new Date();
	private Date lastUpdateDate = new Date();
 

	@Override
	public Session getCurrentSession() {
		init();
		Session session = getSessionFactory().getCurrentSession();
 
		return session;
	}

	private void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	@SuppressWarnings("unchecked")
	public E find(I id) {
		return (E) getCurrentSession().get(getEntityClass(), id);
	}

	@Override
	public void saveOrUpdate(E e) {

		getCurrentSession().saveOrUpdate(e);
	}

	@Override
	public void delete(E e) {
		getCurrentSession().delete(e);
	}

	@Override
	public void flush() {
		getCurrentSession().flush();
	}

	 

	@Override
	public E save(E e) {
		getCurrentSession().save(e);
		return e;
	}

	@Override
	public void update(E e) {
		getCurrentSession().update(e);
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void checkWho(E e) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	/**
	 * @return the entityClass
	 */
	public Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass the entityClass to set
	 */
	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	 

	 
	public void setTenant(Long tenant) {
		this.tenant = tenant;
	}

	 

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	 

	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		creationDate = new Date();
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		lastUpdateDate = new Date();
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date retDateFromStr(String dt, String srcFmt) {

		SimpleDateFormat formatter = new SimpleDateFormat(srcFmt);
		String dateInString = dt;
		Date date = new Date();
		try {
			date = formatter.parse(dateInString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public List<E> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateAuditCol(BaseEntity obj, boolean isCreate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getRecordCount(Class c) {
		// TODO Auto-generated method stub
		return null;
	}

 

}
