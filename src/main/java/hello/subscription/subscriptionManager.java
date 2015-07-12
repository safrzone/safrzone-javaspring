package hello.subscription;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import hello.incident.Incident;
import lombok.Getter;
import lombok.Setter;

@Component
public class SubscriptionManager {

	@Getter @Setter
	private SimpleMailMessage templateMessage;

	public void sendEmail(String emailAddress, String firstName, String lastName) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setPort(587);
		javaMailSender.setUsername("safrzone@gmail.com");
		javaMailSender.setPassword("chimehack");
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		javaMailSender.setJavaMailProperties(properties);
		this.templateMessage.setSubject("Report from SafrZone");
		this.templateMessage.setFrom("safrzone@gmail.com");
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(emailAddress);
		msg.setText("Dear "+firstName+ " "+ lastName);
		msg.setSubject("Report from SafrZone");
		try{
			javaMailSender.send(msg);
		}
		catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
	}

	List<Incident> getIncidents(String landmark, double radius) {
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
