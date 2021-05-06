package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class UpdateCaseModel {

    private final String toUpperCase;
    private final String toLowerCase;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UpdateCaseModel(
            @JsonProperty("toUpperCase") String toUpperCase,
            @JsonProperty("toLowerCase") String toLowerCase) {
        this.toUpperCase = toUpperCase;
        this.toLowerCase = toLowerCase;
    }
    public String getToUpperCase() {
        return toUpperCase;
    }
    public String getToLowerCase() {
        return toLowerCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateCaseModel that = (UpdateCaseModel) o;
        return Objects.equals(toUpperCase, that.toUpperCase)
                && Objects.equals(toLowerCase, that.toLowerCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toUpperCase, toLowerCase);
    }
}
