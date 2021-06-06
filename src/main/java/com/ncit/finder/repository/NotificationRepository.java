package com.ncit.finder.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ncit.finder.db.DB;
import com.ncit.finder.models.Notification;
import com.ncit.finder.models.Post;
import com.ncit.finder.models.User;
import com.ncit.finder.utils.LocalDateTimeParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationRepository {
    private DB db;

    @Autowired
    public NotificationRepository(DB db) {
        this.db = db;
    }

    public boolean save(Notification notification) {
        Connection connection = db.makeConnection();
        PreparedStatement statement;
        String sql = "INSERT INTO\n" + "notifications(initiator_id, post_id, seen, notification_type, initiated_on\n)"
                + "VALUES(?,?,?,?,?);";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, notification.getInitiator().getId());
            statement.setInt(2, notification.getPost().getId());
            statement.setBoolean(3, notification.isSeen());
            statement.setString(4, notification.getNotificationType());
            statement.setTimestamp(5, Timestamp.valueOf(notification.getInitiatedOn()));

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Notification> getAllNotifications(int userId, int n, LocalDateTime before) {
        Connection connection = db.makeConnection();
        PreparedStatement statement;
        List<Notification> notifications = new ArrayList<>();

        String sql = "SELECT n.id nid,n.seen nseen, n.notification_type ntype, n.initiated_on,\n"
                + "inu.id inu_id, inu.firstname inu_firstname, inu.lastname inu_lastname,\n"
                + "inu.middlename inu_middlename, inu.profile_pic inu_pp,\n" + "pid, sp.content,\n"
                + "pu_id, pu_firstname, pu_middlename,pu_lastname\n" + "FROM notifications n\n"
                + "INNER JOIN users inu ON inu.id = n.initiator_id\n" + "INNER JOIN (\n"
                + "SELECT p.id pid , p.content , \n"
                + "u.id pu_id, u.firstname pu_firstname, u.lastname pu_lastname, u.middlename pu_middlename\n"
                + "FROM posts p\n" + "INNER JOIN users u ON p.user_id = u.id\n" + "WHERE p.user_id = ?\n"
                + ")sp ON sp.pid = n.post_id\n" + "WHERE n.initiated_on < '" + before + "'\n"
                + "ORDER BY n.initiated_on DESC LIMIT " + n + ";";

                System.out.println(sql);
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Notification notification = new Notification();

            User initiator = new User();
            initiator.setId(resultSet.getInt("inu_id"));
            initiator.setFirstName(resultSet.getString("inu_firstname"));
            initiator.setMiddleName(resultSet.getString("inu_middlename"));
            initiator.setLastName(resultSet.getString("inu_lastname"));
            initiator.setProfilePic(resultSet.getString("inu_pp"));

            notification.setInitiator(initiator);

            Post post = new Post();
            post.setId(resultSet.getInt("pid"));
            post.setContent(resultSet.getString("content"));

            User user = new User();
            user.setId(resultSet.getInt("pu_id"));
            user.setFirstName(resultSet.getString("pu_firstname"));
            user.setMiddleName(resultSet.getString("pu_middlename"));
            user.setLastName(resultSet.getString("pu_lastname"));

            post.setUser(user);

            notification.setPost(post);
            notification.setId(resultSet.getInt("nid"));
            notification.setSeen(resultSet.getBoolean("nseen"));
            notification.setNotificationType(resultSet.getString("ntype"));
            
            if (resultSet.getTimestamp("initiated_on") != null) {
                notification.setInitiatedOn(resultSet.getTimestamp("initiated_on").toLocalDateTime());

                LocalDateTime fromTemp = notification.getInitiatedOn();
                long[] parsedDateTime = LocalDateTimeParser.parse(fromTemp);
                long years = parsedDateTime[0];
                long months = parsedDateTime[1];
                long days = parsedDateTime[2];
                long hours = parsedDateTime[3];
                long minutes = parsedDateTime[4];
                long seconds = parsedDateTime[5];

                notification.setYearsTillNow(years);
                notification.setMonthsTillNow(months);
                notification.setDaysTillNow(days);
                notification.setHoursTillNow(hours);
                notification.setMinutesTillNow(minutes);
                notification.setSecondsTillNow(seconds);

            }
            notifications.add(notification);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public Notification getNotificationById(int id){
        Connection connection = db.makeConnection();
        PreparedStatement statement;
        Notification notification = null;

        String sql = "SELECT * FROM notifications WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                 notification = new Notification();

                int initiatorId = resultSet.getInt("initiator_id");
                int postId = resultSet.getInt("post_id");
                boolean seen = resultSet.getBoolean("seen");
                String notificationType = resultSet.getString("notification_type");
                if (resultSet.getTimestamp("initiated_on") != null) {
                    notification.setInitiatedOn(resultSet.getTimestamp("initiated_on").toLocalDateTime());
                }
                Post post = new Post();
                post.setId(postId);

                User initiator = new User();
                initiator.setId(initiatorId);

                notification.setId(id);
                notification.setInitiator(initiator);
                notification.setPost(post);
                notification.setSeen(seen);
                notification.setNotificationType(notificationType);

                return notification;
             
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean markAsSeen(int nid){

        Connection connection = db.makeConnection();
        PreparedStatement statement;
        String sql = "UPDATE notifications \n" 
                    + "SET seen = true\n"
                    + "WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, nid);
        
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
