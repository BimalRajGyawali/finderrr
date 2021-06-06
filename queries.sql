/*
users
posts
comments
hashtags
posts_hashtags
followings
join_requests
*/

CREATE TABLE users(
	id SERIAL PRIMARY KEY ,
    firstname VARCHAR(50),
    middlename VARCHAR(50),
    lastname VARCHAR(50),
    bio VARCHAR(100),
    email VARCHAR(100),
    pass VARCHAR(100),
    profile_pic VARCHAR(100),
    joined_on TIMESTAMP
);

CREATE TABLE posts(
 	id SERIAL PRIMARY KEY ,
 	content TEXT,
    posted_on TIMESTAMP,
    user_id INT, 
    comments_count INT,
    join_requests_count INT,
    bio VARCHAR(100),
    status VARCHAR(30),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE comments(
	id SERIAL PRIMARY KEY ,
    content TEXT,
    commented_on TIMESTAMP,
    user_id INT,
    post_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(post_id) REFERENCES posts(id)
);


CREATE TABLE hashtags(
	title VARCHAR(50)  PRIMARY KEY,
    total_followers INT
);

CREATE TABLE posts_hashtags(
	post_id INT,
    hashtag VARCHAR(50),
    PRIMARY KEY(post_id, hashtag),
    FOREIGN KEY(post_id) REFERENCES posts(id),
    FOREIGN KEY(hashtag) REFERENCES hashtags(title)
);

CREATE TABLE followings(
	user_id INT ,
    hashtag VARCHAR(50),
    followed_on TIMESTAMP,
    PRIMARY KEY(user_id, hashtag),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(hashtag) REFERENCES hashtags(title)
);

CREATE TABLE join_requests(
    post_id INT,
    user_id INT,
    requested_on TIMESTAMP,
    PRIMARY KEY(user_id, post_id),
   	FOREIGN KEY(post_id) REFERENCES posts(id),
    FOREIGN KEY(user_id) REFERENCES users(id)

);

CREATE TABLE notifications(
  id SERIAL PRIMARY KEY,
  initiator_id INT,
  post_id INT,
  seen BOOLEAN,
  notification_type VARCHAR(50),
  initiated_on TIMESTAMP,
  FOREIGN KEY(initiator_id) REFERENCES users(id),
  FOREIGN KEY(post_id) REFERENCES posts(id)
);