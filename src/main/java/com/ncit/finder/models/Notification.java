package com.ncit.finder.models;

import com.ncit.finder.functionality.LocalDateTimeParser;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
public class Notification {
    private int id;
    private User initiator;
    private Post post;
    private boolean seen;
    private String notificationType;
    private LocalDateTime initiatedOn;
    private DurationTillNow durationTillNow;

    public static final String COMMENT = "comment";
    public static final String JOIN_REQUEST = "joinrequest";


    public void setInitiatedOn(LocalDateTime initiatedOn) {
        this.initiatedOn = initiatedOn;
        this.durationTillNow = LocalDateTimeParser.parse(initiatedOn);
    }
}
