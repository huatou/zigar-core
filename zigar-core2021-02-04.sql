
drop table if exists z_file;

drop table if exists z_module;

drop table if exists z_notice;

drop table if exists z_privilege;

drop table if exists z_user;

/*==============================================================*/
/* Table: z_file                                                */
/*==============================================================*/
create table if not exists z_file
(
    file_id_             varchar(36) not null comment '文件ID',
    size_                bigint comment '文件大小',
    name_                varchar(1024) not null comment '文件名称',
    md5_                 varchar(512) comment '文件名称md5',
    suffix_              varchar(32) comment '文件后缀',
    url_                 varchar(2048) not null comment '存储路径',
    upload_user_id_      varchar(36) not null comment '上传人ID',
    create_time_         datetime not null comment '创建时间',
    flag_                bit default 0 comment '逻辑删除',
    primary key (file_id_)
)
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci;

alter table z_file comment '文件表';

/*==============================================================*/
/* Table: z_module                                              */
/*==============================================================*/
create table if not exists z_module
(
    module_id_           varchar(36) not null comment '模块ID',
    name_                varchar(64) not null comment '模块名',
    sort_                int comment '排序',
    type_                int comment '模块类型：0业务模块，1系统模块',
    is_enabled_          int not null comment '是否可用',
    create_time_         datetime not null comment '创建时间',
    icon_                varchar(64) comment '图标',
    to_                  varchar(256) comment '路径',
    actions_             varchar(128) comment '操作项，多个以逗号隔开',
    flag_                bit default 0 comment '逻辑删除',
    primary key (module_id_)
)
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci;

alter table z_module comment '模块';

/*==============================================================*/
/* Table: z_privilege                                           */
/*==============================================================*/
create table if not exists z_privilege
(
    privilege_id_        varchar(36) not null comment '权限ID',
    user_id_             varchar(36) comment '用户ID',
    module_id_           varchar(36) not null comment '模块ID',
    actions_             varchar(128) not null comment '操作项，多个以逗号隔开',
    create_time_         datetime not null comment '创建时间',
    flag_                bit default 0 comment '逻辑删除',
    primary key (privilege_id_)
)
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci;

alter table z_privilege comment '权限';

/*==============================================================*/
/* Table: z_user                                                */
/*==============================================================*/
create table if not exists z_user
(
    user_id_             varchar(36) not null comment '用户ID',
    username_            varchar(64) not null comment '用户名',
    password_            varchar(128) not null comment '密码',
    display_name_        varchar(128) comment '姓名',
    sex_                 varchar(32) comment '性别',
    phone_               varchar(64) comment '手机号',
    last_login_time_     datetime comment '最后登录时间',
    create_time_         datetime not null comment '创建时间',
    pwd_reset_time_      datetime not null comment '密码修改时间',
    role_                varchar(32) comment '角色：root，admin，user',
    is_account_non_expired_ int not null comment '是否未过期',
    is_account_non_locked_ int not null comment '是否未锁定',
    is_credentials_non_expired_ int not null comment '证书是否未过期',
    is_enabled_          int not null comment '是否可用',
    flag_                bit default 0 comment '逻辑删除',
    head_pic_            varchar(1024) comment '头像',
    nick_name_           varchar(128) comment '昵称',
    wechat_open_id       varchar(128) comment '微信公众号openId',
    primary key (user_id_)
)
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_general_ci;

alter table z_user comment '用户';

alter table z_privilege add constraint fk_privilege_moduleid foreign key (module_id_)
    references z_module (module_id_) on delete restrict on update restrict;

alter table z_privilege add constraint fk_privilege_userid foreign key (user_id_)
    references z_user (user_id_) on delete restrict on update restrict;
