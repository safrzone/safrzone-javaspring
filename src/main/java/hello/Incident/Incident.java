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
import static java.lang.String.format;

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
				String[] imageUrls = new String[]
						{"http://blog.datingwise.com/wp-content/uploads/2010/04/man-woman-300x211.jpg",
						"http://farm4.staticflickr.com/3332/3493810475_e76a89e154_z.jpg",
						"http://www.bromsgroveglazingrepairs.co.uk/wp-content/uploads/2012/06/BromsgroveShopFrontWindowBroken.jpg",
						"http://www.cochranfirmdc.com/wp-content/uploads/2013/12/apple-store-bethesda-glass-door-broken-pelvis-lawyer.jpg",
						"http://www.laval-ambulances.fr/img/ambulances.jpg",
						"http://farm4.staticflickr.com/3696/9355136130_d8fd538c09_z.jpg",
								"http://www.brooklyneagle.com/sites/default/files/styles/free_style/public/b_LICH_ambulance2_MFrost_6-23-13.jpg?c=806533261199a2bd06a2cd7c81d38c9f"};
				this.imageUrl = imageUrls[new Random().nextInt(imageUrls.length)];
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
