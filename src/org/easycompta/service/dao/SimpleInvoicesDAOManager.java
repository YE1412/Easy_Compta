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

/**
 *
 * @author Yannick
 */
public class SimpleInvoicesDAOManager extends AbstractDAOManager implements InvoicesDAOManager{
    public SimpleInvoicesDAOManager() {
       super();
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Facture> getAllInvoices() {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Facture> invList = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllInvoicesOrderDateDesc");
			invList = q.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return invList;
	}

	@Override
	public int insertInvoice(Facture fac) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(fac);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return 1;
	}

	@Override
	public Facture getInvoiceById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Facture fac = null;
		try {
			em = newEntityManager();
			fac = em.find(Facture.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return fac;
	}

	@Override
	public int deleteInvoice(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Facture fac = null;
		try {
			em = newEntityManager();
			fac = em.find(Facture.class, id);
			em.remove(fac);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return 1;
	}

	@Override
	public Commande getOrderByInvoiceId(int idFac){
		// TODO Auto-generated method stub
		EntityManager em = null;
		Commande ordr = null;
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Commande> q = cb.createQuery(Commande.class);
			Root<Commande> c = q.from(Commande.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(c).where(cb.equal(c.get("idFacture"), p));
			TypedQuery<Commande> query = em.createQuery(q);
			query.setParameter(p, idFac);
			ordr = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
		}
		return ordr;
	}

	@Override
	public int getSellerByInvoiceId(int idFac) throws Exception{
		// TODO Auto-generated method stub
		EntityManager em = null;
		int	id = 0;
//		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Concerner> c = q.from(Concerner.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("idVendeur"))).where(cb.equal(c.get("idFacture"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
			query.setParameter(p, idFac);
			Tuple t = query.getSingleResult();
			id = (int) t.get(0);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
//		}
		return id;
	}

	@Override
	public int getBuyerByInvoiceId(int idFac) throws Exception{
		// TODO Auto-generated method stub
		EntityManager em = null;
		int id = 0;
//		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Concerner> c = q.from(Concerner.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("idClient"))).where(cb.equal(c.get("idFacture"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
			query.setParameter(p, idFac);
			Tuple t = query.getSingleResult();
			id = (int) t.get(0);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
//		}
		return id;
	}

	@Override
	public int insertActors(Concerner conc) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(conc);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return 1;
	}

	@Override
	public int updateInvoice(Facture fac) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Facture f = null;
		try {
			em = newEntityManager();
			f = em.find(Facture.class, fac.getId());
			f.setDate(fac.getDate());
			f.setIdCommande(fac.getIdCommande());
			f.setIdLangue(fac.getIdLangue());
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
//    public List<Facture> getAllInvoices() {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Facture> invoicesList = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Facture");
//           invoicesList = (List<Facture>) q.list();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return invoicesList;
//    }
//
//    @Override
//    public int insertInvoice(Facture fac) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        int retour = 1;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            session.saveOrUpdate(fac);
//            session.flush();
//            tx.commit();
//        } catch (HibernateException e) {
//            tx.rollback();
//            e.printStackTrace();
//            return 0;
//        }
//        finally{
//            //System.out.println(fac.getId());
//            session.close();
//        }
//        retour = getLastInsertInvoiceId();
//        //System.out.println(retour);
//        return retour;
//    }
//
//    @Override
//    public Facture getInvoiceById(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Facture> invoicesList = null;
//        Facture invoice = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Facture where id = ?");
//            q.setParameter(0, id);
//            invoicesList = (List<Facture>)q.list();
//            invoice = invoicesList.get(0);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return invoice;
//    }
//
//    @Override
//    public int deleteInvoice(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("DELETE Facture where id = ?");
//            q.setParameter(0, id);
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
//    public Commande getOrderByInvoiceId(int idFac) {
//        Commande order = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Commande where id_facture = ?");
//            q.setParameter(0, idFac);
//            order = (Commande)q.uniqueResult();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return order;
//    }
//    
//    private int getLastInsertInvoiceId()
//    {
//        int retour = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("select max(id) from Facture");
//            retour = (int)q.uniqueResult();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return retour;
//    }
//
//    @Override
//    public int getSellerByInvoiceId(int idFac) {
//        int p = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("select c.idVendeur from Concerner c where  c.idFacture = :id");
//            q.setParameter("id", idFac);
//            p = q.uniqueResult() != null ? (int)q.uniqueResult() : 0;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return p;
//    }
//
//    @Override
//    public int getBuyerByInvoiceId(int idFac) {
//        int p = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("select c.idClient from Concerner c where  c.idFacture = :id");
//            q.setParameter("id", idFac);
//            p = q.uniqueResult() != null ? (int)q.uniqueResult() : 0;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return p;
//    }
//
//    @Override
//    public int insertActors(Concerner conc) {
//        int retour = 1;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            session.saveOrUpdate(conc);
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
//        return retour;
//    }
}
