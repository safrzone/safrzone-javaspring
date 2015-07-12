package hello;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import static java.lang.String.format;

public class TwitterClient {

	public void updateStatus(String username, String landmark, long inReplyToStatusId) throws TwitterException {
		TwitterFactory factory = new TwitterFactory();

		AccessToken accessToken = loadAccessToken();
		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer("vNVL5I8vAGKCkZ90VRTxiFicb", "PWLRRCt9r4DJ8zG9VIqMwk4KAMKBlfF13hTu2vxd3u5NF4HWzc");
		twitter.setOAuthAccessToken(accessToken);

		String replyMessage = String.format("@%s #%s - help make our neighborhood a safrzone",
				username, landmark.replace(" ", ""));

		StatusUpdate stat= new StatusUpdate(replyMessage);
		stat.setInReplyToStatusId(inReplyToStatusId);

		Status status = twitter.updateStatus(stat);
		System.out.println("Successfully updated the status to [" + status.getText() + "].");
	}

	private static AccessToken loadAccessToken(){
		String token = "3273895747-quPjMkHn28RyR3iuHb0K0u8IbDCpZr5cx3E04MM";
		String tokenSecret = "wTMhAxDZFqtUWTSbIJQ7Q3ig5hXPd354q9Z0hEiFlGoVz";
		return new AccessToken(token, tokenSecret);
	}
}