package hello.subscription;

import java.util.Date;
import java.util.List;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import hello.incident.Incident;

public class subscriptionManager {
	List<Incident> subscribe(String landmark, double radius,
	                         Date startDate, Date endDate) {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAo-mr0uF06CbnYKPZCgCjG27EYvju7ffw");
		try {
			GeocodingResult[] results = GeocodingApi.geocode(context,
					landmark).await();
			LatLng latLng = results[0].geometry.location;
			double leftLat = latLng.lat - radius;
			double rightLat = latLng.lat + radius;
			double topLng = latLng.lng + radius;
			double bottomLng = latLng.lng - radius;

		} catch(Exception e) {

		}
		return null;
	}

}
