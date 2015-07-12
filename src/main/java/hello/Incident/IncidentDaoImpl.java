package hello.incident;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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
	                                                  int timeInterval) {
		List<Incident> incidentList = entityManager.createQuery("from Incident where landmark = :landmark")
				.setParameter("landmark", landmark)
				.getResultList();
		return incidentList;
	}
}
