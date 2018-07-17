CREATE TABLE db_people.person
(
  person_id SMALLINT,
  last_name string,
  first_name string,
  gender string,
  birth_date date
)
  ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
  STORED AS TEXTFILE
  LOCATION '/user/pete/person'; 