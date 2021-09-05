package cz.scholz.sentimentanalysis.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class URLEntity {
    public String text;
    public String url;
    public String expandedURL;
    public String displayURL;
    public int start = -1;
    public int end = -1;

    URLEntity() {
    }

    public URLEntity(String text, String url, String expandedURL, String displayURL, int start, int end) {
        this.text = text;
        this.url = url;
        this.expandedURL = expandedURL;
        this.displayURL = displayURL;
        this.start = start;
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getExpandedURL() {
        return expandedURL;
    }

    public String getDisplayURL() {
        return displayURL;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
