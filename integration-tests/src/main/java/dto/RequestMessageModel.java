package dto;

public class RequestMessageModel {

    private String msg;
    private long id;

    public RequestMessageModel() {
    }

    public RequestMessageModel(String msg, long id) {
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
}
