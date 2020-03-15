-- ----------------------------
-- Table structure for gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(64) DEFAULT NULL,
  `uri` varchar(100) DEFAULT NULL COMMENT '转发地址',
  `predicates` varchar(200) DEFAULT NULL COMMENT '访问路径',
  `filters` varchar(100) DEFAULT NULL COMMENT '过滤',
  `order_` varchar(2) DEFAULT '0' COMMENT '顺序',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `dr` tinyint(1) DEFAULT 0 COMMENT '删除标记,0:否,1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
