create user coreapi_datasmuggler with encrypted password 'coreapi_datasmuggler';
grant select on all tables in schema public TO coreapi_datasmuggler;
alter default privileges in schema public grant select on tables to coreapi_datasmuggler;