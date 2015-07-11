package hello.incident;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import lombok.Setter;

public class LocationDaoImpl implements LocationDao {
	@Setter
	private SessionFactory sessionFactory;

	@Override
	public void save(Location location) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(location);
		tx.commit();
		session.close();
	}

	@Override
	public Location findByLocationId(long locationId) {
		Session session = this.sessionFactory.openSession();
		Location location = (Location) session.createQuery("from location in where in.id = :id")
				.setParameter("id", locationId).list().get(0);
		return location;
	}
}
