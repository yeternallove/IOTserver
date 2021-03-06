package cn.edu.zucc.eternallove.iotserver.data;

/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/25 15:28
 */

public class ChatMessageBean {
    int id;
    private String sender_id;
    private String recipient_id;
    private long timestampe;
    private String message;

    public ChatMessageBean() {
    }

    public ChatMessageBean(String sender_id, String recipient_id, long timestampe, String message) {
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.timestampe = timestampe;
        this.message = message;
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public long getTimestampe() {
        return timestampe;
    }

    public void setTimestampe(long timestampe) {
        this.timestampe = timestampe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
