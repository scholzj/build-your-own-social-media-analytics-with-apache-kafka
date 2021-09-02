package cz.scholz.wordcloud.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SymbolEntity {
    public int start = -1;
    public int end = -1;

    SymbolEntity() {
    }

    public SymbolEntity(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
