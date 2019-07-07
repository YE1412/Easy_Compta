/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.easycompta.object.Commande;
import org.easycompta.object.Concerner;
import org.easycompta.object.Facture;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimpleInvoicesDAOManager extends AbstractDAOManager implements InvoicesDAOManager{
    private Session session;
	public SimpleInvoicesDAOManager() {
       super();
    }
	
	private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Facture> getAllInvoices() {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<Facture> invList = null;
		try {
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllInvoicesOrderDateDesc");
			invList = q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return invList;
	}

	@Override
	public int insertInvoice(Facture fac) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.persist(fac);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public Facture getInvoiceById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Facture fac = null;
		try {
			fac = session.get(Facture.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return fac;
	}

	@Override
	public int deleteInvoice(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Facture fac = null;
		try {
			fac = session.get(Facture.class, id);
			session.remove(fac);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public Commande getOrderByInvoiceId(int idFac){
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Commande ordr = null;
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Commande> q = cb.createQuery(Commande.class);
			Root<Commande> c = q.from(Commande.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(c).where(cb.equal(c.get("idFacture"), p));
			TypedQuery<Commande> query = session.createQuery(q);
			query.setParameter(p, idFac);
			ordr = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
		}finally {
		      session.close();
		}
		return ordr;
	}

	@Override
	public int getSellerByInvoiceId(int idFac) throws Exception{
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		int	id = 0;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> q = cb.createTupleQuery();
		Root<Concerner> c = q.from(Concerner.class);
		ParameterExpression<Integer> p = cb.parameter(Integer.class);
	    q.select(cb.tuple(c.get("idVendeur"))).where(cb.equal(c.get("idFacture"), p));
		TypedQuery<Tuple> query = session.createQuery(q);
		query.setParameter(p, idFac);
		Tuple t = query.getSingleResult();
		id = (int) t.get(0);
		session.close();
		return id;
	}

	@Override
	public int getBuyerByInvoiceId(int idFac) throws Exception{
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		int id = 0;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Tuple> q = cb.createTupleQuery();
		Root<Concerner> c = q.from(Concerner.class);
		ParameterExpression<Integer> p = cb.parameter(Integer.class);
	    q.select(cb.tuple(c.get("idClient"))).where(cb.equal(c.get("idFacture"), p));
		TypedQuery<Tuple> query = session.createQuery(q);
		query.setParameter(p, idFac);
		Tuple t = query.getSingleResult();
		id = (int) t.get(0);
		session.close();
		return id;
	}

	@Override
	public int insertActors(Concerner conc) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.persist(conc);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public int updateInvoice(Facture fac) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Facture f = null;
		try {
			session.saveOrUpdate(fac);
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
