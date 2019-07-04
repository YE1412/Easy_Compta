/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easycompta.object.Personne;

/**
 *
 * @author Yannick
 */
public class SimpleActorsDAOManager extends AbstractDAOManager implements ActorsDAOManager{
    public SimpleActorsDAOManager() {
        super();
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Personne> getAllActors() {
		// TODO Auto-generated method stub
		List<Personne> actorsList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllPersonnesOrderAsc");
			actorsList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return actorsList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Personne> getAllSellers() {
		// TODO Auto-generated method stub
		List<Personne> sellersList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllSellersOrderAsc");
			sellersList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return sellersList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Personne> getAllBuyers() {
		// TODO Auto-generated method stub
		List<Personne> buyersList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllBuyersOrderAsc");
			buyersList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return buyersList;
	}

	@Override
	public int insertActor(Personne actor) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		   try {
		      em = newEntityManager();
		      // utilisation de l'EntityManager
//		      System.out.println("actor : "+actor);
		      em.persist(actor);
		      em.getTransaction().commit();
//		      System.err.println("addProduitService witdh id=" + service.getId());
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
	public Personne getActorById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Personne p = null;
		try {
			em = this.newEntityManager();
			p = em.find(Personne.class, id);
		}catch(Exception e) {
			e.printStackTrace();
			if (em != null) {
				closeEntityManager(em);
			}
		}
		return p;
	}

	@Override
	public int deleteActor(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Personne p = null;
		try {
			em = this.newEntityManager();
			p = em.find(Personne.class, id);
			em.remove(p);
			em.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (em != null) {
				closeEntityManager(em);
			}
		}
		return 1;
	}

	@Override
	public int updateActor(Personne actor) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Personne a = null;
		try {
			em = newEntityManager();
			a = em.find(Personne.class, actor.getId());
			a.setEmail(actor.getEmail());
			a.setCp(actor.getCp());
			a.setNom(actor.getNom());
			a.setNomRue(actor.getNomRue());
			a.setNumCommercant(actor.getNumCommercant());
			a.setNumRue(actor.getNumRue());
			a.setPrenom(actor.getPrenom());
			a.setTel(actor.getTel());
			a.setType(actor.getType());
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
//    public List<Personne> getAllActors() {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Personne> actorsList = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Personne");
//           actorsList = (List<Personne>) q.list();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return actorsList;
//    }
//
//    @Override
//    public int insertActor(Personne actor) {
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//         try {
//            tx = session.beginTransaction();
//            session.saveOrUpdate(actor);
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
//    public Personne getActorById(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        List<Personne> actorsList = null;
//        Personne actor = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Personne where id = ?");
//            q.setParameter(0, id);
//            actorsList = (List<Personne>)q.list();
//            actor = actorsList.get(0);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return actor;
//    }
//
//    @Override
//    public int deleteActor(int id) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        int retour = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("DELETE Personne where id = ?");
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
//    public List<Personne> getAllSellers() {
//        List<Personne> actorsList = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Personne where type = 1");
//            actorsList = (List<Personne>)q.list();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return actorsList;
//    }
//
//    @Override
//    public List<Personne> getAllBuyers() {
//        List<Personne> actorsList = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Personne where type = 0");
//            actorsList = (List<Personne>)q.list();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return actorsList;
//    }
    
}
