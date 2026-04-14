-- The ddl scripts for the default tables created when using JdbcUserDetailsManager
-- can be found in JdbcUserDetailsManager > JdbcDaoImpl > users.ddl

create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

--- Sample data

insert ignore into users values ('user', '{noop}EazyBytes@12345', '1');
insert ignore into authorities values ('user', 'read');

insert ignore into users values ('admin', '{bcrypt}$2a$12$FseXXeUxAptDlak4cEmrT.BS7T6NNw3XJ6V5DQfjDFKnQS/RH0ebu', '1');
insert ignore into authorities values ('admin', 'admin');