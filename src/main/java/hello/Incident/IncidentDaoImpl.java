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
		Incident incident = (Incident) entityManager.createQuery("from incident in where in.id = :id")
				.setParameter("id", incidentId).getResultList().get(0);
		return incident;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Incident> findIncidents(String latitude, String longitude) {
		List<Incident> incidentList = entityManager.createQuery("from Incident").getResultList();
		return incidentList;
	}
}
