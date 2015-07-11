package hello.incident;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {
	@Autowired private IncidentDao incidentDao;

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = {"/incident"}, method= RequestMethod.POST)
	public Incident createIncident(@RequestParam String incidentType,
	                               @RequestParam Date date,
	                               Location location){
		Incident incident = new Incident(incidentType, date, location );
		incidentDao.save(incident);
		return incident;
	}

	@RequestMapping(value = "/getIncidents/", method=RequestMethod.GET, params = { "longitude",
			"latitude", "radius", "timeinterval"})
	public List<Incident> getIncidents(@RequestParam String longitude,
	                                   @RequestParam String latitude,
	                                   @RequestParam long radius,
	                                   @RequestParam int timeinterval) {
		List<Incident> incidents = incidentDao.findIncidents(latitude, longitude);
		return incidents;
	}
}
