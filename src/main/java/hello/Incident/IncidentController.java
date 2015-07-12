package hello.incident;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.TwitterClient;
import hello.parser.Tokenizer;
import hello.parser.Tokenizer.InputFields;
import twitter4j.TwitterException;

@RestController
public class IncidentController {
	@Autowired private IncidentDao incidentDao;
	@Autowired private Tokenizer tokenizer;

	private final AtomicLong counter = new AtomicLong();
	final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	final String FACEBOOK="yyyy-MM-dd'T'HH:mm:ss+SSSS";

	@RequestMapping(value = {"/incident"}, method= RequestMethod.POST)
	public Incident createIncident(@RequestParam(required=false) String incidentDetail,
			                       @RequestParam(required=false) String incidentType,
	                               @RequestParam(required=false) String date,
	                               Location location,
	                               @RequestParam(required = false) String landmark,
	                               @RequestParam(required = false) String imageUrl,
			                       @RequestParam(required = false) String src,
			                       @RequestParam(required = false) Long tweetId,
			                       @RequestParam(required = false) String userName) {
		Map<InputFields, String> incidentInfo;
		String dateInText = null;
		if(incidentDetail!=null) {
			incidentInfo = tokenizer.tokenizeIncident(incidentDetail);
			incidentType = incidentInfo.get(InputFields.TYPE);
			dateInText = incidentInfo.get(InputFields.TIME);
			landmark = incidentInfo.get(InputFields.POI);
		}

		Date javaDate;
		if(dateInText == null) {
			if (getJavaDateForTwitterFormat(date).isPresent()) {
				javaDate = getJavaDateForTwitterFormat(date).get();
			} else if (getJavaDateForFacebookFormat(date).isPresent()) {
				javaDate = getJavaDateForFacebookFormat(date).get();
			} else {
				javaDate = new Date(date);
			}
		} else {
			javaDate = new Date(dateInText);
		}
		System.out.println(javaDate.toString());
		Incident incident = new Incident(incidentType, javaDate,
				location, landmark, imageUrl, src);
		incidentDao.save(incident);

		TwitterClient twitterClient = new TwitterClient();
		try {
			if(src.equals("twitter")) {
				twitterClient.updateStatus(userName, landmark, tweetId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return incident;
	}

	@RequestMapping(value = "/getIncidents/", method=RequestMethod.GET)
	public List<Incident> getIncidents(@RequestParam(required = false) String longitude,
	                                   @RequestParam(required = false) String latitude,
	                                   @RequestParam String radius,
	                                   @RequestParam int timeinterval,
	                                   @RequestParam(required = false) String landmark) throws Exception {
		List<Incident> incidents = new ArrayList<>();
		if(latitude !=null && longitude !=null) {
			incidents = incidentDao.findIncidentsAroundLocation(latitude,
					longitude, radius, timeinterval);
		} else if(landmark != null) {
			incidents = incidentDao.findIncidentsAroundLandmark(landmark, radius, timeinterval);
		}
		return incidents;
	}

	@RequestMapping({"/incident/{id}"})
	public Incident viewIncident(@PathVariable long id) {
		Incident incident = incidentDao.findByIncidentId(id);
		return incident;
	}

	private Optional<Date> getJavaDateForTwitterFormat(String date) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
			sf.setLenient(true);
			return Optional.of(sf.parse(date));
		} catch(ParseException e) {
			//Dont do anything
			return Optional.empty();
		}
	}

	private Optional<Date> getJavaDateForFacebookFormat(String date) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(FACEBOOK);
			sf.setLenient(true);
			return Optional.of(sf.parse(date));
		} catch(ParseException e) {
			//Dont do anything
			return Optional.empty();
		}
	}
}
