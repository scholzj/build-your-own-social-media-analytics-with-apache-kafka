package cz.scholz.sentimentanalysis.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class HashtagEntity {
    public String text;
    public int start = -1;
    public int end = -1;

    HashtagEntity() {
    }

    public HashtagEntity(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
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
}
