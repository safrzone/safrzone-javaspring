package hello;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import hello.incident.Incident;
import hello.incident.IncidentDao;
import hello.incident.Location;

public class SpringHibernateMain {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

		IncidentDao incidentDao = context.getBean(IncidentDao.class);

		Location location = new Location();
		location.setLatitude("244");
		location.setLongitude("5677");
		String landmark = "ATTPark, San Francisco";
		String imageUrl = "http://sdfs";
		String src = "twitter";
		Incident incident = new Incident("rape", new Date(), location, landmark, imageUrl, src);

		incidentDao.save(incident);

		System.out.println("Incident::"+incident);

		List<Incident> incidents = incidentDao.findIncidentsAroundLocation("343", "2424",
				"2342", 13);

		incidents.forEach(incidentInfo -> System.out.println(incidentInfo.getId()));

		context.close();
	}
}
