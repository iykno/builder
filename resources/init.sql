-- ----------------------------
-- Table structure for tb_account_one
-- ----------------------------
DROP TABLE IF EXISTS `tb_account_one`;
CREATE TABLE `tb_account_one`  (
  `org_id` int(11) NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL,
  `account_time` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_account_one
-- ----------------------------
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');
INSERT INTO `tb_account_one` VALUES (1, 1, '2018-11-09 16:03:28');
INSERT INTO `tb_account_one` VALUES (2, 2, '2018-11-18 16:03:36');

-- ----------------------------
-- Table structure for tb_account_two
-- ----------------------------
DROP TABLE IF EXISTS `tb_account_two`;
CREATE TABLE `tb_account_two`  (
  `org_id` int(11) NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL,
  `account_time` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_account_two
-- ----------------------------
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');
INSERT INTO `tb_account_two` VALUES (1, 1, '2018-11-08 16:03:28');
INSERT INTO `tb_account_two` VALUES (2, 2, '2018-11-16 16:03:36');

-- ----------------------------
-- Table structure for tb_org
-- ----------------------------
DROP TABLE IF EXISTS `tb_org`;
CREATE TABLE `tb_org`  (
  `org_id` int(11) NOT NULL AUTO_INCREMENT,
  `org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_org_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_org
-- ----------------------------
INSERT INTO `tb_org` VALUES (1, 'org no1', 0);
INSERT INTO `tb_org` VALUES (2, 'org no2', 0);