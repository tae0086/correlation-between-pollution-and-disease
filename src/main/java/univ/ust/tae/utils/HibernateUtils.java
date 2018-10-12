package univ.ust.tae.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import univ.ust.tae.data.AirPollution;
import univ.ust.tae.data.Disease;

public class HibernateUtils {

	private static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(AirPollution.class);
		configuration.addAnnotatedClass(Disease.class);
		configuration.configure("hibernate.cfg.xml");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static Session openSession() {
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory.openSession();
	}

	public static void closeSessionFactory() {
		sessionFactory.close();
	}

}
