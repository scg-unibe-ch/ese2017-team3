
-- drop table if exists authorities;
-- drop table if exists users;
create table if not exists users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table if not exists authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));

-- The following postgres statement was traduced to MySQL:
-- create unique index if not exists ix_auth_username on authorities (username,authority);
set @x := (select count(*) from information_schema.statistics where table_name = 'authorities' and index_name = 'ix_auth_username' and table_schema = database());
set @sql := if( @x > 0, 'select ''Index exists.''', 'Alter Table authorities ADD Index ix_auth_authorities (username,authority);');
PREPARE stmt FROM @sql;
EXECUTE stmt;