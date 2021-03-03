create table test  (id serial primary key, value varchar(1000));

insert into test(value) values('hello');
select * from test;