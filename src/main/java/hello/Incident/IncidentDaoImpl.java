package hello.incident;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

@Repository
@Transactional
public class IncidentDaoImpl implements IncidentDao {
	@PersistenceContext private EntityManager entityManager;

	@Override
	public void save(Incident incident) {
		entityManager.persist(incident);
		return;
	}

	@Override
	public Incident findByIncidentId(long incidentId) {
		Incident incident = (Incident) entityManager.createQuery("from Incident where id = :incidentId")
				.setParameter("incidentId", incidentId).getResultList().get(0);
		return incident;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Incident> findIncidentsAroundLocation(String latitude, String longitude,
	                                                  String radius,
	                                                  int timeInterval) {
		List<Incident> incidentList = entityManager.createQuery("from Incident").getResultList();
		return incidentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Incident> findIncidentsAroundLandmark(String landmark, String radius,
	                                                  int timeInterval) throws Exception {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAo-mr0uF06CbnYKPZCgCjG27EYvju7ffw");
		GeocodingResult[] results =  GeocodingApi.geocode(context,
				landmark).await();
		System.out.println(results[0].formattedAddress);
		String latitude = String.valueOf(results[0].geometry.location.lat);
		String longitude = String.valueOf(results[0].geometry.location.lng);
		System.out.println(latitude);
		System.out.println(longitude);

		return findIncidentsAroundLocation(latitude, longitude, radius, timeInterval);
	}

	@Override
	public List<Incident> findIncidentsForSubscription(String latitude, String longitude,
	                                                   String radius, Date startDate, Date endDate) {
		List<Incident> incidentList = entityManager.createQuery("from Incident where ").getResultList();
		return incidentList;
	}
}
