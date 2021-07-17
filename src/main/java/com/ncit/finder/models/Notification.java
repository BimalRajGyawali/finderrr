package com.ncit.finder.models;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private User initiator;
    private Post post;
    private boolean seen;
    private String notificationType;
    private LocalDateTime initiatedOn;

	private long yearsTillNow;
	private long monthsTillNow;
    private long daysTillNow;
	private long hoursTillNow;
	private long minutesTillNow;
	private long secondsTillNow;

    public static final String COMMENT = "comment";
    public static final String JOIN_REQUEST = "joinrequest";

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getInitiator() {
        return initiator;
    }
    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public boolean isSeen() {
        return seen;
    }
    public void setSeen(boolean seen) {
        this.seen = seen;
    }
    public String getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    public LocalDateTime getInitiatedOn() {
        return initiatedOn;
    }
    public void setInitiatedOn(LocalDateTime initiatedOn) {
        this.initiatedOn = initiatedOn;
    }
    public long getYearsTillNow() {
        return yearsTillNow;
    }
    public void setYearsTillNow(long yearsTillNow) {
        this.yearsTillNow = yearsTillNow;
    }
    public long getMonthsTillNow() {
        return monthsTillNow;
    }
    public void setMonthsTillNow(long monthsTillNow) {
        this.monthsTillNow = monthsTillNow;
    }
    public long getDaysTillNow() {
        return daysTillNow;
    }
    public void setDaysTillNow(long daysTillNow) {
        this.daysTillNow = daysTillNow;
    }
    public long getHoursTillNow() {
        return hoursTillNow;
    }
    public void setHoursTillNow(long hoursTillNow) {
        this.hoursTillNow = hoursTillNow;
    }
    public long getMinutesTillNow() {
        return minutesTillNow;
    }
    public void setMinutesTillNow(long minutesTillNow) {
        this.minutesTillNow = minutesTillNow;
    }
    public long getSecondsTillNow() {
        return secondsTillNow;
    }
    public void setSecondsTillNow(long secondsTillNow) {
        this.secondsTillNow = secondsTillNow;
    }

    @Override
    public String toString() {
        return "Notification [id=" + id + ", initiatedOn=" + initiatedOn + ", initiator=" + initiator
                + ", notificationType=" + notificationType + ", post=" + post + ", seen=" + seen + "]";
    }
    
}
