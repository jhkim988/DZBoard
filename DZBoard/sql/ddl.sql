/* 프로시저: 회원 삭제 + 임시 테이블 이동 트랜잭션 처리 */
CREATE DEFINER=`root`@`localhost` PROCEDURE `pc_dzboard_deleteMember`(
	IN `member_id` VARCHAR(50),
	OUT `result` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	DECLARE exit handler FOR NOT FOUND
		begin
			ROLLBACK;
			SET result = 0;
		END;
		
	DECLARE exit handler FOR SQLEXCEPTION
		begin
			ROLLBACK;
			SET result = 0;
		END;
		
START TRANSACTION;
	INSERT INTO tb_dzboard_tmp_member (id, pwd, NAME, email, phone, createdAt, authority)
		SELECT id, pwd, NAME, email, phone, createdAt, authority FROM tb_dzboard_member WHERE id = member_id;
	delete from tb_dzboard_member where id = member_id;
COMMIT;
SET result = 1;
END

/* 프로시저: 게시글 삭제 + 임시 테이블 이동 트랜잭션 */
CREATE DEFINER=`root`@`localhost` PROCEDURE `pc_dzboard_deletePost`(
	IN `post_id` INT,
	OUT `result` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	DECLARE exit handler FOR NOT FOUND
		begin
			ROLLBACK;
			SET result = 0;
		END;
		
	DECLARE exit handler FOR SQLEXCEPTION
		begin
			ROLLBACK;
			SET result = 0;
		END;
		
START TRANSACTION;
	INSERT INTO tb_dzboard_tmp_board (id, author, title, content, createdAt, category, viewcount, good, bad)
		SELECT id, author, title, content, createdAt, category, viewcount, good, bad FROM tb_dzboard_board WHERE id = post_id;
	delete from tb_dzboard_board where id = post_id;
COMMIT;
SET result = 1;
END

/* 프로시저: 글 작성 */
CREATE DEFINER=`root`@`localhost` PROCEDURE `pc_dzboard_insertPost`(
	IN `author` VARCHAR(50),
	IN `title` VARCHAR(50),
	IN `content` VARCHAR(50),
	IN `category` VARCHAR(50),
	OUT `lastId` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
DECLARE exit handler FOR NOT FOUND
	begin
		ROLLBACK;
		SET lastId = -1;
	END;
	
DECLARE exit handler FOR SQLEXCEPTION
	begin
		ROLLBACK;
		SET lastId = -1;
	END;
SET lastId = -1;
START TRANSACTION;
	INSERT into tb_dzboard_board (`author`, `title`, `content`, `category`) VALUE (author, title, content, category);
	SET lastId = LAST_INSERT_ID();
	UPDATE tb_dzboard_board SET parent = lastId WHERE id = lastId;
COMMIT;
END
/* 프로시저: 답글 작성 */
BEGIN
DECLARE exit handler FOR NOT FOUND
	begin
		ROLLBACK;
		SET lastId = -1;
	END;
	
DECLARE exit handler FOR SQLEXCEPTION
	begin
		ROLLBACK;
		SET lastId = -1;
	END;
SET lastId = -1;
START TRANSACTION;
	INSERT into tb_dzboard_board (`parent`, `author`, `title`, `content`, `category`) VALUE (parent, author, title, content, category);
	SET lastId = LAST_INSERT_ID();
COMMIT;
END

/* 테이블 생성: Board 테이블 */
CREATE TABLE `tb_dzboard_board` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`author` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`title` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`content` VARCHAR(4000) NOT NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`createdAt` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`category` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`viewcount` INT(11) NOT NULL DEFAULT '0',
	`good` INT(11) NOT NULL DEFAULT '0',
	`bad` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=403
;


/* 테이블 생성: category 테이블 */
CREATE TABLE `tb_dzboard_board_category` (
	`value` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`textContent` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`authority` INT(11) NOT NULL DEFAULT '1'
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

/* 테이블 생성: 추천/비추천 이력 */
CREATE TABLE `tb_dzboard_goodbad` (
	`good` CHAR(1) NOT NULL DEFAULT 'y' COLLATE 'utf8mb4_general_ci',
	`memberid` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`postid` INT(11) NOT NULL,
	`time` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	PRIMARY KEY (`good`, `memberid`, `postid`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

/* 테이블 생성: 회원 테이블 */
CREATE TABLE `tb_dzboard_member` (
	`id` VARCHAR(15) NOT NULL COLLATE 'utf8mb4_general_ci',
	`pwd` VARCHAR(15) NOT NULL COLLATE 'utf8mb4_general_ci',
	`name` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_general_ci',
	`email` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`phone` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`createdAt` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`updatedAt` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`authority` INT(11) NOT NULL DEFAULT '1',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `phone` (`phone`) USING BTREE,
	UNIQUE INDEX `email` (`email`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;


/* 테이블 생성: 게시글 임시 테이블(게시글 삭제 시 이곳으로 이동)*/
CREATE TABLE `tb_dzboard_tmp_board` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`author` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`title` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`content` VARCHAR(4000) NOT NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`createdAt` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`category` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`viewcount` INT(11) NOT NULL DEFAULT '0',
	`good` INT(11) NOT NULL DEFAULT '0',
	`bad` INT(11) NOT NULL DEFAULT '0',
	`deletedAt` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=401
;


/* 테이블 생성: 임시 회원 테이블 (회원 삭제 시 이곳으로 이동) */
CREATE TABLE `tb_dzboard_tmp_member` (
	`id` VARCHAR(15) NOT NULL COLLATE 'utf8mb4_general_ci',
	`pwd` VARCHAR(15) NOT NULL COLLATE 'utf8mb4_general_ci',
	`name` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_general_ci',
	`email` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`phone` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`createdAt` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`deletedAt` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`authority` INT(11) NOT NULL DEFAULT '1',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `phone` (`phone`) USING BTREE,
	UNIQUE INDEX `email` (`email`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;


/* 테이블 생성: UrlAuth 테이블 생성 */
CREATE TABLE `tb_dzboard_urlauth` (
	`url` VARCHAR(200) NOT NULL COLLATE 'utf8mb4_general_ci',
	`authority` VARCHAR(200) NOT NULL COLLATE 'utf8mb4_general_ci',
	`note` VARCHAR(4000) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`url`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;
