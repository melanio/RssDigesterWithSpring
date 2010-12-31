package reyes.melanio.portfolio.RssDigester;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

public class FeedManager {

	private static Logger logger = Logger.getLogger(FeedManager.class);
	private Parser[] parsers;

	private FeedManager() {
	}

	public void setParsers(Parser[] parsers) {
		this.parsers = parsers;
	}

	public Feed createFeed(String URL) {

		Feed feed = null;
		try {
			feed = new Feed(URL);
		} catch (URISyntaxException e) {
			logger.error("Failed to create feed", e);
		}
		return feed;

	}

	public void getNewsItemsFromFeed(Feed feed) {
		HttpClient client = new HttpClient();
		if ("feed".equals(feed.getURI().getScheme())) {
			Protocol feedProtocol = new Protocol("feed",
					new DefaultProtocolSocketFactory(), 80);
			Protocol.registerProtocol("feed", feedProtocol);
			client.getHostConfiguration().setHost(feed.getURI().getHost(), 80,
					feedProtocol);
		}

		HttpMethod getMethod = new GetMethod(feed.getURI().toString());
		int responseCode = 0;
		try {
			responseCode = client.executeMethod(getMethod);

			if (responseCode != 404) {
				InputStream inputStream = getMethod.getResponseBodyAsStream();
				BufferedReader bufferedinStream = new BufferedReader(
						new InputStreamReader(inputStream));
				Document document = null;
				SAXBuilder builder = new SAXBuilder();
				document = builder.build(bufferedinStream);
				parse(document, feed);
			}

		} catch (Exception e) {
			logger.error("Failed to get news items from feed", e);
		}
	}

	private void parse(Document document, Feed feed) {
		getParser(document).parse(document, feed);
	}

	private Parser getParser (Document document) {
		Parser result = null;
		for ( Parser parser : parsers ) {
			if ( parser.checkFormat(document)) {
				result = parser;
				break;
			}
		}			
		return result;
	}

}
