/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easycompta.object.Devise;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimpleDeviseDAOManager extends AbstractDAOManager implements DeviseDAOManager {
    private Session session;
	public SimpleDeviseDAOManager() {
        super();
    }
	private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Devise> getAllDevise() {
		// TODO Auto-generated method stub
		List<Devise> devList = null;
		Transaction tx = getTx();
		try {
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllDevises");
			devList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return devList;
	}    
}
