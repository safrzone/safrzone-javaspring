package hello.incident;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Location implements Serializable {
	private static final long serialVersionUID = -7113435006229283852L;

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Getter @Setter
	protected String latitude;

	@Getter @Setter
	protected String longitude;
}
