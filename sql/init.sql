drop table if exists user;
create table if not exists user(
    id          int          primary key auto_increment comment '主键',
    role        varchar(128) comment '角色',
    username    varchar(255) not null unique comment '用户名',
    name        varchar(255) comment '昵称',
    password    varchar(255) not null comment '密码',
    avatar      varchar(255) comment '头像链接',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '用户表';
-- 教师信息表

-- 学生信息表

-- 班级表

-- 课程表

-- 题库表

-- 题目表

-- 试卷表

-- 考试表

drop table if exists exam;
create table if not exists exam(
    id int primary key auto_increment comment '主键',
    title varchar(255) not null comment '考试标题',
    profile text comment '考试简介',
    paperId int not null comment '关联的试卷ID'
)comment '考试表';