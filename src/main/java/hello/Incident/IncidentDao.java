package hello.incident;

import java.util.Date;
import java.util.List;

public interface IncidentDao {
	public void save(Incident incident);
	public Incident findByIncidentId(long incidentId);
	public List<Incident> findIncidentsAroundLocation(String latitude,
	                                                  String longitude,
	                                                  String radius,
	                                                  int timeInterval);
	public List<Incident> findIncidentsAroundLandmark(String landmark, String radius,
	                                                  int timeInterval) throws Exception;
	public List<Incident> findIncidentsForSubscription(String latitude,
	                                                   String longitude,
	                                                   String radius,
	                                                   Date startDate,
	                                                   Date endDate);
}
