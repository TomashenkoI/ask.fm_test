begin;
set constraints all deferred;
commit;

begin;

truncate table loan cascade;
alter sequence loan_seq restart with 1;

truncate table users cascade;
alter sequence user_seq restart with 1;

commit;

begin;
set constraints all immediate;
commit;
