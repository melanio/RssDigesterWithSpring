package reyes.melanio.portfolio.RssDigester;

// Parser to read in RSS2.0 feeds and create FeedItem data objects

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

public class RssParser implements Parser {

	private DateParser Date_Parser;
	private String Format = "rss";
	
	protected RssParser() {

	}
	
	public String getFormat() {
		return Format;
	}
	
	public boolean checkFormat(Document document) {
		boolean result = false;
		String format = document.getRootElement().getName().toLowerCase();
		if ("rss".equals(format)) {
			result = true;
		}
		return result;		
	}

	public void parse(Document document, Feed feed) {
		Element root = document.getRootElement();
		parseRootElement(root, feed);
	}

	@SuppressWarnings("unchecked")
	private void parseRootElement(Element root, Feed feed) {
		List<Element> children = root.getChildren();
		for (Element child : children) {
			String name = child.getName().toLowerCase();
			if ("channel".equals(name)) {
				parseChannelElement(child, feed);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void parseChannelElement(Element channel, Feed feed) {
		List<Element> channelChildren = channel.getChildren();
		for (Element child: channelChildren) {
			String name = child.getName().toLowerCase();
			if ("item".equals(name))
				parseItemElement(child, feed);
		}
	}

	@SuppressWarnings("unchecked")
	private void parseItemElement(Element item, Feed feed) {
		FeedItem rssItem = new FeedItem();
		List<Element> rssItemFields = item.getChildren();
		for ( Element child : rssItemFields) {
			String name = child.getName().toLowerCase();
			if ("title".equals(name)) {
				rssItem.setTitle(child.getText());
			} else if ("link".equals(name)) {
				rssItem.setLink(child.getText());
			} else if ("description".equals(name)) {
				rssItem.setContent(child.getText().trim());
			} else if ("pubdate".equals(name)) {
				rssItem.setPubDate(Date_Parser.parse(child.getText()));
			}
		}
		feed.addItem(rssItem);
	}
	
	public void setDate_Parser( DateParser dateParser ){
		Date_Parser = dateParser;
	}
	
}
