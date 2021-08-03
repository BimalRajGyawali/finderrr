package com.ncit.finder.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
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


}
