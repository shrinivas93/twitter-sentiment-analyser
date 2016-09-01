package com.shrinivas.sentiment.twitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterManager {
    int limit;
    ConfigurationBuilder cb;
    Twitter twitter;

    public TwitterManager(int limit) {
        this.limit = limit < 100 ? limit : 100;
        cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("jfvxTokruN7XWfDNwtL2w3HlX");
        cb.setOAuthConsumerSecret("XNLQIfn8srFXNa91A7k4ihbiCxRLr8QUg41JDPgtN7SjsB2Qtc");
        cb.setOAuthAccessToken("3222473696-VcLW2SvbfELI0V12n6tThbt0Cflho1CRpfVGeQ5");
        cb.setOAuthAccessTokenSecret("90sAtbJw8w3KFBdk5SLh0D89WWS1JTu3F5sgUMVgnfmKJ");
        twitter = new TwitterFactory(cb.build()).getInstance();
    }

    public List<String> performQuery(String inQuery) throws InterruptedException, IOException {
        Query query = new Query(inQuery);
        query.setCount(limit);
        List<String> tweets = new ArrayList<String>();
        try {
            int count = 0;
            QueryResult r;
            do {
                r = twitter.search(query);
                List<Status> ts = r.getTweets();
                for (int i = 0; i < ts.size() && count < limit; i++) {
                    count++;
                    Status t = ts.get(i);
                    tweets.add(t.getText());
                }
            } while ((query = r.nextQuery()) != null && count < limit);
        } catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }
        return tweets;
    }
}