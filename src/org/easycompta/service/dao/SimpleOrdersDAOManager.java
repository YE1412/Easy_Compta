/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.easycompta.object.Commande;
import org.easycompta.object.Comporter;
import org.easycompta.object.ComporterId;
import org.easycompta.object.Contains;
import org.easycompta.object.Tva;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Yannick
 */
public class SimpleOrdersDAOManager extends AbstractDAOManager implements OrdersDAOManager{
    private Session session;
	public SimpleOrdersDAOManager() {
        super();
    }
	private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Commande> getAllOrders() {
		// TODO Auto-generated method stub
		List<Commande> ordersList = null;
		Transaction tx = getTx();
		try {
			TypedQuery q  = (TypedQuery) session.createNamedQuery("findAllOrdersByAsc");
			ordersList = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return ordersList;
	}

	@Override
	public int insertOrder(Commande order) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
		     // utilisation de l'EntityManager
		      session.persist(order);
		      tx.commit();
//		      System.err.println("addProduitService witdh id=" + service.getId());
		   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.close();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public int insertServicesInOrder(Contains cont) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
	   try {
//	   	 em = newEntityManager();
	   	 for (Integer id:cont.getComporte()) {
	   		 Comporter comp=new Comporter();
	         comp.setId(new ComporterId(cont.getIdCommande(), id));
	         session.persist(comp);
	   	 }
	   	 tx.commit();
	   } catch(Exception e) {
		   e.printStackTrace();
		   session.close();
		   return 0;
	   } finally{
		   session.close();
	   }
		return 1;
	}

	@Override
	public Commande getOrderById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Commande c = null;
		try {
			c = session.get(Commande.class, id);
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return c;
	}

	@Override
	public int deleteOrder(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Commande c = null;
		try {
			c = session.get(Commande.class, id);
			session.remove(c);
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
		return 1;
	}

	@Override
	public Commande getOrderByInvoiceId(int idInvoice) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Commande o = null;
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Commande> q = cb.createQuery(Commande.class);
			Root<Commande> c = q.from(Commande.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
			q.select(c).where(cb.equal(c.get("idFacture"), p));
			TypedQuery<Commande> query = session.createQuery(q);
			query.setParameter(p, idInvoice);
			o = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return o;
	}

	@Override
	public List<Integer> getContains(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<ComporterId> compList = new ArrayList<ComporterId>();
		List<Integer> servsList = new ArrayList<Integer>();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Comporter> c = q.from(Comporter.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idCommande"), p));
//			q.multiselect(c.get("id")).where(cb.equal(c.get("idCommande"), p));
			TypedQuery<Tuple> query = session.createQuery(q);
			query.setParameter(p, id);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
				compList.add((ComporterId) t.get(0));
			}
			compList.forEach((item) -> {
              servsList.add(item.getIdProduitService());
          });
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
		}finally {
		      session.close();
		}
		return servsList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Tva> getAllTva() {
		// TODO Auto-generated method stub
		List<Tva> tvaList = null;
		Transaction tx = getTx();
		try {
			TypedQuery q  = (TypedQuery) session.createNamedQuery("findAllTVA");
			tvaList = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return tvaList;
	}

	@Override
	public int deleteContains(int idCommande) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
//		List<Comporter> compList = new ArrayList<Comporter>();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Comporter> c = q.from(Comporter.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idCommande"), p));
			TypedQuery<Tuple> query = session.createQuery(q);
			query.setParameter(p, idCommande);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
//				compList.add((Comporter) t.get(0));
				session.remove((Comporter) t.get(0));
			}
//			em.remove(compList);
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
	public Tva getTvaById(int idTva) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		Tva tva = null;
		try {
//			em = newEntityManager();
			tva = session.get(Tva.class, idTva);
		} catch(Exception e) {
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
		return tva;
	}

	@Override
	public int updateOrder(Commande order) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		try {
			session.saveOrUpdate(order);
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
