USE `nhn_academy_219`;
CREATE TABLE `jdbc_users`
(
    `user_id`       varchar(50)                                                  NOT NULL,
    `user_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `user_password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* ================================================================================================================== */

/* install */
INSERT INTO jdbc_users(user_id, user_name, user_password) VALUES(?, ?, ?)

/* select */
SELECT * FROM jdbc_users WHERE user_id=?

/* select by id and password */
SELECT * FROM jdbc_users WHERE user_id=? AND user_password=?

/* update */
UPDATE jdbc_users SET user_password=? WHERE user_id=?

/* delete */
DELETE FROM jdbc_users WHERE user_id=?
