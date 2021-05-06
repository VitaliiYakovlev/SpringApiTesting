package api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class CurrencyCourseToUserWithTime {

    private final Set<CurrencyCourseToUser> course;
    private final long timestamp;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CurrencyCourseToUserWithTime(
            @JsonProperty("course") Set<CurrencyCourseToUser> course,
            @JsonProperty("timestamp") long timestamp) {
        this.course = course;
        this.timestamp = timestamp;
    }

    public Set<CurrencyCourseToUser> getCourse() {
        return course;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
