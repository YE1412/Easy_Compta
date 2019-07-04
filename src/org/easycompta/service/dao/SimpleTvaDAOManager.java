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

/**
 *
 * @author Yannick
 */
public class SimpleTvaDAOManager extends AbstractDAOManager implements TvaDAOManager{

    public SimpleTvaDAOManager() {
        super();
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Tva> getAllTva() {
		// TODO Auto-generated method stub
		List<Tva> tvaList = null;
	    TypedQuery q = session.getNamedQuery("findAllTvaOrderAsc");
	    tvaList =  (List<Tva>) q.getResultList();
        return tvaList;
	}

	@Override
	public Tva getTvaById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Tva tva = null;
	    try {
	    	em = newEntityManager();
	        // utilisation de l'EntityManager
	        tva = em.find(Tva.class, id);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	    }
		return tva;
	}
    
//    @Override
//    public List<Tva> getAllTva() {
//        List<Tva> tvaList = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Tva");
//           tvaList = (List<Tva>) q.list();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return tvaList;
//    }
//
//    @Override
//    public Tva getTvaById(int id) {
//        Tva tva = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Tva where id=:id");
//           q.setInteger("id", id);
//           tva = (Tva) q.uniqueResult();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return tva;
//    }
    
}
