package org.easycompta.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractDAOManager {
//	final protected Session session;
	static SessionFactory factory;
	public AbstractDAOManager() {
//		factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		//    	StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
//        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();  
//        session = factory.openSession();
//        factory=meta.getSessionFactoryBuilder().build();  
//        session=factory.openSession(); 
//        this.session =  HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        factory = session.getEntityManagerFactory();
		factory = createFactory();
    }
	public static SessionFactory createFactory() {
		factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		return factory;
	}
	public static Session getHibernateSession() {
		Session session = factory.openSession();
		return session;
	}
	// Créer un EM et ouvrir une transaction
// 	protected EntityManager newEntityManager() throws Exception{
// 	   EntityManager em = factory.createEntityManager();
// 	   //factory.addNamedQuery(query.getIdentifier(), em.createQuery(query.getQuery()));
// 	   em.getTransaction().begin();
// 	   return (em);
// 	}
// 	protected void initDbDatas() {
//// 		List<Tva> tvaList = new ArrayList<Tva>();
// 		try {
//			EntityManager em = newEntityManager();
//			Tva tva1  = new Tva((float) 0.2, "Tva 20%");
//	 		Tva tva2  = new Tva((float) 0.02, "Tva 2%");
//	 		Tva tva3  = new Tva((float) 0.05, "Tva 5%");
//	 		Devise dev1 = new Devise('$');
//	 		Devise dev2 = new Devise('€');
//	 		Devise dev3 = new Devise('£');
//	 		em.persist(tva1);
//	 		em.persist(tva2);
//	 		em.persist(tva3);
//	 		em.persist(dev1);
//	 		em.persist(dev2);
//	 		em.persist(dev3);
//	 		Langue lang1 = new Langue("US", "Anglais (Américain)", 1);
//	 		Langue lang2 = new Langue("FR", "Français", 2);
//	 		Langue lang3 = new Langue("EN", "Anglais", 3);
//	 		em.persist(lang1);
//	 		em.persist(lang2);
//	 		em.persist(lang3);
//	 		em.getTransaction().commit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
// 	}
 	// Fermer un EM et défaire la transaction si nécessaire
 	protected void closeEntityManager(EntityManager em) {
 	   if (em != null) {
 	      if (em.isOpen()) {
 	         EntityTransaction t = em.getTransaction();
 	         if (t.isActive()) {
 	            try {
 	               t.rollback();
 	            } catch (PersistenceException e) {
 	            }
 	         }
 	         em.close();
 	      }
 	   }
 	}
}
