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

/**
 *
 * @author Yannick
 */
public class SimplePaymentsDAOManager extends AbstractDAOManager implements PaymentsDAOManager{
    public SimplePaymentsDAOManager() {
        super();
        }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Paiement> getAllPayments() {
		// TODO Auto-generated method stub
		List<Paiement> payList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllPaymentsOrderASC");
			payList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return payList;
	}

	@Override
	public int insertPayment(Paiement pay) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(pay);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			closeEntityManager(em);
		}
		return 1;
	}

	@Override
	public int insertOrdersInPayment(Composed comp) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			em = newEntityManager();
			for (Integer id:comp.getCompose()) {
				Composer c=new Composer();
				c.setId(new ComposerId(comp.getIdPayment(), id));
				em.persist(c);
			}
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
	public Paiement getPaymentById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Paiement p = null;
		try {
			em = newEntityManager();
			p = em.find(Paiement.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return p;
	}

	@Override
	public int deletePayment(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Paiement p = null;
		try {
			em = newEntityManager();
			p = em.find(Paiement.class, id);
			em.remove(p);
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
	public int deleteComposed(int idP) {
		// TODO Auto-generated method stub
		EntityManager em = null;
//		List<Composer> compList = new ArrayList<Composer>();
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Composer> c = q.from(Composer.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idPaiement"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
			query.setParameter(p, idP);
			List<Tuple> results = query.getResultList();
			for (Tuple t:results) {
//				compList.add((Composer) t.get(0));
				em.remove(t.get(0));
			}
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
	public List<Integer> getOrdersByPaymentId(int idP) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<ComposerId> compList = new ArrayList<ComposerId>();
		List<Integer> idList = new ArrayList<Integer>();
		try {
			em = newEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> q = cb.createTupleQuery();
			Root<Composer> c = q.from(Composer.class);
			ParameterExpression<Integer> p = cb.parameter(Integer.class);
		    q.select(cb.tuple(c.get("id"))).where(cb.equal(c.get("id").get("idPaiement"), p));
			TypedQuery<Tuple> query = em.createQuery(q);
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
		}finally {
		      if (em != null) {
		    	  closeEntityManager(em);
		      }
		}
		return idList;
	}

	@Override
	public int updatePayment(Paiement pay) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Paiement p = null;
		try {
			em = newEntityManager();
			p = em.find(Paiement.class, pay.getId());
			p.setEtat(pay.getEtat());
			p.setReste(pay.getReste());
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
//    public List<Paiement> getAllPayments() {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Paiement> paymentsList = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Paiement");
//           paymentsList = (List<Paiement>) q.list();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return paymentsList;
//    }
//
//    @Override
//    public int insertPayment(Paiement pay) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            session.saveOrUpdate(pay);
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
//    public Paiement getPaymentById(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Paiement> paymentsList = null;
//        Paiement payment = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Paiement where id = ?");
//            q.setParameter(0, id);
//            paymentsList = (List<Paiement>)q.list();
//            payment = paymentsList.get(0);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return payment;
//    }
//
//    @Override
//    public int deletePayment(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        int retour = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("DELETE Paiement where id = ?");
//            q.setParameter(0, id);
//            retour = q.executeUpdate();
//            tx.commit();
//        } catch (HibernateException e) {
//            tx.rollback();
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return retour;
//    }
//
//    @Override
//    public List<Integer> getOrdersByPaymentId(int idP) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        //List<Paiement> paymentsList = null;
//        List<Integer> retour = new ArrayList<>();
//        List<ComposerId> compList = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("select c.id from Composer c where id_paiement = ?");
//            q.setParameter(0, idP);
//            compList = (List<ComposerId>)q.list();
//            compList.forEach((item) -> {
//                retour.add(item.getIdCommande());
//            });
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
//    public int deleteComposed(int idP) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//         int retour = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("delete Composer where id_paiement = :id");
//            q.setParameter("id", idP);
//            retour = q.executeUpdate();
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
//
//    @Override
//    public int insertOrdersInPayment(Composed comp) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            comp.getCompose().forEach((item) -> {
//                Composer com=new Composer();
//                com.setId(new ComposerId(item, comp.getIdPayment()));
//                session.saveOrUpdate(com);
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
}
