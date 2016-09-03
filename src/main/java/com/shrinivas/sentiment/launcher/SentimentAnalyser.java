package com.shrinivas.sentiment.launcher;

import java.io.IOException;
import java.util.List;

import com.shrinivas.sentiment.twitter.TwitterManager;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.utils.IndicoException;

public class SentimentAnalyser {

    private Indico indico;
    private TwitterManager manager;

    private SentimentAnalyser() throws IndicoException {
        indico = new Indico("f826de06ef66138f43552602b7b7f78d");
        manager = new TwitterManager(50);
    }

    public static void main(String[] args) throws IndicoException, IOException, InterruptedException {
        SentimentAnalyser launcher = new SentimentAnalyser();
        for (String topic : args) {
            double sentimentScore = launcher.getSentiments(topic);
            System.out.println(topic + " - " + sentimentScore);
        }
    }

    private double getSentiments(String text) throws IOException, IndicoException, InterruptedException {
        List<String> tweets = manager.performQuery(text);
        // for (String tweet : tweets) {
        // System.out.println(tweet);
        // }
        BatchIndicoResult result = indico.sentimentHQ.predict(tweets);
        List<Double> sentiments = result.getSentimentHQ();
        double average = 0;
        for (Double sentiment : sentiments) {
            // System.out.println(sentiment);
            average += sentiment;
        }
        average = average / sentiments.size();
        return average;
    }
}
