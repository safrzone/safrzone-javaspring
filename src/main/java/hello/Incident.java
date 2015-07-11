package hello;

import java.util.Date;

public class Incident {
	private long id;
	private String incidentType;
	private Date date;
	private Location location;

	public Incident(long id, String incidentType, Date date, Location location) {
		this.id = id;
		this.incidentType = incidentType;
		this.date = date;
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
