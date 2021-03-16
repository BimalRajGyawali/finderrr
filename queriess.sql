CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(50),
    middlename VARCHAR(50),
    lastname VARCHAR(50),
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
    id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(50) ,
    total_followers INT
);

CREATE TABLE posts_hashtags(
	post_id INT,
    hashtag_id INT,
    PRIMARY KEY(post_id, hashtag_id),
    FOREIGN KEY(post_id) REFERENCES posts(id),
    FOREIGN KEY(hashtag_id) REFERENCES hashtags(id)
);

CREATE TABLE followings(
	user_id INT ,
    hashtag_id INT,
    followed_on DATETIME,
    PRIMARY KEY(user_id, hashtag_id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(hashtag_id) REFERENCES hashtags(id)
);


CREATE TABLE join_requests(
 	user_id INT,
    post_id INT,
    requested_on DATETIME,
    PRIMARY KEY(user_id, post_id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(post_id) REFERENCES posts(id)
);
