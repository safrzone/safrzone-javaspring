package hello.incident;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Incident implements Serializable {
	private static final long serialVersionUID = -7118895006229283852L;

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Getter @Setter
	private String incidentType;

	@Getter @Setter
	private Date date;

	@Getter @Setter
	@OneToOne
	@JoinColumn(name="locationId")
	@Cascade(CascadeType.ALL)
	private Location location;

	public Incident(String incidentType, Date date, Location location) {
		this.incidentType = incidentType;
		this.date = date;
		this.location = location;
	}

	public Incident(){}
}
