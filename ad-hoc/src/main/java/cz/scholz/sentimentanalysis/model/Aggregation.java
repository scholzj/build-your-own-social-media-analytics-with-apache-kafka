package cz.scholz.sentimentanalysis.model;

import cz.scholz.sentimentanalysis.model.twitter.Tweet;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Aggregation {
    public int countWithMedia = 0;
    public int countWithoutMedia = 0;
    public double avgRetweetsWithMedia = 0;
    public double avgRetweetsWithoutMedia = 0;
    public double sumRetweetsWithMedia = 0;
    public double sumRetweetsWithoutMedia = 0;

    public Aggregation() {
    }

    public int getCountWithMedia() {
        return countWithMedia;
    }

    public int getCountWithoutMedia() {
        return countWithoutMedia;
    }

    public double getAvgRetweetsWithMedia() {
        return avgRetweetsWithMedia;
    }

    public double getAvgRetweetsWithoutMedia() {
        return avgRetweetsWithoutMedia;
    }

    public double getSumRetweetsWithMedia() {
        return sumRetweetsWithMedia;
    }

    public double getSumRetweetsWithoutMedia() {
        return sumRetweetsWithoutMedia;
    }

    public Aggregation aggregate(Tweet tweet) {
        if (tweet.mediaEntities != null
                && tweet.mediaEntities.length > 0)    {
            // Has media
            increaseWithMedia(tweet.getRetweetCount());
        } else {
            // Does not have media
            increaseWithoutMedia(tweet.getRetweetCount());
        }

        return this;
    }

    private void increaseWithMedia(int retweets)    {
        countWithMedia++;
        sumRetweetsWithMedia += retweets;
        avgRetweetsWithMedia = BigDecimal.valueOf(sumRetweetsWithMedia / countWithMedia).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private void increaseWithoutMedia(int retweets)    {
        countWithoutMedia++;
        sumRetweetsWithoutMedia += retweets;
        avgRetweetsWithoutMedia = BigDecimal.valueOf(sumRetweetsWithoutMedia / countWithoutMedia).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        return "We have so far:\n\t" +
                countWithMedia + " tweets with media with average " + avgRetweetsWithMedia + " retweets\n\t" +
                countWithoutMedia + " tweets without media with average " + avgRetweetsWithoutMedia + " retweets\n\t";
    }
}
