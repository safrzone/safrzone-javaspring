package hello.Incident;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = {"/incident"}, method= RequestMethod.POST)
	public Incident createIncident(@RequestParam String assaultType,
	                               @RequestParam Date date,
	                               @RequestParam Location location){
		Incident incident = new Incident(counter.incrementAndGet(),assaultType, date, location );
		return incident;
	}

	@RequestMapping(value = "/getIncidents/", method=RequestMethod.GET, params = { "longitude",
			"latitude", "radius", "timeinterval"})
	public List<Incident> getIncidents(@RequestParam String longitude,
	                                   @RequestParam String latitude,
	                                   @RequestParam long radius,
	                                   @RequestParam int timeinterval) {
		Incident incident1 = new Incident(counter.incrementAndGet(),"Hima", new Date(), new Location());
		Incident incident2 = new Incident(counter.incrementAndGet(),"Latha", new Date(), new Location());
		List<Incident> incidents = new ArrayList<>();
		incidents.add(incident1);
		incidents.add(incident2);
		return incidents;
	}
}
