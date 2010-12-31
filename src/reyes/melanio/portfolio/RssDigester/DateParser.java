package reyes.melanio.portfolio.RssDigester;

import java.text.ParseException;
import java.util.Date;

public class DateParser {
	private static DateFormat[] DATE_FORMATS;

	protected DateParser() {
		
	}
	
	public Date parse(String dateString) {
		Date result = new Date();
			for (DateFormat dateformat : DATE_FORMATS)
				try {
					result = dateformat.parse(dateString.trim());
				} catch (ParseException e) {
			}
		return result;
	}

	public void setDATE_FORMATS(DateFormat[] dates_formats) {
		DateParser.DATE_FORMATS = dates_formats;
	}

}
