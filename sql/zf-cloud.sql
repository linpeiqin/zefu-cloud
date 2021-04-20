/*
navicat mysql data transfer

source server         : 106.53.125.170
source server version : 50724
source host           : 106.53.125.170:3306
source database       : zf-cloud

target server type    : mysql
target server version : 50724
file encoding         : 65001

date: 2021-02-05 10:03:38
*/

set foreign_key_checks=0;

-- ----------------------------
-- table structure for gen_table
-- ----------------------------
drop table if exists `gen_table`;
create table `gen_table` (
  `table_id` bigint(20) not null auto_increment comment '编号',
  `table_name` varchar(200) default '' comment '表名称',
  `table_comment` varchar(500) default '' comment '表描述',
  `sub_table_name` varchar(64) default null comment '关联子表的表名',
  `sub_table_fk_name` varchar(64) default null comment '子表关联的外键名',
  `class_name` varchar(100) default '' comment '实体类名称',
  `tpl_category` varchar(200) default 'crud' comment '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) default null comment '生成包路径',
  `module_name` varchar(30) default null comment '生成模块名',
  `business_name` varchar(30) default null comment '生成业务名',
  `function_name` varchar(50) default null comment '生成功能名',
  `function_author` varchar(50) default null comment '生成功能作者',
  `gen_type` char(1) default '0' comment '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) default '/' comment '生成路径（不填默认项目路径）',
  `options` varchar(1000) default null comment '其它生成选项',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`table_id`)
) engine=innodb auto_increment=2 default charset=utf8 comment='代码生成业务表';

-- ----------------------------
-- table structure for gen_table_column
-- ----------------------------
drop table if exists `gen_table_column`;
create table `gen_table_column` (
  `column_id` bigint(20) not null auto_increment comment '编号',
  `table_id` varchar(64) default null comment '归属表编号',
  `column_name` varchar(200) default null comment '列名称',
  `column_comment` varchar(500) default null comment '列描述',
  `column_type` varchar(100) default null comment '列类型',
  `java_type` varchar(500) default null comment 'java类型',
  `java_field` varchar(200) default null comment 'java字段名',
  `is_pk` char(1) default null comment '是否主键（1是）',
  `is_increment` char(1) default null comment '是否自增（1是）',
  `is_required` char(1) default null comment '是否必填（1是）',
  `is_insert` char(1) default null comment '是否为插入字段（1是）',
  `is_edit` char(1) default null comment '是否编辑字段（1是）',
  `is_list` char(1) default null comment '是否列表字段（1是）',
  `is_query` char(1) default null comment '是否查询字段（1是）',
  `query_type` varchar(200) default 'eq' comment '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) default null comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) default '' comment '字典类型',
  `sort` int(11) default null comment '排序',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  primary key (`column_id`)
) engine=innodb auto_increment=2 default charset=utf8 comment='代码生成业务表字段';

-- ----------------------------
-- table structure for qrtz_blob_triggers
-- ----------------------------
drop table if exists `qrtz_blob_triggers`;
create table `qrtz_blob_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `blob_data` blob,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_blob_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_calendars
-- ----------------------------
drop table if exists `qrtz_calendars`;
create table `qrtz_calendars` (
  `sched_name` varchar(120) not null,
  `calendar_name` varchar(200) not null,
  `calendar` blob not null,
  primary key (`sched_name`,`calendar_name`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_cron_triggers
-- ----------------------------
drop table if exists `qrtz_cron_triggers`;
create table `qrtz_cron_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `cron_expression` varchar(200) not null,
  `time_zone_id` varchar(80) default null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_cron_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_fired_triggers
-- ----------------------------
drop table if exists `qrtz_fired_triggers`;
create table `qrtz_fired_triggers` (
  `sched_name` varchar(120) not null,
  `entry_id` varchar(95) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `instance_name` varchar(200) not null,
  `fired_time` bigint(13) not null,
  `sched_time` bigint(13) not null,
  `priority` int(11) not null,
  `state` varchar(16) not null,
  `job_name` varchar(200) default null,
  `job_group` varchar(200) default null,
  `is_nonconcurrent` varchar(1) default null,
  `requests_recovery` varchar(1) default null,
  primary key (`sched_name`,`entry_id`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_job_details
-- ----------------------------
drop table if exists `qrtz_job_details`;
create table `qrtz_job_details` (
  `sched_name` varchar(120) not null,
  `job_name` varchar(200) not null,
  `job_group` varchar(200) not null,
  `description` varchar(250) default null,
  `job_class_name` varchar(250) not null,
  `is_durable` varchar(1) not null,
  `is_nonconcurrent` varchar(1) not null,
  `is_update_data` varchar(1) not null,
  `requests_recovery` varchar(1) not null,
  `job_data` blob,
  primary key (`sched_name`,`job_name`,`job_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_locks
-- ----------------------------
drop table if exists `qrtz_locks`;
create table `qrtz_locks` (
  `sched_name` varchar(120) not null,
  `lock_name` varchar(40) not null,
  primary key (`sched_name`,`lock_name`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_paused_trigger_grps
-- ----------------------------
drop table if exists `qrtz_paused_trigger_grps`;
create table `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) not null,
  `trigger_group` varchar(200) not null,
  primary key (`sched_name`,`trigger_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_scheduler_state
-- ----------------------------
drop table if exists `qrtz_scheduler_state`;
create table `qrtz_scheduler_state` (
  `sched_name` varchar(120) not null,
  `instance_name` varchar(200) not null,
  `last_checkin_time` bigint(13) not null,
  `checkin_interval` bigint(13) not null,
  primary key (`sched_name`,`instance_name`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_simple_triggers
-- ----------------------------
drop table if exists `qrtz_simple_triggers`;
create table `qrtz_simple_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `repeat_count` bigint(7) not null,
  `repeat_interval` bigint(12) not null,
  `times_triggered` bigint(10) not null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_simple_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_simprop_triggers
-- ----------------------------
drop table if exists `qrtz_simprop_triggers`;
create table `qrtz_simprop_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `str_prop_1` varchar(512) default null,
  `str_prop_2` varchar(512) default null,
  `str_prop_3` varchar(512) default null,
  `int_prop_1` int(11) default null,
  `int_prop_2` int(11) default null,
  `long_prop_1` bigint(20) default null,
  `long_prop_2` bigint(20) default null,
  `dec_prop_1` decimal(13,4) default null,
  `dec_prop_2` decimal(13,4) default null,
  `bool_prop_1` varchar(1) default null,
  `bool_prop_2` varchar(1) default null,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  constraint `qrtz_simprop_triggers_ibfk_1` foreign key (`sched_name`, `trigger_name`, `trigger_group`) references `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for qrtz_triggers
-- ----------------------------
drop table if exists `qrtz_triggers`;
create table `qrtz_triggers` (
  `sched_name` varchar(120) not null,
  `trigger_name` varchar(200) not null,
  `trigger_group` varchar(200) not null,
  `job_name` varchar(200) not null,
  `job_group` varchar(200) not null,
  `description` varchar(250) default null,
  `next_fire_time` bigint(13) default null,
  `prev_fire_time` bigint(13) default null,
  `priority` int(11) default null,
  `trigger_state` varchar(16) not null,
  `trigger_type` varchar(8) not null,
  `start_time` bigint(13) not null,
  `end_time` bigint(13) default null,
  `calendar_name` varchar(200) default null,
  `misfire_instr` smallint(2) default null,
  `job_data` blob,
  primary key (`sched_name`,`trigger_name`,`trigger_group`),
  key `sched_name` (`sched_name`,`job_name`,`job_group`),
  constraint `qrtz_triggers_ibfk_1` foreign key (`sched_name`, `job_name`, `job_group`) references `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) engine=innodb default charset=utf8;

-- ----------------------------
-- table structure for sys_config
-- ----------------------------
drop table if exists `sys_config`;
create table `sys_config` (
  `config_id` int(5) not null auto_increment comment '参数主键',
  `config_name` varchar(100) default '' comment '参数名称',
  `config_key` varchar(100) default '' comment '参数键名',
  `config_value` varchar(500) default '' comment '参数键值',
  `config_type` char(1) default 'n' comment '系统内置（y是 n否）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`config_id`)
) engine=innodb auto_increment=100 default charset=utf8 comment='参数配置表';

-- ----------------------------
-- table structure for sys_dept
-- ----------------------------
drop table if exists `sys_dept`;
create table `sys_dept` (
  `dept_id` bigint(20) not null auto_increment comment '部门id',
  `parent_id` bigint(20) default '0' comment '父部门id',
  `ancestors` varchar(50) default '' comment '祖级列表',
  `dept_name` varchar(30) default '' comment '部门名称',
  `order_num` int(4) default '0' comment '显示顺序',
  `leader` varchar(20) default null comment '负责人',
  `phone` varchar(11) default null comment '联系电话',
  `email` varchar(50) default null comment '邮箱',
  `status` char(1) default '0' comment '部门状态（0正常 1停用）',
  `del_flag` char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  primary key (`dept_id`)
) engine=innodb auto_increment=200 default charset=utf8 comment='部门表';

-- ----------------------------
-- table structure for sys_dict_data
-- ----------------------------
drop table if exists `sys_dict_data`;
create table `sys_dict_data` (
  `dict_code` bigint(20) not null auto_increment comment '字典编码',
  `dict_sort` int(4) default '0' comment '字典排序',
  `dict_label` varchar(100) default '' comment '字典标签',
  `dict_value` varchar(100) default '' comment '字典键值',
  `dict_type` varchar(100) default '' comment '字典类型',
  `css_class` varchar(100) default null comment '样式属性（其他样式扩展）',
  `list_class` varchar(100) default null comment '表格回显样式',
  `is_default` char(1) default 'n' comment '是否默认（y是 n否）',
  `status` char(1) default '0' comment '状态（0正常 1停用）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`dict_code`)
) engine=innodb auto_increment=100 default charset=utf8 comment='字典数据表';

-- ----------------------------
-- table structure for sys_dict_type
-- ----------------------------
drop table if exists `sys_dict_type`;
create table `sys_dict_type` (
  `dict_id` bigint(20) not null auto_increment comment '字典主键',
  `dict_name` varchar(100) default '' comment '字典名称',
  `dict_type` varchar(100) default '' comment '字典类型',
  `status` char(1) default '0' comment '状态（0正常 1停用）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`dict_id`),
  unique key `dict_type` (`dict_type`)
) engine=innodb auto_increment=100 default charset=utf8 comment='字典类型表';

-- ----------------------------
-- table structure for sys_job
-- ----------------------------
drop table if exists `sys_job`;
create table `sys_job` (
  `job_id` bigint(20) not null auto_increment comment '任务id',
  `job_name` varchar(64) not null default '' comment '任务名称',
  `job_group` varchar(64) not null default 'default' comment '任务组名',
  `invoke_target` varchar(500) not null comment '调用目标字符串',
  `cron_expression` varchar(255) default '' comment 'cron执行表达式',
  `misfire_policy` varchar(20) default '3' comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) default '1' comment '是否并发执行（0允许 1禁止）',
  `status` char(1) default '0' comment '状态（0正常 1暂停）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default '' comment '备注信息',
  primary key (`job_id`,`job_name`,`job_group`)
) engine=innodb auto_increment=100 default charset=utf8 comment='定时任务调度表';

-- ----------------------------
-- table structure for sys_job_log
-- ----------------------------
drop table if exists `sys_job_log`;
create table `sys_job_log` (
  `job_log_id` bigint(20) not null auto_increment comment '任务日志id',
  `job_name` varchar(64) not null comment '任务名称',
  `job_group` varchar(64) not null comment '任务组名',
  `invoke_target` varchar(500) not null comment '调用目标字符串',
  `job_message` varchar(500) default null comment '日志信息',
  `status` char(1) default '0' comment '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) default '' comment '异常信息',
  `create_time` datetime default null comment '创建时间',
  primary key (`job_log_id`)
) engine=innodb default charset=utf8 comment='定时任务调度日志表';

-- ----------------------------
-- table structure for sys_logininfor
-- ----------------------------
drop table if exists `sys_logininfor`;
create table `sys_logininfor` (
  `info_id` bigint(20) not null auto_increment comment '访问id',
  `user_name` varchar(50) default '' comment '用户账号',
  `ipaddr` varchar(50) default '' comment '登录ip地址',
  `status` char(1) default '0' comment '登录状态（0成功 1失败）',
  `msg` varchar(255) default '' comment '提示信息',
  `access_time` datetime default null comment '访问时间',
  primary key (`info_id`)
) engine=innodb auto_increment=3 default charset=utf8 comment='系统访问记录';

-- ----------------------------
-- table structure for sys_menu
-- ----------------------------
drop table if exists `sys_menu`;
create table `sys_menu` (
  `menu_id` bigint(20) not null auto_increment comment '菜单id',
  `menu_name` varchar(50) not null comment '菜单名称',
  `parent_id` bigint(20) default '0' comment '父菜单id',
  `order_num` int(4) default '0' comment '显示顺序',
  `path` varchar(200) default '' comment '路由地址',
  `component` varchar(255) default null comment '组件路径',
  `is_frame` int(1) default '1' comment '是否为外链（0是 1否）',
  `is_cache` int(1) default '0' comment '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) default '' comment '菜单类型（m目录 c菜单 f按钮）',
  `visible` char(1) default '0' comment '菜单状态（0显示 1隐藏）',
  `status` char(1) default '0' comment '菜单状态（0正常 1停用）',
  `perms` varchar(100) default null comment '权限标识',
  `icon` varchar(100) default '#' comment '菜单图标',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default '' comment '备注',
  primary key (`menu_id`)
) engine=innodb auto_increment=2000 default charset=utf8 comment='菜单权限表';

-- ----------------------------
-- table structure for sys_notice
-- ----------------------------
drop table if exists `sys_notice`;
create table `sys_notice` (
  `notice_id` int(4) not null auto_increment comment '公告id',
  `notice_title` varchar(50) not null comment '公告标题',
  `notice_type` char(1) not null comment '公告类型（1通知 2公告）',
  `notice_content` longblob comment '公告内容',
  `status` char(1) default '0' comment '公告状态（0正常 1关闭）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(255) default null comment '备注',
  primary key (`notice_id`)
) engine=innodb auto_increment=10 default charset=utf8 comment='通知公告表';

-- ----------------------------
-- table structure for sys_oper_log
-- ----------------------------
drop table if exists `sys_oper_log`;
create table `sys_oper_log` (
  `oper_id` bigint(20) not null auto_increment comment '日志主键',
  `title` varchar(50) default '' comment '模块标题',
  `business_type` int(2) default '0' comment '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) default '' comment '方法名称',
  `request_method` varchar(10) default '' comment '请求方式',
  `operator_type` int(1) default '0' comment '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) default '' comment '操作人员',
  `dept_name` varchar(50) default '' comment '部门名称',
  `oper_url` varchar(255) default '' comment '请求url',
  `oper_ip` varchar(50) default '' comment '主机地址',
  `oper_location` varchar(255) default '' comment '操作地点',
  `oper_param` varchar(2000) default '' comment '请求参数',
  `json_result` varchar(2000) default '' comment '返回参数',
  `status` int(1) default '0' comment '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) default '' comment '错误消息',
  `oper_time` datetime default null comment '操作时间',
  primary key (`oper_id`)
) engine=innodb auto_increment=5 default charset=utf8 comment='操作日志记录';

-- ----------------------------
-- table structure for sys_post
-- ----------------------------
drop table if exists `sys_post`;
create table `sys_post` (
  `post_id` bigint(20) not null auto_increment comment '岗位id',
  `post_code` varchar(64) not null comment '岗位编码',
  `post_name` varchar(50) not null comment '岗位名称',
  `post_sort` int(4) not null comment '显示顺序',
  `status` char(1) not null comment '状态（0正常 1停用）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`post_id`)
) engine=innodb auto_increment=5 default charset=utf8 comment='岗位信息表';

-- ----------------------------
-- table structure for sys_role
-- ----------------------------
drop table if exists `sys_role`;
create table `sys_role` (
  `role_id` bigint(20) not null auto_increment comment '角色id',
  `role_name` varchar(30) not null comment '角色名称',
  `role_key` varchar(100) not null comment '角色权限字符串',
  `role_sort` int(4) not null comment '显示顺序',
  `data_scope` char(1) default '1' comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) default '1' comment '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) default '1' comment '部门树选择项是否关联显示',
  `status` char(1) not null comment '角色状态（0正常 1停用）',
  `del_flag` char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`role_id`)
) engine=innodb auto_increment=100 default charset=utf8 comment='角色信息表';

-- ----------------------------
-- table structure for sys_role_dept
-- ----------------------------
drop table if exists `sys_role_dept`;
create table `sys_role_dept` (
  `role_id` bigint(20) not null comment '角色id',
  `dept_id` bigint(20) not null comment '部门id',
  primary key (`role_id`,`dept_id`)
) engine=innodb default charset=utf8 comment='角色和部门关联表';

-- ----------------------------
-- table structure for sys_role_menu
-- ----------------------------
drop table if exists `sys_role_menu`;
create table `sys_role_menu` (
  `role_id` bigint(20) not null comment '角色id',
  `menu_id` bigint(20) not null comment '菜单id',
  primary key (`role_id`,`menu_id`)
) engine=innodb default charset=utf8 comment='角色和菜单关联表';

-- ----------------------------
-- table structure for sys_user
-- ----------------------------
drop table if exists `sys_user`;
create table `sys_user` (
  `user_id` bigint(20) not null auto_increment comment '用户id',
  `dept_id` bigint(20) default null comment '部门id',
  `user_name` varchar(30) not null comment '用户账号',
  `nick_name` varchar(30) not null comment '用户昵称',
  `user_type` varchar(2) default '00' comment '用户类型（00系统用户）',
  `email` varchar(50) default '' comment '用户邮箱',
  `phonenumber` varchar(11) default '' comment '手机号码',
  `sex` char(1) default '0' comment '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) default '' comment '头像地址',
  `password` varchar(100) default '' comment '密码',
  `status` char(1) default '0' comment '帐号状态（0正常 1停用）',
  `del_flag` char(1) default '0' comment '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) default '' comment '最后登录ip',
  `login_date` datetime default null comment '最后登录时间',
  `create_by` varchar(64) default '' comment '创建者',
  `create_time` datetime default null comment '创建时间',
  `update_by` varchar(64) default '' comment '更新者',
  `update_time` datetime default null comment '更新时间',
  `remark` varchar(500) default null comment '备注',
  primary key (`user_id`)
) engine=innodb auto_increment=100 default charset=utf8 comment='用户信息表';

-- ----------------------------
-- table structure for sys_user_post
-- ----------------------------
drop table if exists `sys_user_post`;
create table `sys_user_post` (
  `user_id` bigint(20) not null comment '用户id',
  `post_id` bigint(20) not null comment '岗位id',
  primary key (`user_id`,`post_id`)
) engine=innodb default charset=utf8 comment='用户与岗位关联表';

-- ----------------------------
-- table structure for sys_user_role
-- ----------------------------
drop table if exists `sys_user_role`;
create table `sys_user_role` (
  `user_id` bigint(20) not null comment '用户id',
  `role_id` bigint(20) not null comment '角色id',
  primary key (`user_id`,`role_id`)
) engine=innodb default charset=utf8 comment='用户和角色关联表';
