drop table if exists persons;
create table persons
(
    id   bigint primary key auto_increment,
    name varchar(255),
    age  int
);

drop table if exists BATCH_JOB_EXECUTION_CONTEXT;
drop table if exists BATCH_JOB_EXECUTION_PARAMS;
drop table if exists BATCH_JOB_EXECUTION_SEQ;
drop table if exists BATCH_JOB_SEQ;
drop table if exists BATCH_STEP_EXECUTION_CONTEXT;
drop table if exists BATCH_STEP_EXECUTION_SEQ;
drop table if exists BATCH_STEP_EXECUTION;
drop table if exists BATCH_JOB_EXECUTION;
drop table if exists BATCH_JOB_INSTANCE;