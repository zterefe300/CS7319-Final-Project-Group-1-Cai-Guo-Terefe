
--  add reorder_quantity will be used in the automatic reorder logic when sending text message or email
alter table item add reorder_quantity int default 0;

-- Create a new table called “Reorder_Tracker”
DROP TABLE IF EXISTS `reorder_tracker`;
CREATE TABLE `reorder_tracker` (
                        `item_id` int NOT NULL,
                        `vendor_id` int NOT NULL,
                        `status` int NOT NULL,
                        `error_message` varchar(255) DEFAULT NULL,
                        `date` datetime DEFAULT NULL,
                        PRIMARY KEY (`item_id`,`status`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='reorder_tracker';