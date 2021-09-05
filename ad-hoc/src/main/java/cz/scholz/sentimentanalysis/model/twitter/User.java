package cz.scholz.sentimentanalysis.model.twitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public RateLimitStatus rateLimitStatus;
    public int accessLevel = 0;
    public long id;
    public String name;
    public String email;
    public String screenName;
    public String location;
    public String description;
    public URLEntity[] descriptionURLEntities;
    public URLEntity urlEntity;
    public boolean contributorsEnabled;
    public String profileImageURL;
    public String profileImageURLHttps;
    public boolean defaultProfileImage;
    public String url;
    @JsonProperty("protected")
    public boolean isProtected;
    public int followersCount;
    public Tweet status;
    public String profileBackgroundColor;
    public String profileTextColor;
    public String profileLinkColor;
    public String profileSidebarFillColor;
    public String profileSidebarBorderColor;
    public boolean profileUseBackgroundImage;
    public boolean defaultProfile;
    public boolean showAllInlineMedia;
    public int friendsCount;
    public Date createdAt;
    public int favouritesCount;
    public int utcOffset;
    public String timeZone;
    public String profileBackgroundImageURL;
    public String profileBackgroundImageUrlHttps;
    public String profileBannerImageURL;
    public boolean profileBackgroundTiled;
    public String lang;
    public int statusesCount;
    public boolean geoEnabled;
    public boolean verified;
    public boolean translator;
    public int listedCount;
    public boolean followRequestSent;
    public String[] withheldInCountries;

    public User() {
    }

    public User(RateLimitStatus rateLimitStatus, int accessLevel, long id, String name, String email, String screenName, String location, String description, URLEntity[] descriptionURLEntities, URLEntity urlEntity, boolean contributorsEnabled, String profileImageURL, String profileImageURLHttps, boolean defaultProfileImage, String url, boolean isProtected, int followersCount, Tweet status, String profileBackgroundColor, String profileTextColor, String profileLinkColor, String profileSidebarFillColor, String profileSidebarBorderColor, boolean profileUseBackgroundImage, boolean defaultProfile, boolean showAllInlineMedia, int friendsCount, Date createdAt, int favouritesCount, int utcOffset, String timeZone, String profileBackgroundImageURL, String profileBackgroundImageUrlHttps, String profileBannerImageURL, boolean profileBackgroundTiled, String lang, int statusesCount, boolean geoEnabled, boolean verified, boolean translator, int listedCount, boolean followRequestSent, String[] withheldInCountries) {
        this.rateLimitStatus = rateLimitStatus;
        this.accessLevel = accessLevel;
        this.id = id;
        this.name = name;
        this.email = email;
        this.screenName = screenName;
        this.location = location;
        this.description = description;
        this.descriptionURLEntities = descriptionURLEntities;
        this.urlEntity = urlEntity;
        this.contributorsEnabled = contributorsEnabled;
        this.profileImageURL = profileImageURL;
        this.profileImageURLHttps = profileImageURLHttps;
        this.defaultProfileImage = defaultProfileImage;
        this.url = url;
        this.isProtected = isProtected;
        this.followersCount = followersCount;
        this.status = status;
        this.profileBackgroundColor = profileBackgroundColor;
        this.profileTextColor = profileTextColor;
        this.profileLinkColor = profileLinkColor;
        this.profileSidebarFillColor = profileSidebarFillColor;
        this.profileSidebarBorderColor = profileSidebarBorderColor;
        this.profileUseBackgroundImage = profileUseBackgroundImage;
        this.defaultProfile = defaultProfile;
        this.showAllInlineMedia = showAllInlineMedia;
        this.friendsCount = friendsCount;
        this.createdAt = createdAt;
        this.favouritesCount = favouritesCount;
        this.utcOffset = utcOffset;
        this.timeZone = timeZone;
        this.profileBackgroundImageURL = profileBackgroundImageURL;
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
        this.profileBannerImageURL = profileBannerImageURL;
        this.profileBackgroundTiled = profileBackgroundTiled;
        this.lang = lang;
        this.statusesCount = statusesCount;
        this.geoEnabled = geoEnabled;
        this.verified = verified;
        this.translator = translator;
        this.listedCount = listedCount;
        this.followRequestSent = followRequestSent;
        this.withheldInCountries = withheldInCountries;
    }

    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public URLEntity[] getDescriptionURLEntities() {
        return descriptionURLEntities;
    }

    public URLEntity getUrlEntity() {
        return urlEntity;
    }

    public boolean isContributorsEnabled() {
        return contributorsEnabled;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public String getProfileImageURLHttps() {
        return profileImageURLHttps;
    }

    public boolean isDefaultProfileImage() {
        return defaultProfileImage;
    }

    public String getUrl() {
        return url;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public Tweet getStatus() {
        return status;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public String getProfileTextColor() {
        return profileTextColor;
    }

    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    public boolean isProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    public boolean isDefaultProfile() {
        return defaultProfile;
    }

    public boolean isShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getProfileBackgroundImageURL() {
        return profileBackgroundImageURL;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    public String getProfileBannerImageURL() {
        return profileBannerImageURL;
    }

    public boolean isProfileBackgroundTiled() {
        return profileBackgroundTiled;
    }

    public String getLang() {
        return lang;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public boolean isGeoEnabled() {
        return geoEnabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isTranslator() {
        return translator;
    }

    public int getListedCount() {
        return listedCount;
    }

    public boolean isFollowRequestSent() {
        return followRequestSent;
    }

    public String[] getWithheldInCountries() {
        return withheldInCountries;
    }
}
