create table user(
  id int primary key ,
  name varchar (100),

)

create table role(
  id int primary key ,
  name varchar (100),
)

create table user_role(
  user_id int ,
  role_id int
)

create  table principal(
  id int primary key ,
  name varchar (100),
)

create table role_principal(
  role_id int,
  principal_id int
)