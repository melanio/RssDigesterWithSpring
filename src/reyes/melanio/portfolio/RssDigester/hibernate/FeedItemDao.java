package reyes.melanio.portfolio.RssDigester.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import reyes.melanio.portfolio.RssDigester.FeedItem;
import reyes.melanio.portfolio.RssDigester.hibernate.SessionFactoryUtil;

public class FeedItemDao {
	private static Logger logger = Logger.getLogger(FeedItemDao.class);

	public FeedItemDao() {

	}

	public static void updateFeedItem(FeedItem feedItem) {
		Transaction trans = null;
		Session session = SessionFactoryUtil.instance().getCurrentSession();
		try {
			trans = session.beginTransaction();
			session.update(feedItem);
			trans.commit();
		} catch (RuntimeException e) {
			if (trans != null && trans.isActive()) {
				try {
					logger.error(
							"Error saving item, rollbing back transaction", e);
					trans.rollback();
				} catch (HibernateException he) {
					logger.error("Error rolling back transaction", he);
				}
			}
		}
	}

	public static void saveFeedItem(FeedItem item) {
		Transaction trans = null;
		Session session = SessionFactoryUtil.instance().getCurrentSession();
		try {
			trans = session.beginTransaction();
			session.save(item);
			trans.commit();
		} catch (RuntimeException e) {
			if (trans != null && trans.isActive()) {
				try {
					logger.error(
							"Error updating item, rollbing back transaction", e);
					trans.rollback();
				} catch (HibernateException he) {
					logger.error("Error rolling back transaction", he);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static FeedItem getFeedItemByLink(String uri) {
		FeedItem feedItem = null;

		Session session = SessionFactoryUtil.instance().getCurrentSession();
		session.beginTransaction();
		try {
			List<FeedItem> results = session.createCriteria("reyes.melanio.portfolio.RssDigester.FeedItem").add(Restrictions.eq("link", uri)).list();
			if (results.size() > 0) {
				feedItem = (FeedItem) results.get(0);	
			}
		} catch (Exception e) {
			logger.error("Error in finding item", e);
		}
		return feedItem;
	}
}
