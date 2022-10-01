CREATE TABLE customer (
  id INTEGER NOT NULL AUTO_INCREMENT, 
  name varchar(45), 
  created_date datetime NOT NULL, 
  last_update  datetime DEFAULT NULL, 
  PRIMARY KEY (id)
);



CREATE TABLE featuretoggle (
  id INTEGER NOT NULL AUTO_INCREMENT, 
  name varchar(45), 
  displayname varchar(45), 
  technicalname varchar(45), 
  expireson datetime NOT NULL, 
  description varchar(45), 
  inverted INTEGER , 
  status INTEGER DEFAULT 1 , 
  created_date datetime NOT NULL, 
  last_update  datetime DEFAULT NULL, 
  PRIMARY KEY (id)
);


CREATE TABLE customerfeaturetoggle (
  id INTEGER NOT NULL AUTO_INCREMENT, 
   expireson datetime NOT NULL, 
  teaturetoggleid INTEGER , 
    inverted INTEGER , 
  customerid INTEGER ,
  created_date datetime NOT NULL, 
  last_update  datetime DEFAULT NULL, 
  PRIMARY KEY (id)
);