use opac;

drop table issue;
drop table member;
drop table books;

create table books
(
 a_no int primary key,
 title varchar(30),
 author varchar(30),
 category varchar(20),
 status varchar(15) check(status in ('available','issued','transferred'))
);

create table member
(
 id int primary key,
 name varchar(20),
 category varchar(20) check(category in('student','staff')),
 dept varchar(20),
 yr_of_join int,
 ct_books int
);

create table issue
(
 b_ano int references books(a_no),
 m_id int references member(id),
 issue_dt date,
 ret_dt date,
 fine int,
 primary key(b_ano,m_id)
);

insert into books values(101,'Programming Paradigm','Horstmann','Java','Available');
insert into books values(102,'TOC','Motwani','Automata','Issued');
insert into books values(103,'Discrete Maths','Venkatraman','Maths','Available');
insert into books values(104,'System Software','L.Beck','Compiler Design','Transferred');
insert into books values(105,'SE','Pressman','Action','Available');

insert into member values(501,'Ramanathan','Student','cse',2010,0);
insert into member values(502,'Praveen','Staff','cse',2011,0);
insert into member values(503,'Prem','Student','it',2009,0);
insert into member values(504,'Raj','Staff','mech',2012,0);
insert into member values(505,'Rahul','Student','bme',2010,0);

insert into issue values(101,501,'2012-06-09','2012-07-12',0);
insert into issue values(102,502,'2012-10-10','2011-11-22',0);
insert into issue values(103,503,'2011-10-20','2010-11-02',0);
insert into issue values(104,504,'2012-10-20','2012-12-12',0);
insert into issue values(105,505,'2012-08-05','2010-08-25',0); 
insert into issue values(102,505,'2012-06-25','2010-06-28',0);
 
