/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.easycompta.object.Langue;

/**
 *
 * @author Yannick
 */
public class SimpleLanguagesDAOManager extends AbstractDAOManager implements LanguagesDAOManager{
    public SimpleLanguagesDAOManager() {
        super();
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Langue> getAllLanguages() {
		// TODO Auto-generated method stub
		List<Langue> langList = null;
		EntityManager em = null;
		try {
			em = newEntityManager();
			TypedQuery q = (TypedQuery) em.createNamedQuery("findAllLanguagesOrderAsc");
			langList = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return langList;
	}

	@Override
	public int insertLanguage(Langue fac) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(fac);
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return 1;
	}

	@Override
	public Langue getLanguageById(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Langue l = null;
		try {
			em = newEntityManager();
			l = em.find(Langue.class, id);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return l;
	}

	@Override
	public int deleteLanguage(int id) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Langue l = null;
		try {
			em = newEntityManager();
			l = em.find(Langue.class, id);
			em.remove(l);
			em.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (em != null)
				closeEntityManager(em);
		}
		return 1;
	}

	@Override
	public int updateLanguage(Langue lan) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		Langue a = null;
		try {
			em = newEntityManager();
			a = em.find(Langue.class, lan.getId());
			a.setIdDevise(lan.getIdDevise());
			a.setLibelle(lan.getLibelle());
			a.setNom(lan.getNom());
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
//    public List<Langue> getAllLanguages() {
//        List<Langue> languagesList = null;
//        org.hibernate.Transaction tx = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try{
//           tx = session.beginTransaction();
//           Query q = session.createQuery ("from Langue");
//           languagesList = (List<Langue>) q.list();
//        }
//        catch(HibernateException e){
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return languagesList;
//    }
//
//    @Override
//    public int insertLanguage(Langue fac) {
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
//            session.close();
//        }
//        return 1;
//    }
//
//    @Override
//    public Langue getLanguageById(int id) {
//        List<Langue> languagesList = null;
//        Langue language = null;
//        session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            org.hibernate.Transaction tx = session.beginTransaction();
//            Query q = session.createQuery ("from Langue where id = ?");
//            q.setParameter(0, id);
//            languagesList = (List<Langue>)q.list();
//            language = languagesList.get(0);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        finally{
//            session.close();
//        }
//        return language;
//    }
//
//    @Override
//    public int deleteLanguage(int id) {
//        int retour = 0;
//        session = HibernateUtil.getSessionFactory().openSession();
//        org.hibernate.Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery ("DELETE Langue where id = ?");
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
}
