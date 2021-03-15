/*
users
posts
likes
comments
hashtags
posts_hashtags
followings
*/

CREATE TABLE users(
	id INT PRIMARY KEY,
    firstname VARCHAR(50),
    middlename VARCHAR(50),
    lastname VARCHAR(50),
    joined_on DATETIME
);

CREATE TABLE posts(
 	id INT PRIMARY KEY,
 	content TEXT,
    posted_on DATETIME,
    posted_user_id INT, 
 	likes_count INT,
    comments_count INT,
    join_requests_count INT,
    FOREIGN KEY(posted_user_id) REFERENCES users(id)
);


CREATE TABLE likes(
 	liked_by_user_id INT,
    post_id INT,
    liked_on DATETIME,
    PRIMARY KEY(liked_by_user_id, post_id),
    FOREIGN KEY(liked_by_user_id) REFERENCES users(id),
    FOREIGN KEY(post_id) REFERENCES posts(id)
);

CREATE TABLE comments(
	id INT PRIMARY KEY,
    content TEXT,
    posted_on DATETIME,
    user_id INT,
    post_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(post_id) REFERENCES posts(id)
);

CREATE TABLE hashtags(
	title VARCHAR(50) PRIMARY KEY,
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
	user_id INT,
    hashtag VARCHAR(50),
    followed_on DATETIME,
    PRIMARY KEY(user_id, hashtag),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(hashtag) REFERENCES hashtags(title)
);

