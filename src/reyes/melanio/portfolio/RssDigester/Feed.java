package reyes.melanio.portfolio.RssDigester;

// Container class for FeedItems.  Also contains the URI 

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import reyes.melanio.portfolio.RssDigester.hibernate.FeedItemDao;

public class Feed {

	private URI _uri;
	private Vector<FeedItem> _items;

	public Feed(String URI) throws URISyntaxException {
		setURI(new URI(URI));
		_items = new Vector<FeedItem>();
	}

	public void setURI(URI _uri) {
		this._uri = _uri;
	}

	public URI getURI() {
		return _uri;
	}

	public void setItems(Vector<FeedItem> _items) {
		this._items = _items;
	}

	public Vector<FeedItem> getItems() {
		return _items;
	}

	public void addItem(FeedItem item) {
		_items.add(item);
	}

	public void save() {
		FeedItem tempFeedItem;
		for (FeedItem item : _items) {
			tempFeedItem = null;
			tempFeedItem = FeedItemDao.getFeedItemByLink(item.getLink());
			// save item if it does not already exists in db
			if (tempFeedItem == null) {
				FeedItemDao.saveFeedItem(item);
			} 
			// update item if it exists in db and this is an update
			else if (tempFeedItem != null
					&& tempFeedItem.getPubDate().compareTo(item.getPubDate()) < 0)
				FeedItemDao.updateFeedItem(item);
		}
	}
}
