package com.bellaryinfotech.DAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.bellaryinfotech.common.BaseEntity;

 
 

/**
 *
 * @author Raja
 * @param <E>
 * @param <I>
 */
public interface GenericDAO<E, I> extends Serializable {

    public Session getCurrentSession();

    public List<E> findAll();

    public E find(I id);

    public void saveOrUpdate(E e);

    public void delete(E e);

    public void flush();

    public E save(E e);

    public void update(E e);
    
    public void checkWho(E e);

    public void clearCache();
    
    public void populateAuditCol(BaseEntity obj, boolean isCreate);
    
    //public void clearLookUpCache();
    
    public Long getRecordCount(Class c);

}
