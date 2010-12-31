package reyes.melanio.portfolio.RssDigester;


import org.jdom.Document;

public interface Parser {
	public String getFormat();
	public boolean checkFormat( Document document);
	public void parse(Document document, Feed feed);
}
