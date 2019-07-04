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

import org.easycompta.object.Commande;
import org.easycompta.object.Comporter;
import org.easycompta.object.ComporterId;
import org.easycompta.object.Contains;
import org.easycompta.object.Tva;

/**
 *
 * @author Yannick
 */
public class SimpleOrdersDAOManager extends AbstractDAOManager implements OrdersDAOManager{
    public SimpleOrdersDAOManager() {
        super();
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Commande> getAllOrders() {
		// TODO Auto-generated method stub
		List<Commande> ordersList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q  = (TypedQuery) em.createNamedQuery("findAllOrdersByAsc");
			ordersList = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return ordersList;
	}

	@Override
	public int insertOrder(Commande order) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		   try {
		      em = newEntityManager();
		      // utilisation de l'EntityManager
		      em.persist(order);
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
	public int insertServicesInOrder(Contains cont) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		   try {
		   	 em = newEntityManager();
		   	 for (Integer id:cont.getComporte()) {
		   		 Comporter comp=new Comporter();
		         comp.setId(new ComporterId(cont.getIdCommande(), id));
		         em.persist(comp);
		   	 }
		   	 em.getTransaction().commit();
		   } catch(Exception e) {
			   e.printStackTrace();
			   return 0;
		   } finally{
			   if (em != null) {
				   closeEntityManager(em);   
			   }
		   }
		return 1;
	}

	@Override
	public Commande getOrderById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Commande c = null;
		try {
			em = newEntityManager();
			c = em.find(Commande.class, id);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				closeEntityManager(em);
			}
		}
		return c;
	}

	@Override
	public int deleteOrder(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Commande c = null;
		try {
			em = newEntityManager();
			c = em.find(Commande.class, id);
			em.remove(c);
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

	@Override
	public Commande getOrderByInvoiceId(int idInvoice) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Commande o = null;
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Commande> q = cb.createQuery(Commande.class);
			Root<Commande> c = q.from(Commande.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
			q.select(c).where(cb.equal(c.get("idFacture"), p));
			TypedQuery<Commande> query = em.createQuery(q);
			query.setParameter(p, idInvoice);
			o = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeEntityManager(em);
		}
		return o;
	}

	@Override
	public List<Integer> getContains(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<ComporterId> compList = new ArrayList<ComporterId>();
		List<Integer> servsList = new ArrayList<Integer>();
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Comporter> c = q.from(Comporter.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idCommande"), p));
//			q.multiselect(c.get("id")).where(cb.equal(c.get("idCommande"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
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
		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
		}
		return servsList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Tva> getAllTva() {
		// TODO Auto-generated method stub
		List<Tva> tvaList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q  = (TypedQuery) em.createNamedQuery("findAllTVA");
			tvaList = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return tvaList;
	}

	@Override
	public int deleteContains(int idCommande) {
		// TODO Auto-generated method stub
		EntityManager em = null;
//		List<Comporter> compList = new ArrayList<Comporter>();
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Comporter> c = q.from(Comporter.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idCommande"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
			query.setParameter(p, idCommande);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
//				compList.add((Comporter) t.get(0));
				em.remove((Comporter) t.get(0));
			}
//			em.remove(compList);
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
		}
		return 1;
	}

	@Override
	public Tva getTvaById(int idTva) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Tva tva = null;
		try {
			em = newEntityManager();
			tva = em.find(Tva.class, idTva);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				closeEntityManager(em);
			}
		}
		return tva;
	}

	@Override
	public int updateOrder(Commande order) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Commande c = null;
		try {
			em = newEntityManager();
			c = em.find(Commande.class, order.getId());
			c.setContenuAdditionnel(order.getContenuAdditionnel());
			c.setIdFacture(order.getIdFacture());
			c.setIdTva(order.getIdTva());
			c.setPriceHt(order.getPriceHt());
			c.setPriceTt(order.getPriceTt());
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

    
//    @Override
//    public List<Commande> getAllOrders() {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Commande> ordersList = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Commande");
//           ordersList = (List<Commande>) q.list();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return ordersList;
//    }
//
//    @Override
//    public int insertOrder(Commande order) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            session.saveOrUpdate(order);
//            session.flush();
//            tx.commit();
//        } catch (HibernateException e) {
//            tx.rollback();
//            e.printStackTrace();
//            return 0;
//        }
//        finally{
//            //System.out.println(order.getId()+" "+order.getIdFacture());
//            session.close();
//        }
//        return 1;
//    }
//
//    @Override
//    public Commande getOrderById(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Commande> ordersList = null;
//        Commande order = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Commande where id = ?");
//            q.setParameter(0, id);
//            ordersList = (List<Commande>)q.list();
//            order = ordersList.get(0);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return order;
//    }
//
//    @Override
//    public int deleteOrder(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("DELETE Commande where id = ?");
//            q.setParameter(0, id);
//            q.executeUpdate();
//            tx.commit();
//        } catch (HibernateException e) {
//            tx.rollback();
//            e.printStackTrace();
//            return 0;
//        }
//        finally{
//            session.close();
//        }
//        return 1;
//    }
//
//    @Override
//    public Commande getOrderByInvoiceId(int idInvoice) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Commande> ordersList = null;
//        Commande order = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Commande where idFacture = ?");
//            q.setParameter(0, idInvoice);
//            ordersList = (List<Commande>)q.list();
//            order = ordersList.get(0);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return order;
//    }
//
//    @Override
//    public List<Tva> getAllTva() {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Tva> tvaList = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Tva");
//            tvaList = (List<Tva>)q.list();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return tvaList;
//    }
//
//    @Override
//    public int insertServicesInOrder(Contains cont) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            cont.getComporte().forEach((item) -> {
//                Comporter comp=new Comporter();
//                comp.setId(new ComporterId(cont.getIdCommande(), item));
//                session.saveOrUpdate(comp);
//            });
//            session.flush();
//            tx.commit();
//        } catch (HibernateException e) {
//            tx.rollback();
//            e.printStackTrace();
//            return 0;
//        }
//        finally{
//            session.close();
//        }
//        return 1;
//    }
//
//    @Override
//    public List<Integer> getContains(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<ComporterId> compList = null;
//        List<Integer> servList = new ArrayList<>();
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("select c.id from Comporter c where id_commande = ?");
//            q.setParameter(0, id);
//            compList = (List<ComporterId>)q.list();
//            compList.forEach((item) -> {
//                servList.add(item.getIdProduitService());
//            });
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return servList;
//    }
//
//    @Override
//    public int deleteContains(int idCommande) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        int retour = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("delete Comporter where id_commande = :id");
//            q.setParameter("id", idCommande);
//            //session.
//            retour = q.executeUpdate();
//            tx.commit();
//        } catch (HibernateException e) {
//            tx.rollback();
//            e.printStackTrace();
//            return -1;
//        }
//        finally{
//            session.close();
//        }
//        return retour;
//    }
//
//    @Override
//    public Tva getTvaById(int idTva) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Tva> tvaList = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Tva c where id = ?");
//            q.setParameter(0, idTva);
//            tvaList = (List<Tva>)q.list();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return tvaList.get(0);
//    }
    
    
}
