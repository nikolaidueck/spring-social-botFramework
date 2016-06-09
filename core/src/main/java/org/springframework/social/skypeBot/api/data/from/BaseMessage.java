package org.springframework.social.skypeBot.api.data.from;

import java.util.Date;

import org.springframework.social.skypeBot.api.dict.ActivityType;

/**
 * @author Anton Leliuk
 */
public abstract class BaseMessage {

    /**
     * The message activityId.
     * Optional: Is 0 when message cannot be referenced.
     */
    private String id;

    /**
     * Identifies the sender, and is transparent for the receiver such that
     * the Bot can use it to send a message in response.
     */
    private String from;

    /**
     * Identifies the receiver.
     */
    private String to;

    /**
     * The server time of the event in ISO 8601 format.
     */
    private Date time;

    /**
     * Which event happened and what other fields to expect.
     * For message this field contains constant string: “message”
     */
    private ActivityType activity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ActivityType getActivity() {
        return activity;
    }

    public void setActivity(ActivityType activity) {
        this.activity = activity;
    }
}
