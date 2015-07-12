package hello.parser;

import java.util.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class Tokenizer {
	/* Example tokens to tokenize:
	I saw a guy get mugged near Civic Center Bart Station. Date:<time>
	I got attacked by a guy in Tenderloin. Date:<time>
	My friend was sexually assaulted at the Hogwash College Campus. Date:<time>

	Incident Type: followed by "get", "got", "was"
	Landmark: followed by "at", "near", "in"
	Date: followed by "Date:"
	*/
	public static String timeToken = "Date:";


	public static enum InputFields {
		TYPE,
		POI,
		TIME
	}

	public static Map<InputFields, String> tokenizeIncident(String incidentText) {
		// Keys: type, poi, time
		Map<InputFields, String> incidentMetadata = new HashMap();
		String time = getTime(incidentText);
		System.out.println("In tokenizer");
		System.out.println(format("time is %s", time));
		incidentMetadata.put(InputFields.TIME, time);

		String timePattern = timeToken + "?\\w+";
		// Create a Pattern object
		Pattern r = Pattern.compile(timePattern);

		// Now create matcher object.
		Matcher m = r.matcher(incidentText);
		if (m.find()) {
			incidentText = incidentText.substring(0, m.start());
		}

		String poi = getPoi(incidentText);
		System.out.println(format("poi is %s", poi));
		incidentMetadata.put(InputFields.POI, poi);

		String poiPattern = "(at|near|in)\\s\\w+";
		Pattern r2 = Pattern.compile(poiPattern);
		    
		// Now create matcher object.
		Matcher m2 = r2.matcher(incidentText);
		if (m2.find()) {
			incidentText = incidentText.substring(0, m2.start());
		}

		String type = getAssaultType(incidentText);
		System.out.println(format("type is %s", type));
		incidentMetadata.put(InputFields.TYPE, type);
		return incidentMetadata;
	}
		  
	// Looking for the token "Time:"
	public static String getTime(String text) {
		String regEx = timeToken;
		String[] res = text.split(regEx);
		int size = res.length;
		if (size > 1) {
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

	public static void main(String[] args) {
		String text = "My friend was sexually assaulted at the Civic Center Bart Station";
		Tokenizer tokenizer = new Tokenizer();
	}
}