-- create database coreapi_auth;
create user coreapi_auth with encrypted password 'coreapi_auth';
grant all privileges on database coreapi_auth to coreapi_auth;