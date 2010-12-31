package reyes.melanio.portfolio.RssDigester;

// Parser to read in Atom feeds and create FeedItem data objects

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

public class AtomParser implements Parser {

	private DateParser Date_Parser;
	private String Format = "atom";
	private static final String ATOM_NAMESPACE = "http://www.w3.org/2005/Atom";
	
	protected AtomParser() {

	}
	
	public String getFormat() {
		return Format;
	}
	
	public boolean checkFormat( Document document) {
		boolean result = false;
		String namespace = document.getRootElement().getNamespace().getURI();
		if (namespace.equals(ATOM_NAMESPACE)) {
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
			if ("entry".equals(name)) {
				parseEntryElement(child, feed);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void parseEntryElement(Element entry, Feed feed) {
		FeedItem atomEntry = new FeedItem();

		List<Element> atomEntryFields = entry.getChildren();
		for (Element child : atomEntryFields ) {
			String name = child.getName().toLowerCase();
			if ("title".equals(name)) {
				atomEntry.setTitle(child.getText());
			} else if ("link".equals(name)) {
				atomEntry.setLink(child.getAttributeValue("href"));
			} else if ("summary".equals(name) || "content".equals(name)) {
				atomEntry.setContent(parseContent(child));
			} else if ("updated".equals(name)) {
				atomEntry.setPubDate(Date_Parser.parse(child.getText()));
			}
		}
		feed.addItem(atomEntry);
	}

	@SuppressWarnings("unchecked")
	private String parseContent(Element content) {
		String type = content.getAttributeValue("type");
		String text = content.getText().trim();
		if ("xhtml".equals(type)) {
			List<Element> children = content.getChildren();
			for (Element contentChild : children) {
				text = contentChild.getValue().trim();
			}
		}

		return text;
	}	
	
	public void setDate_Parser( DateParser dateParser ){
		this.Date_Parser = dateParser;
	}
}
