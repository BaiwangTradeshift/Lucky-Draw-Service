CREATE TABLE `ruleset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ruleset_name_index` (`name`)
);

CREATE TABLE `rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `ruleset_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Rule_Ruleset_idx` (`ruleset_id`),
  KEY `rule_name_index` (`name`),
  CONSTRAINT `fk_rule_ruleset` FOREIGN KEY (`ruleset_id`) REFERENCES `ruleset` (`id`)
);

CREATE TABLE `round` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_time` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2),
  `ruleset_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Round_Ruleset1_idx` (`ruleset_id`),
  CONSTRAINT `fk_round_ruleset1` FOREIGN KEY (`ruleset_id`) REFERENCES `ruleset` (`id`)
);

CREATE TABLE `participant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `display_name` varchar(45) DEFAULT NULL,
  `create_time` timestamp(2) NOT NULL DEFAULT CURRENT_TIMESTAMP(2) ON UPDATE CURRENT_TIMESTAMP(2),
  `ruleset_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `participant_ruleset_id_index` (`ruleset_id`),
  CONSTRAINT `participant_ruleset_id_fk` FOREIGN KEY (`ruleset_id`) REFERENCES `ruleset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `drawresult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `round_id` bigint(20) NOT NULL,
  `rule_id` bigint(20) NOT NULL,
  `participant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_drawresult_participant1_idx` (`participant_id`),
  KEY `fk_drawresult_round1_idx` (`round_id`),
  KEY `fk_drawresult_rule1_idx` (`rule_id`),
  CONSTRAINT `fk_drawresult_participant1` FOREIGN KEY (`participant_id`) REFERENCES `participant` (`id`),
  CONSTRAINT `fk_drawresult_round1` FOREIGN KEY (`round_id`) REFERENCES `round` (`id`),
  CONSTRAINT `fk_drawresult_rule1` FOREIGN KEY (`rule_id`) REFERENCES `rule` (`id`)
);
