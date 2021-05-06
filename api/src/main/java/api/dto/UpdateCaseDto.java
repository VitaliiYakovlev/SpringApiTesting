package api.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateCaseDto {

    @NotNull(message = "shall not be null")
    private final String toUpperCase;

    @NotNull(message = "shall not be null")
    private final String toLowerCase;

    public UpdateCaseDto(String toUpperCase, String toLowerCase) {
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
        UpdateCaseDto that = (UpdateCaseDto) o;
        return toUpperCase.equals(that.toUpperCase) && toLowerCase.equals(that.toLowerCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toUpperCase, toLowerCase);
    }
}
