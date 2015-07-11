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
		location.setLatitude("3242");
		location.setLongitude("23423");
		Incident incident = new Incident("rape", new Date(), location);

		incidentDao.save(incident);

		System.out.println("Incident::"+incident);

		List<Incident> incidents = incidentDao.findIncidents("343", "2424");

		incidents.forEach(incidentInfo -> System.out.println(incident.getId()));

		context.close();
	}
}
