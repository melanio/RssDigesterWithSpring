package reyes.melanio.portfolio.RssDigester;

// Simple Main Class to test RssDigester

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class MainTestRunner {
	static Logger logger = Logger.getLogger(MainTestRunner.class);

	public static void main(String[] args) {
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(
				"spring-cfg.xml"));

		FeedManager feedManager = (FeedManager) beanFactory
				.getBean("FeedManagerBean");
		Feed feed;
		// RSS Feed
		 feed = feedManager
				.createFeed("http://rss.slashdot.org/Slashdot/slashdot");
		saveFeed(feed, feedManager);
		// Atom Feed
		 feed = feedManager
			.createFeed("http://news.google.com/news?pz=1&cf=all&ned=us&hl=en&topic=h&num=10&output=atom");
		 saveFeed(feed, feedManager); 
	}
	
	private static void saveFeed (Feed feed, FeedManager feedManager) {
		if (feed != null) {
			feedManager.getNewsItemsFromFeed(feed);
			feed.save();
		}		
	}

}
