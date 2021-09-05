package cz.scholz.sentimentanalysis.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UserMentionEntity {
    public String text;
    public String name;
    public String screenName;
    public long id;
    public int start = -1;
    public int end = -1;

    UserMentionEntity() {
    }

    public UserMentionEntity(String text, String name, String screenName, long id, int start, int end) {
        this.text = text;
        this.name = name;
        this.screenName = screenName;
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getId() {
        return id;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
