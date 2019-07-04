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

/**
 *
 * @author Yannick
 */
public class SimpleDeviseDAOManager extends AbstractDAOManager implements DeviseDAOManager {
    public SimpleDeviseDAOManager() {
        super();
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Devise> getAllDevise() {
		// TODO Auto-generated method stub
		List<Devise> devList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllDevises");
			devList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return devList;
	}
    
    
//    @Override
//    public List<Devise> getAllDevise() {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Devise> deviseList = null;
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Devise as devise");
//            deviseList = (List<Devise>) q.list();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return deviseList;
//    }
    
}
