package reyes.melanio.portfolio.RssDigester;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormat {
	private SimpleDateFormat simpleDateFormat;
	
	DateFormat( String format) {
		simpleDateFormat = new SimpleDateFormat(format,
				Locale.ENGLISH);
	}
	
	public Date parse(String dateString) throws ParseException {
		return simpleDateFormat.parse(dateString);
	}

}
