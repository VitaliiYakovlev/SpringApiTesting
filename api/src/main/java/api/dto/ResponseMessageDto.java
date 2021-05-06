package api.dto;

public class ResponseMessageDto {

    private String updatedMessage;
    private long updatedId;

    public ResponseMessageDto() {
    }

    public ResponseMessageDto(String updatedMessage, long updatedId) {
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
}
