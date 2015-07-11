package hello.incident;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import lombok.Setter;

public class IncidentDaoImpl implements IncidentDao {
	@Setter
	private SessionFactory sessionFactory;

	@Override
	public void save(Incident incident) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(incident);
		tx.commit();
		session.close();
	}

	@Override
	public Incident findByIncidentId(long incidentId) {
		Session session = this.sessionFactory.openSession();
		Incident incident = (Incident) session.createQuery("from incident in where in.id = :id")
				.setParameter("id", incidentId).list().get(0);
		return incident;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Incident> findIncidents(String latitude, String longitude) {
		Session session = this.sessionFactory.openSession();
		List<Incident> incidentList = session.createQuery("from Incident").list();
		session.close();
		return incidentList;
	}
}
