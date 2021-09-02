package cz.scholz.tagcloud.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Map;

@RegisterForReflection
public class MediaEntity {
    public String text;
    public int start = -1;
    public int end = -1;
    public long id;
    public String url;
    public String mediaURL;
    public String mediaURLHttps;
    public String expandedURL;
    public String displayURL;
    public Map<Integer, Size> sizes;
    public String type;
    public int videoAspectRatioWidth;
    public int videoAspectRatioHeight;
    public long videoDurationMillis;
    public Variant[] videoVariants;
    public String extAltText;

    MediaEntity() {
    }

    public MediaEntity(String text, int start, int end, long id, String url, String mediaURL, String mediaURLHttps, String expandedURL, String displayURL, Map<Integer, Size> sizes, String type, int videoAspectRatioWidth, int videoAspectRatioHeight, long videoDurationMillis, Variant[] videoVariants, String extAltText) {
        this.text = text;
        this.start = start;
        this.end = end;
        this.id = id;
        this.url = url;
        this.mediaURL = mediaURL;
        this.mediaURLHttps = mediaURLHttps;
        this.expandedURL = expandedURL;
        this.displayURL = displayURL;
        this.sizes = sizes;
        this.type = type;
        this.videoAspectRatioWidth = videoAspectRatioWidth;
        this.videoAspectRatioHeight = videoAspectRatioHeight;
        this.videoDurationMillis = videoDurationMillis;
        this.videoVariants = videoVariants;
        this.extAltText = extAltText;
    }

    public String getText() {
        return text;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public String getMediaURLHttps() {
        return mediaURLHttps;
    }

    public String getExpandedURL() {
        return expandedURL;
    }

    public String getDisplayURL() {
        return displayURL;
    }

    public Map<Integer, Size> getSizes() {
        return sizes;
    }

    public String getType() {
        return type;
    }

    public int getVideoAspectRatioWidth() {
        return videoAspectRatioWidth;
    }

    public int getVideoAspectRatioHeight() {
        return videoAspectRatioHeight;
    }

    public long getVideoDurationMillis() {
        return videoDurationMillis;
    }

    public Variant[] getVideoVariants() {
        return videoVariants;
    }

    public String getExtAltText() {
        return extAltText;
    }

    @RegisterForReflection
    public static class Variant {
        public int bitrate;
        public String contentType;
        public String url;

        Variant() {
        }

        public Variant(int bitrate, String contentType, String url) {
            this.bitrate = bitrate;
            this.contentType = contentType;
            this.url = url;
        }

        public int getBitrate() {
            return bitrate;
        }

        public String getContentType() {
            return contentType;
        }

        public String getUrl() {
            return url;
        }
    }

    @RegisterForReflection
    public static class Size {
        int width;
        int height;
        int resize;

        Size() {
        }

        public Size(int width, int height, int resize) {
            this.width = width;
            this.height = height;
            this.resize = resize;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getResize() {
            return resize;
        }
    }
}
