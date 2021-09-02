package cz.scholz.tagcloud.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Date;

@RegisterForReflection
public class Tweet {
    public RateLimitStatus rateLimitStatus;
    public int accessLevel = 0;
    public Date createdAt;
    public long id;
    public String text;
    public int displayTextRangeStart = -1;
    public int displayTextRangeEnd = -1;
    public String source;
    public boolean truncated;
    public long inReplyToStatusId;
    public long inReplyToUserId;
    public boolean favorited;
    public boolean retweeted;
    public boolean retweet;
    public int favoriteCount;
    public String inReplyToScreenName;
    public GeoLocation geoLocation = null;
    public Place place = null;
    public long retweetCount;
    public boolean possiblySensitive;
    public String lang;
    public long[] contributors;
    public Tweet retweetedStatus;
    public UserMentionEntity[] userMentionEntities;
    public URLEntity[] urlentities;
    public HashtagEntity[] hashtagEntities;
    public MediaEntity[] mediaEntities;
    public SymbolEntity[] symbolEntities;
    public long currentUserRetweetId = -1L;
    public Scopes scopes;
    public User user = null;
    public String[] withheldInCountries = null;
    public Tweet quotedStatus;
    public long quotedStatusId = -1L;
    public URLEntity quotedStatusPermalink;
    public boolean retweetedByMe;

    public Tweet() {
    }

    public Tweet(RateLimitStatus rateLimitStatus, int accessLevel, Date createdAt, long id, String text, int displayTextRangeStart, int displayTextRangeEnd, String source, boolean truncated, long inReplyToStatusId, long inReplyToUserId, boolean favorited, boolean retweeted, boolean retweet, int favoriteCount, String inReplyToScreenName, GeoLocation geoLocation, Place place, long retweetCount, boolean possiblySensitive, String lang, long[] contributors, Tweet retweetedStatus, UserMentionEntity[] userMentionEntities, URLEntity[] urlentities, HashtagEntity[] hashtagEntities, MediaEntity[] mediaEntities, SymbolEntity[] symbolEntities, long currentUserRetweetId, Scopes scopes, User user, String[] withheldInCountries, Tweet quotedStatus, long quotedStatusId, URLEntity quotedStatusPermalink, boolean retweetedByMe) {
        this.rateLimitStatus = rateLimitStatus;
        this.accessLevel = accessLevel;
        this.createdAt = createdAt;
        this.id = id;
        this.text = text;
        this.displayTextRangeStart = displayTextRangeStart;
        this.displayTextRangeEnd = displayTextRangeEnd;
        this.source = source;
        this.truncated = truncated;
        this.inReplyToStatusId = inReplyToStatusId;
        this.inReplyToUserId = inReplyToUserId;
        this.favorited = favorited;
        this.retweeted = retweeted;
        this.retweet = retweet;
        this.favoriteCount = favoriteCount;
        this.inReplyToScreenName = inReplyToScreenName;
        this.geoLocation = geoLocation;
        this.place = place;
        this.retweetCount = retweetCount;
        this.possiblySensitive = possiblySensitive;
        this.lang = lang;
        this.contributors = contributors;
        this.retweetedStatus = retweetedStatus;
        this.userMentionEntities = userMentionEntities;
        this.urlentities = urlentities;
        this.hashtagEntities = hashtagEntities;
        this.mediaEntities = mediaEntities;
        this.symbolEntities = symbolEntities;
        this.currentUserRetweetId = currentUserRetweetId;
        this.scopes = scopes;
        this.user = user;
        this.withheldInCountries = withheldInCountries;
        this.quotedStatus = quotedStatus;
        this.quotedStatusId = quotedStatusId;
        this.quotedStatusPermalink = quotedStatusPermalink;
        this.retweetedByMe = retweetedByMe;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getDisplayTextRangeStart() {
        return displayTextRangeStart;
    }

    public int getDisplayTextRangeEnd() {
        return displayTextRangeEnd;
    }

    public String getSource() {
        return source;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public Place getPlace() {
        return place;
    }

    public int getRetweetCount() {
        return (int) retweetCount;
    }

    public boolean isPossiblySensitive() {
        return possiblySensitive;
    }

    public String getLang() {
        return lang;
    }

    public Tweet getRetweetedStatus() {
        return retweetedStatus;
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return userMentionEntities;
    }

    public HashtagEntity[] getHashtagEntities() {
        return hashtagEntities;
    }

    public MediaEntity[] getMediaEntities() {
        return mediaEntities;
    }

    public SymbolEntity[] getSymbolEntities() {
        return symbolEntities;
    }

    public long getCurrentUserRetweetId() {
        return currentUserRetweetId;
    }

    public Scopes getScopes() {
        return scopes;
    }

    public User getUser() {
        return user;
    }

    public boolean isRetweet() {
        return retweet;
    }

    public String[] getWithheldInCountries() {
        return withheldInCountries;
    }

    public Tweet getQuotedStatus() {
        return quotedStatus;
    }

    public long getQuotedStatusId() {
        return quotedStatusId;
    }

    public URLEntity getQuotedStatusPermalink() {
        return quotedStatusPermalink;
    }

    public long[] getContributors() {
        return contributors;
    }

    public boolean isRetweetedByMe() {
        return retweetedByMe;
    }

    public URLEntity[] geturlentities() {
        return urlentities;
    }

    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    public int getAccessLevel() {
        return accessLevel;
    }
}
