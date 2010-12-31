package reyes.melanio.portfolio.RssDigester.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class SessionFactoryUtil {

	private static SessionFactory _sessionFactory = null;

	private SessionFactoryUtil() {
	}

	public static synchronized SessionFactory instance() {
		if (_sessionFactory == null) {
			_sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		}
		return _sessionFactory;
	}

	public Session openSession() {
		return _sessionFactory.openSession();
	}

	public Session getCurrentSession() {
		return _sessionFactory.getCurrentSession();
	}

	public static void close() {
		if (_sessionFactory != null)
			_sessionFactory.close();
		_sessionFactory = null;

	}
}
