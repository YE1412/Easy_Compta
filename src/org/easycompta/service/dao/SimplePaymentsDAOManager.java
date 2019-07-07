/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.easycompta.object.Composed;
import org.easycompta.object.Composer;
import org.easycompta.object.ComposerId;
import org.easycompta.object.Paiement;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimplePaymentsDAOManager extends AbstractDAOManager implements PaymentsDAOManager{
    private Session session;
	public SimplePaymentsDAOManager() {
        super();
    }
	
	private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Paiement> getAllPayments() {
		// TODO Auto-generated method stub
		List<Paiement> payList = null;
		Transaction tx = getTx();
		try {
			TypedQuery q = (TypedQuery) session.createNamedQuery("findAllPaymentsOrderASC");
			payList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return payList;
	}

	@Override
	public int insertPayment(Paiement pay) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.persist(pay);
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
	public int insertOrdersInPayment(Composed comp) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			for (Integer id:comp.getCompose()) {
				Composer c=new Composer();
				c.setId(new ComposerId(comp.getIdPayment(), id));
				session.persist(c);
			}
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
	public Paiement getPaymentById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Paiement p = null;
		try {
			p = session.get(Paiement.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return p;
	}

	@Override
	public int deletePayment(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Paiement p = null;
		try {
			p = session.get(Paiement.class, id);
			session.remove(p);
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
	public int deleteComposed(int idP) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
//		List<Composer> compList = new ArrayList<Composer>();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Composer> c = q.from(Composer.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idPaiement"), p));
			TypedQuery<Tuple> query = session.createQuery(q);
			query.setParameter(p, idP);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
//				compList.add((Composer) t.get(0));
				session.remove(t.get(0));
			}
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
			return 0;
		}finally {
		      session.close();
		}
		return 1;
	}

	@Override
	public List<Integer> getOrdersByPaymentId(int idP) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<ComposerId> compList = new ArrayList<ComposerId>();
		List<Integer> idList = new ArrayList<Integer>();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Composer> c = q.from(Composer.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idPaiement"), p));
			TypedQuery<Tuple> query = session.createQuery(q);
			query.setParameter(p, idP);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
				compList.add((ComposerId) t.get(0));
			}
			for (ComposerId comp:compList) {
				idList.add(comp.getIdCommande());
			}
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
		}finally {
		      session.close();
		}
		return idList;
	}

	@Override
	public int updatePayment(Paiement pay) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.saveOrUpdate(pay);
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
