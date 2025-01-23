USE `nhn_academy_219`;
CREATE TABLE `jdbc_students`
(
    `id`         varchar(50) NOT NULL COMMENT '학생-아이디',
    `name`       varchar(50) NOT NULL COMMENT '학생-이름',
    `gender`     varchar(1)  NOT NULL COMMENT '성별 (M | F)',
    `age`        int         NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='학생';

/* install */
INSERT INTO jdbc_students(id, name, gender, age, created_at) VALUES(?, ?, ?, ?, ?);

/* select */
SELECT * FROM jdbc_students WHERE id=?;

/* update */
UPDATE jdbc_students SET name=?, gender=?, age=? WHERE id=?;

/* delete */
DELETE FROM jdbc_students WHERE id=?;
