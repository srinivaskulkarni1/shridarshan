DROP KEYSPACE IF EXISTS shridarshan;

CREATE KEYSPACE shridarshan WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

USE shridarshan;

CREATE TABLE temple(
god VARCHAR,
place varchar,
district varchar,
state varchar,
PRIMARY KEY ((god, place))
); 