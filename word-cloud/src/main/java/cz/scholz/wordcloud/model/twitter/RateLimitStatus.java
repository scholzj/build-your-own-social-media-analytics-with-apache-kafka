package cz.scholz.wordcloud.model.twitter;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class RateLimitStatus {
    public int remaining;
    public int limit;
    public int resetTimeInSeconds;
    public int secondsUntilReset;

    public RateLimitStatus() {
    }

    public RateLimitStatus(int remaining, int limit, int resetTimeInSeconds, int secondsUntilReset) {
        this.remaining = remaining;
        this.limit = limit;
        this.resetTimeInSeconds = resetTimeInSeconds;
        this.secondsUntilReset = secondsUntilReset;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getLimit() {
        return limit;
    }

    public int getResetTimeInSeconds() {
        return resetTimeInSeconds;
    }

    public int getSecondsUntilReset() {
        return secondsUntilReset;
    }
}
