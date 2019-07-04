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




/**
 *
 * @author Yannick
 */
public class SimpleServicesDAOManager extends AbstractDAOManager implements ServicesDAOManager {
//	Session session = null;
//    EntityManagerFactory factory =  null;
    public SimpleServicesDAOManager() {
    	super();
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<ProduitService> getAllServices() {
		List<ProduitService> servicesList = null;
		EntityManager em = null;
//    	initDbDatas();
    	try {
			em = newEntityManager();
		    TypedQuery q = (TypedQuery) em.createNamedQuery("findAllProduitServicesOrderAsc");
	        servicesList =  (List<ProduitService>) q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
        return servicesList;
    }

	@Override
	public int insertService(ProduitService service) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		   try {
		      em = newEntityManager();
		      // utilisation de l'EntityManager
		      em.persist(service);
		      em.getTransaction().commit();
//		      System.err.println("addProduitService witdh id=" + service.getId());
		   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
		   }
		return 1;
	}

	@Override
	public ProduitService getServiceById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		ProduitService p = null;
	    try {
	    	em = newEntityManager();
	        // utilisation de l'EntityManager
	        p = em.find(ProduitService.class, id);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	    }
		return p;
	}

	@Override
	public int deleteService(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
	    ProduitService p = null;
	    try {
	    	em = newEntityManager();
	        // utilisation de l'EntityManager
	    	p = em.find(ProduitService.class, id);
	    	em.remove(p);
	    	em.getTransaction().commit();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	    }
		return 1;
	}

	@Override
	public List<Integer> getOrdersId(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Comporter> compList = new ArrayList<Comporter>();
		List<Integer> idList = new ArrayList<Integer>();
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Comporter> c = q.from(Comporter.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idProduitService"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
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
		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
		}
		return idList;
	}

	@Override
	public int updateService(ProduitService service) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		ProduitService c = null;
		try {
			em = newEntityManager();
			c = em.find(ProduitService.class, service.getId());
			c.setMontantHt(service.getMontantHt());
			c.setNom(service.getNom());
			c.setQuantite(service.getQuantite());
			em.flush();
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (em != null) {
				closeEntityManager(em);
			}
		}
		return 1;
	}
}
