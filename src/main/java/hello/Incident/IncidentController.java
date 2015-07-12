package hello.incident;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {
	@Autowired private IncidentDao incidentDao;

	private final AtomicLong counter = new AtomicLong();
	final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	final String FACEBOOK="yyyy-MM-dd'T'HH:mm:ss+SSSS";

	@RequestMapping(value = {"/incident"}, method= RequestMethod.POST)
	public Incident createIncident(@RequestParam String incidentType,
	                               @RequestParam String date,
	                               Location location,
	                               @RequestParam(required = false) String landmark,
	                               @RequestParam(required = false) String imageUrl) {
		Date javaDate;
		if(getJavaDateForTwitterFormat(date).isPresent()) {
			javaDate = getJavaDateForTwitterFormat(date).get();
		} else if(getJavaDateForFacebookFormat(date).isPresent()) {
			javaDate = getJavaDateForFacebookFormat(date).get();
		} else {
			javaDate = new Date(date);
		}
		System.out.println(javaDate.toString());
		Incident incident = new Incident(incidentType, javaDate,
				location, landmark, imageUrl);
		incidentDao.save(incident);
		return incident;
	}

	@RequestMapping(value = "/getIncidents/", method=RequestMethod.GET, params = { "longitude",
			"latitude", "radius", "timeinterval"})
	public List<Incident> getIncidents(@RequestParam String longitude,
	                                   @RequestParam String latitude,
	                                   @RequestParam String radius,
	                                   @RequestParam int timeinterval) {
		List<Incident> incidents = incidentDao.findIncidentsAroundLocation(latitude,
				longitude, radius, timeinterval);
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
