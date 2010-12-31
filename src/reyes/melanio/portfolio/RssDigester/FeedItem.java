package reyes.melanio.portfolio.RssDigester;

// FeedItem DataObject.  
// Corresponds to the <item> Entity in a RSS Feed
// Corresponds to the <entry> Entity in a Atom Feed

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class FeedItem {
	@Id
	@GeneratedValue
	private Integer id;
	private String title;
	private String link;
	private String content;
	private Date pubDate;

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Integer getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getLink() {
		return this.link;
	}

	public String getContent() {
		return this.content;
	}

	public Date getPubDate() {
		return this.pubDate;
	}

	@Override
	public String toString() {
		return "title: " + title + " link: " + link + " content: " + content
				+ " pubDate: " + pubDate.toString();
	}
}
