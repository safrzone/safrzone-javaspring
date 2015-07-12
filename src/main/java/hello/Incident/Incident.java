package hello.incident;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

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

	@Getter @Setter
	private String landmark;

	@Getter @Setter
	private String imageUrl;

	@Getter @Setter
	private String src;

	public Incident(String incidentType, Date date, Location location, String landmark,
	                String imageUrl, String src) {
		this.incidentType = incidentType;
		this.src = src;
		this.date = date;
		if(imageUrl!=null) {
			this.imageUrl = imageUrl;
		} else {
			try {
				String basePath = new File("").getAbsolutePath();
				System.out.println(basePath);
				this.imageUrl = choose(new File("src/main/resources/imageUrls.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(landmark!=null) {
			this.landmark = landmark;
			GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAo-mr0uF06CbnYKPZCgCjG27EYvju7ffw");
			try {
				GeocodingResult[] results = GeocodingApi.geocode(context,
						landmark).await();
				System.out.println(results[0].formattedAddress);
				String latitude = String.valueOf(results[0].geometry.location.lat);
				String longitude = String.valueOf(results[0].geometry.location.lng);
				System.out.println(latitude);
				System.out.println(longitude);
				Location location1 = new Location();
				location1.setLatitude(latitude);
				location1.setLongitude(longitude);
				this.location = location1;
			} catch (Exception e) {
				this.location = null;
			}
		} else {
			this.location = location;
		}
	}

	public static String choose(File f) throws FileNotFoundException
	{
		String result = null;
		Random rand = new Random();
		int n = 0;
		for(Scanner sc = new Scanner(f); sc.hasNext(); )
		{
			++n;
			String line = sc.nextLine();
			if(rand.nextInt(n) == 0)
				result = line;
		}

		return result;
	}

	public Incident(){}
}
