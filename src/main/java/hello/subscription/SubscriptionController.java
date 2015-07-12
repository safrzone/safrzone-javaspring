package hello.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.incident.Incident;

@RestController
public class SubscriptionController {
	@Autowired SubscriptionManager subscriptionManager;

	@RequestMapping(value = {"/subscribe"}, method= RequestMethod.POST)
	public void subscribe(@RequestParam(required=true) String emailAddress,
	                      @RequestParam(required=false) String firstName,
	                      @RequestParam(required=false) String lastName,
	                      @RequestParam(required=true) String landmark,
	                      @RequestParam(required=false) String radius){
		List<Incident> incidents = subscriptionManager.getIncidents(landmark, Double.parseDouble(radius));

		subscriptionManager.sendEmail(emailAddress,firstName, lastName);
	}
}
