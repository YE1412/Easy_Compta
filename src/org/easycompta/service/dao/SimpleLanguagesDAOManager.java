/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easycompta.object.Langue;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimpleLanguagesDAOManager extends AbstractDAOManager implements LanguagesDAOManager{
    private Session session;
	public SimpleLanguagesDAOManager() {
        super();
    }
	private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Langue> getAllLanguages() {
		// TODO Auto-generated method stub
		List<Langue> langList = null;
		Transaction tx = getTx();
		try {
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllLanguagesOrderAsc");
			langList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return langList;
	}

	@Override
	public int insertLanguage(Langue fac) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.persist(fac);
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public Langue getLanguageById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Langue l = null;
		try {
			l = session.get(Langue.class, id);
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return l;
	}

	@Override
	public int deleteLanguage(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Langue l = null;
		try {
			l = session.get(Langue.class, id);
			session.remove(l);
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public int updateLanguage(Langue lan) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Langue a = null;
		try {
			session.saveOrUpdate(lan);
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}
}
