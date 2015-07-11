package hello.incident;

import java.util.List;

public interface IncidentDao {
	public void save(Incident incident);
	public Incident findByIncidentId(long incidentId);
	public List<Incident> findIncidents(String latitude, String longitude);
}
