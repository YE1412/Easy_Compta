/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easycompta.object.Tva;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimpleTvaDAOManager extends AbstractDAOManager implements TvaDAOManager{
	private Session session;
    public SimpleTvaDAOManager() {
        super();
    }
    
    private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Tva> getAllTva() {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<Tva> tvaList = null;
	    TypedQuery q = session.getNamedQuery("findAllTvaOrderAsc");
	    tvaList =  (List<Tva>) q.getResultList();
	    session.close();
        return tvaList;
	}

	@Override
	public Tva getTvaById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Tva tva = null;
	    try {
	    	// utilisation de l'EntityManager
	        tva = session.get(Tva.class, id);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.close();
		} finally {
	      session.close();
	    }
		return tva;
	}  
}
