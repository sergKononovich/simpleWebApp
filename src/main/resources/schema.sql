DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  department_id INT NOT NULL,
  job_title TEXT NOT NULL,
  gender VARCHAR(250) NOT NULL,
  birthday DATE NOT NULL
);