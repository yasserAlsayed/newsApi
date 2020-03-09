DROP TABLE IF EXISTS NEWS;
 
 create TABLE NEWS (
     	ID BIGINT (11) NOT NULL,
        DATE timestamp,
        DESCRIPTION CLOB,
        BODY CLOB,
        TITLE varchar(255),
        primary key (ID)
    )
    
  