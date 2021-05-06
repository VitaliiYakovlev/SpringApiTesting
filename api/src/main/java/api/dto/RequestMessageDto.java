package api.dto;

import java.util.Objects;

public class RequestMessageDto {

    private String msg;
    private long id;

    public RequestMessageDto() {
    }

    public RequestMessageDto(String msg, long id) {
        this.msg = msg;
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMessageDto that = (RequestMessageDto) o;
        return id == that.id && Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, id);
    }
}
