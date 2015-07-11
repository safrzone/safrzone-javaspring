package hello.incident;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class LocationDaoImpl implements LocationDao {
	@Autowired private EntityManager entityManager;

	@Override
	public void save(Location location) {
		entityManager.persist(location);
		return;
	}

	@Override
	public Location findByLocationId(long locationId) {
		Location location = (Location) entityManager.createQuery("from location in where in.id = :id")
				.setParameter("id", locationId).getResultList().get(0);
		return location;
	}
}
