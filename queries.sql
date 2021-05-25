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
	id INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(50),
    middlename VARCHAR(50),
    lastname VARCHAR(50),
    bio VARCHAR(100),
    email VARCHAR(100),
    pass VARCHAR(100),
    profile_pic VARCHAR(100),
    joined_on DATETIME
);

CREATE TABLE posts(
 	id INT PRIMARY KEY AUTO_INCREMENT,
 	content TEXT,
    posted_on DATETIME,
    user_id INT, 
    comments_count INT,
    join_requests_count INT,
    bio VARCHAR(100),
    status VARCHAR(30),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE comments(
	id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT,
    commented_on DATETIME,
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
    followed_on DATETIME,
    PRIMARY KEY(user_id, hashtag),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(hashtag) REFERENCES hashtags(title)
);

CREATE TABLE join_requests(
    post_id INT,
    user_id INT,
    requested_on DATETIME,
     PRIMARY KEY(user_id, post_id),
   	FOREIGN KEY(post_id) REFERENCES posts(id),
    FOREIGN KEY(user_id) REFERENCES users(id)

);
