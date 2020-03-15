-- gateway 路由信息
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(64) NOT NULL,
  `uri` varchar(100) NOT NULL COMMENT '转发地址',
  `sort` tinyint(4) DEFAULT NULL COMMENT '顺序',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(64) NOT NULL DEFAULT '',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(64) NOT NULL DEFAULT '',
  `dr` tinyint(1) DEFAULT '0' COMMENT '删除标记,0:否,1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT ='gateway 路由信息';

-- gateway 路由filter信息
DROP TABLE IF EXISTS `gateway_route_filter`;
CREATE TABLE `gateway_route_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) NOT NULL COMMENT '路由id',
  `service_id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '类型名字',
  `args_name` varchar(200) NOT NULL COMMENT '参数配置名字',
  `args_value` varchar(200) NOT NULL COMMENT '参数配置值',
  `sort` tinyint(2) DEFAULT '0' COMMENT '顺序',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(64) NOT NULL DEFAULT '',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(64) NOT NULL DEFAULT '',
  `dr` tinyint(1) DEFAULT '0' COMMENT '删除标记,0:否,1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT ='gateway 路由filter信息';

-- gateway 路由predicate信息
DROP TABLE IF EXISTS `gateway_route_predicate`;
CREATE TABLE `gateway_route_predicate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) NOT NULL COMMENT '路由id',
  `service_id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '类型名字',
  `args_name` varchar(200) NOT NULL COMMENT '参数配置名字',
  `args_value` varchar(200) NOT NULL COMMENT '参数配置值',
  `sort` tinyint(2) DEFAULT '0' COMMENT '顺序',
  `remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注信息',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(64) NOT NULL DEFAULT '',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(64) NOT NULL DEFAULT '',
  `dr` tinyint(1) DEFAULT '0' COMMENT '删除标记,0:否,1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT ='gateway 路由predicate信息' ;

INSERT INTO `cloud-gateway`.`gateway_route`(`id`, `service_id`, `uri`, `sort`, `remarks`, `created_at`, `created_by`, `updated_at`, `updated_by`, `dr`) VALUES (1, 'member-center', 'lb://member-center', 0, '', '2020-02-10 20:10:29', '', '2020-02-10 20:10:29', '', 0);

INSERT INTO `cloud-gateway`.`gateway_route_filter`(`id`, `route_id`, `service_id`, `name`, `args_name`, `args_value`, `sort`, `remarks`, `created_at`, `created_by`, `updated_at`, `updated_by`, `dr`) VALUES (1, 1, 'member-center', 'StripPrefix', '_genkey_0', '1', 0, '', '2020-02-10 20:10:29', '', '2020-02-10 20:10:29', '', 0);

INSERT INTO `cloud-gateway`.`gateway_route_predicate`(`id`, `route_id`, `service_id`, `name`, `args_name`, `args_value`, `sort`, `remarks`, `created_at`, `created_by`, `updated_at`, `updated_by`, `dr`) VALUES (1, 1, 'member-center', 'Path', 'pattern', '/member-center/**', 0, '', '2020-02-10 20:10:29', '', '2020-02-10 20:10:29', '', 0);
