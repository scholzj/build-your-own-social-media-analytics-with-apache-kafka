package cz.scholz.sentimentanalysis.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Scopes {
    public String[] placeIds;

    public Scopes() {
    }

    public Scopes(String[] placeIds) {
        this.placeIds = placeIds;
    }

    public String[] getPlaceIds() {
        return placeIds;
    }
}
