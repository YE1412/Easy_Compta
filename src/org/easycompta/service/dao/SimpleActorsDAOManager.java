/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.easycompta.object.Personne;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimpleActorsDAOManager extends AbstractDAOManager implements ActorsDAOManager{
    private Session session;
	public SimpleActorsDAOManager() {
        super();
    }
	private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Personne> getAllActors() {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<Personne> actorsList = null;
		try {
//			em = session.getEntityManagerFactory().createEntityManager();
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllPersonnesOrderAsc");
			actorsList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return actorsList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Personne> getAllSellers() {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<Personne> sellersList = null;
		try {
//			em = session.getEntityManagerFactory().createEntityManager();
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllSellersOrderAsc");
			sellersList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return sellersList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Personne> getAllBuyers() {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<Personne> buyersList = null;
		try {
//			em = session.getEntityManagerFactory().createEntityManager();
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllBuyersOrderAsc");
			buyersList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return buyersList;
	}

	@Override
	public int insertActor(Personne actor) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		   try {
		      session.persist(actor);
		      tx.commit();
		   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return 1;
	}
	@Override
	public Personne getActorById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Personne p = null;
		try {
			p = session.get(Personne.class, id);
		}catch(Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return p;
	}

	@Override
	public int deleteActor(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Personne p = null;
		try {
			p = session.get(Personne.class, id);
			session.remove(p);
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public int updateActor(Personne actor) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.saveOrUpdate(actor);
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
