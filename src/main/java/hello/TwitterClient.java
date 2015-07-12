package hello;

public class TwitterClient {

	public void updateStatus(String landmark, int convoIdToReplyTo) {
		TwitterFactory factory = new TwitterFactory();
		
	    // AccessToken accessToken = loadAccessToken(Integer.parseInt(args[0]));
	    Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumerKey("vNVL5I8vAGKCkZ90VRTxiFicb", "PWLRRCt9r4DJ8zG9VIqMwk4KAMKBlfF13hTu2vxd3u5NF4HWzc");
	    twitter.setOAuthAccessToken("3273895747-quPjMkHn28RyR3iuHb0K0u8IbDCpZr5cx3E04MM");

	    String replyMessage = String.format("#%s - help make our neighborhood a safrzone", landmark.replace(" ", ""));
	    Status status = twitter.updateStatus(replyMessage);
	    System.out.println("Successfully updated the status to [" + status.getText() + "].");
	}
}