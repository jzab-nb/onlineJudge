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
drop table if exists clazz;
create table if not exists clazz(
    id int primary key auto_increment comment '主键',
    name varchar(255) not null comment '班级名称',
    teacherId int not null comment '教师ID',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '班级表';
-- 学生分班情况
drop table if exists stuInClazz;
create table if not exists stuInClazz(
    id int primary key auto_increment comment '主键',
    stuId int not null comment '学生ID',
    clazzId int not null comment '班级ID',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '学生班级关联表';
-- 课程表
drop table if exists course;
create table if not exists course(
    id int primary key auto_increment comment '主键',
    name varchar(255) not null comment '课程名字',
    img  text comment '课程图片',
    introduce   text comment '课程介绍',
    clazzId     int not null   comment '关联的班级ID',
    teacherId   int not null   comment '关联的教师ID',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '课程表';

drop table if exists stuInCourse;
create table if not exists stuInCourse(
     id int primary key auto_increment comment '主键',
     stuId int not null comment '学生ID',
     courseId int not null comment '课程ID',
     isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
     createUser  int DEFAULT(0) COMMENT '创建者',
     updateUser  int DEFAULT(0) COMMENT '更新者',
     createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
     updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '学生课程关联表';
-- 题库表
drop table if exists questionBank;
create table if not exists questionBank(
    id int primary key auto_increment comment '主键',
    name varchar(255) not null comment '题库名字',
    introduce   text comment '题库介绍',
    courseId int default(0) not null comment '关联的课程ID',
    teacherId  int not null comment '关联的教师ID',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '题库表';

-- 题目表
drop table if exists question;
create table if not exists question(
    id int primary key auto_increment comment '主键',
    type varchar(255) not null comment '题目类型',
    content text not null comment '题目内容', -- json类型
    questionBankId int not null comment '关联的题库ID',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '题目表';
-- 试卷表
drop table if exists paper;
create table if not exists paper(
    id int primary key auto_increment comment '主键',
    name varchar(255) not null comment '试卷名字',
    introduce   text comment '试卷介绍',
    questionList text not null comment '试卷题目列表', -- json格式
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '试卷表';
-- 考试表

drop table if exists exam;
create table if not exists exam(
    id int primary key auto_increment comment '主键',
    title varchar(255) not null comment '考试标题',
    introduce text comment '考试简介',
    paperId int not null comment '关联的试卷ID',
    startTime datetime not null comment '考试开始时间',
    endTime datetime not null comment '考试结束时间',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '考试表';

-- 学生考试记录表
drop table if exists stuExamRecord;
create table if not exists stuExamRecord(
    id int primary key auto_increment comment '主键',
    stuId int not null comment '学生ID',
    examId int not null comment '考试ID',
    score int not null comment '考试分数',
    isDelete    TINYINT DEFAULT(0) COMMENT '是否删除',
    createUser  int DEFAULT(0) COMMENT '创建者',
    updateUser  int DEFAULT(0) COMMENT '更新者',
    createTime  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)comment '学生考试记录表';