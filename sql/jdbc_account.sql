USE `nhn_academy_219`;
CREATE TABLE `jdbc_account`
(
    `account_number` bigint unsigned NOT NULL COMMENT '계좌번호',
    `name`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '고객_이름',
    `balance`        bigint DEFAULT '0' COMMENT '잔고',
    PRIMARY KEY (`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

