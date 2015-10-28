package de.coins2015.oscar1.twittercrawler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterCrawler {

    private final String CONSUMER_KEY = "2z7BAbcq51gEpkk0qRGlbKGhi";
    private final String CONSUMER_SECRET = "8PGAiV7XyUHx6A9RImDFkbeUxHvQGqlt3I9skxOGhkDt63JGfe";
    private final String ACCESS_TOKEN = "1491313489-H6NQzgaMkS3q5d7Tq0GEL90afhRl7hKbWhhGsCG";
    private final String ACCESS_TOKEN_SECRET = "Ltpfiy8ya1q31KsrxMh97UQqEiVIGGU7wxW8duEnPyoAo";

    private final int TWEETS_PER_QUERY = 100;

    private final int MAX_QUERIES = 90;

    private MongoDBHandler mdbh;

    public TwitterCrawler(MongoDBHandler mdbh) {
	this.mdbh = mdbh;
    }

    private TwitterStream getTwitterStream() {

	ConfigurationBuilder cb = new ConfigurationBuilder();
	cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
		.setOAuthConsumerSecret(CONSUMER_SECRET)
		.setOAuthAccessToken(ACCESS_TOKEN)
		.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

	TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
		.getInstance();
	return twitterStream;

    }

    public void retrieve(List<String> searchItems) {

	TwitterStream twitterStream = getTwitterStream();

	StatusListener listener = new StatusListener() {

	    private List<JSONObject> buffer = new ArrayList<JSONObject>();

	    @Override
	    public void onStatus(Status status) {

		long tweetId = status.getId();
		String timestamp = new Timestamp(new Date().getTime())
			.toString();
		String username = status.getUser().getScreenName();
		String profileLocation = status.getUser().getLocation();
		int favoritecount = status.getFavoriteCount();
		int rtcount = status.getRetweetCount();
		String content = status.getText();

		JSONObject tweet = new JSONObject();
		tweet.put("id", tweetId);
		tweet.put("timestamp", timestamp);
		tweet.put("username", username);
		tweet.put("location", profileLocation);
		tweet.put("favoritecount", favoritecount);
		tweet.put("retweetcount", rtcount);
		tweet.put("content", content);

		buffer.add(tweet);

		System.out.println("buffersize: " + buffer.size());

		if (buffer.size() > 1000) {
		    flushBuffer();
		}

	    }

	    private void flushBuffer() {
		System.out.println("flushing...");
		for (JSONObject tweet : buffer) {
		    mdbh.storeData(tweet, "twitter");
		}
		buffer.clear();
		System.out.println("flushed, buffer cleared");
	    }

	    @Override
	    public void onDeletionNotice(
		    StatusDeletionNotice statusDeletionNotice) {
	    }

	    @Override
	    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	    }

	    @Override
	    public void onException(Exception ex) {
		ex.printStackTrace();
	    }

	    @Override
	    public void onScrubGeo(long arg0, long arg1) {

	    }

	    @Override
	    public void onStallWarning(StallWarning arg0) {

	    }
	};

	FilterQuery fq = new FilterQuery();
	String keywords[] = searchItems.toArray(new String[0]);
	fq.language("en");
	fq.track(keywords);

	twitterStream.addListener(listener);
	twitterStream.filter(fq);
	boolean test = true;
    }

}
