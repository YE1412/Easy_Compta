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

import org.easycompta.object.Comporter;
import org.easycompta.object.ProduitService;
import org.hibernate.Session;
import org.hibernate.Transaction;




/**
 *
 * @author Yannick
 */
public class SimpleServicesDAOManager extends AbstractDAOManager implements ServicesDAOManager {
	private Session session;
    public SimpleServicesDAOManager() {
    	super();
    }
    private Transaction getTx() {
		session = AbstractDAOManager.getHibernateSession();
		return session.beginTransaction();
	}
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<ProduitService> getAllServices() {
    	Transaction tx = getTx();
		List<ProduitService> servicesList = null;
//    	initDbDatas(0);
    	try {
//			em = newEntityManager();
		    TypedQuery q = (TypedQuery) session.createNamedQuery("findAllProduitServicesOrderAsc");
	        servicesList =  (List<ProduitService>) q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.close();
		} finally {
			session.close();
		}
        return servicesList;
    }

	@Override
	public int insertService(ProduitService service) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
	   try {
	      // utilisation de l'EntityManager
	      session.persist(service);
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
	public ProduitService getServiceById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		ProduitService p = null;
	    try {
//	    	em = newEntityManager();
	    	p = session.get(ProduitService.class, id);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      session.close();
	    }
		return p;
	}

	@Override
	public int deleteService(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
	    ProduitService p = null;
	    try {
	        // utilisation de l'EntityManager
	    	p = session.get(ProduitService.class, id);
	    	session.remove(p);
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
	public List<Integer> getOrdersId(int id) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		List<Comporter> compList = new ArrayList<Comporter>();
		List<Integer> idList = new ArrayList<Integer>();
		try {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Comporter> c = q.from(Comporter.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idProduitService"), p));
			TypedQuery<Tuple> query = session.createQuery(q);
			query.setParameter(p, id);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
				compList.add((Comporter) t.get(0));
			}
			for (Comporter comport:compList) {
				idList.add(comport.getId().getIdCommande());
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
	public int updateService(ProduitService service) {
		// TODO Auto-generated method stub
		Transaction tx = getTx();
		ProduitService c = null;
		try {
			session.saveOrUpdate(service);
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
