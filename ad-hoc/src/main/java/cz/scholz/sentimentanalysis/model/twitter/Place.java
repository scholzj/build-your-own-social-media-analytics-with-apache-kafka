package cz.scholz.sentimentanalysis.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Place {
    public RateLimitStatus rateLimitStatus;
    public int accessLevel = 0;
    public String name;
    public String streetAddress;
    public String countryCode;
    public String id;
    public String country;
    public String placeType;
    public String url;
    public String fullName;
    public String boundingBoxType;
    public GeoLocation[][] boundingBoxCoordinates;
    public String geometryType;
    public GeoLocation[][] geometryCoordinates;
    public Place[] containedWithIn;

    public Place() {
    }

    public Place(RateLimitStatus rateLimitStatus, int accessLevel, String name, String streetAddress, String countryCode, String id, String country, String placeType, String url, String fullName, String boundingBoxType, GeoLocation[][] boundingBoxCoordinates, String geometryType, GeoLocation[][] geometryCoordinates, Place[] containedWithIn) {
        this.rateLimitStatus = rateLimitStatus;
        this.accessLevel = accessLevel;
        this.name = name;
        this.streetAddress = streetAddress;
        this.countryCode = countryCode;
        this.id = id;
        this.country = country;
        this.placeType = placeType;
        this.url = url;
        this.fullName = fullName;
        this.boundingBoxType = boundingBoxType;
        this.boundingBoxCoordinates = boundingBoxCoordinates;
        this.geometryType = geometryType;
        this.geometryCoordinates = geometryCoordinates;
        this.containedWithIn = containedWithIn;
    }

    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getPlaceType() {
        return placeType;
    }

    public String getUrl() {
        return url;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBoundingBoxType() {
        return boundingBoxType;
    }

    public GeoLocation[][] getBoundingBoxCoordinates() {
        return boundingBoxCoordinates;
    }

    public String getGeometryType() {
        return geometryType;
    }

    public GeoLocation[][] getGeometryCoordinates() {
        return geometryCoordinates;
    }

    public Place[] getContainedWithIn() {
        return containedWithIn;
    }
}
