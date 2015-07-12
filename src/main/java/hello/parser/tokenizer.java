import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tokenizer {
	/* Example tokens to tokenize:
	I saw a guy get mugged near Civic Center Bart Station. Time:<time>
	I got attacked by a guy in Tenderloin. Time:<time>
	My friend was sexually assaulted at the Hogwash College Campus. Time:<time>

	Assault Type: followed by "get", "got", "was"
	Place: followed by "at", "near", "in"
	Time: followed by "Time:"
	*/
	public static String timeToken = "Time:";
	public static Map<String, String> tokenizeIncident(String incidentText) {
		// Keys: type, poi, time
		Map<String, String> incidentMetadata = new HashMap<String, String>();  
		String time = getTime(incidentText);  
		incidentMetadata.put("time", time);

		String timePattern = timeToken + "?\\w+";
		// Create a Pattern object
		Pattern r = Pattern.compile(timePattern);

		// Now create matcher object.
		Matcher m = r.matcher(incidentText);
		if (m.find()) {
			incidentText = incidentText.substring(0, m.start());
		}

		String poi = getPoi(incidentText);
		incidentMetadata.put("poi", poi);

		String poiPattern = "(at|near|in)\\s\\w+";
		Pattern r2 = Pattern.compile(poiPattern);
		    
		// Now create matcher object.
		Matcher m2 = r2.matcher(incidentText);
		if (m2.find()) {
			incidentText = incidentText.substring(0, m2.start());
		}

		String type = getAssaultType(incidentText);
		incidentMetadata.put("type", type);
		return incidentMetadata;
	}
		  
	// Looking for the token "Time:"
	public static String getTime(String text) {
		String regEx = timeToken;
		String[] res = text.split(regEx);
		int size = res.length;
		if (size > 0) {
			return res[size-1].trim();
		}

		return null;
	}

	public static String getPoi(String text) {
		String regEx = " at | near | in ";
		String[] res = text.split(regEx);
		int size = res.length;
		if (size > 0) {
			 String poi = res[size-1];
			 poi = poi.replace(".", "");
			 return poi.trim();
		}

		return null;
	}

	public static String getAssaultType(String text) {
		String regEx = " get | got | was ";
		String[] res = text.split(regEx);
		int size = res.length;
		if (size > 0) {
			String type = res[size-1];
			type = type.replace(".", "");
			return type.trim();
		}

		return null;
	}
}