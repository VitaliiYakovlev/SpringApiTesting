package dto;

import java.util.Objects;

public class ResponseMessageModel {

    private String updatedMessage;
    private long updatedId;

    public ResponseMessageModel() {
    }

    public ResponseMessageModel(String updatedMessage, long updatedId) {
        this.updatedMessage = updatedMessage;
        this.updatedId = updatedId;
    }

    public String getUpdatedMessage() {
        return updatedMessage;
    }

    public void setUpdatedMessage(String updatedMessage) {
        this.updatedMessage = updatedMessage;
    }

    public long getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(long updatedId) {
        this.updatedId = updatedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseMessageModel that = (ResponseMessageModel) o;
        return updatedId == that.updatedId && Objects.equals(updatedMessage, that.updatedMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updatedMessage, updatedId);
    }

    @Override
    public String toString() {
        return "ResponseMessageModel{" +
                "updatedMessage='" + updatedMessage + '\'' +
                ", updatedId=" + updatedId +
                '}';
    }
}
