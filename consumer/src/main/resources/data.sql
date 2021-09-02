DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS waypoint;

CREATE TABLE booking (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  passenger_name VARCHAR(250) NOT NULL,
  passanger_contact_number VARCHAR(250) NOT NULL,
  pickup_time TIMESTAMP NULL,
  asap BOOLEAN DEFAULT NULL,
  waiting_time INT DEFAULT NULL,
  number_of_passengers INT DEFAULT NULL,
  price DECIMAL(20,2) DEFAULT NULL,
  rating INT DEFAULT NULL,
  created_on TIMESTAMP NULL,
  last_modified_on TIMESTAMP NULL
);

CREATE TABLE trip_waypoint (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  locality VARCHAR(250) NOT NULL,
  latitude DECIMAL(8,6) NOT NULL,
  longitude DECIMAL(8,6) NULL,
  booking_id INT,
  foreign key (booking_id) references booking(id)
);