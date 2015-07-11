package hello;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = {"/incident"}, method= RequestMethod.POST)
	public Incident createIncident(@RequestParam String assaultType){
		Incident incident = new Incident(counter.incrementAndGet(),assaultType, new Date(), new Location() );
		return incident;
	}

}
