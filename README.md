# ChairVisE

ChairVisE is a web application for conference metadata visualization. This is the 4th version.

The wiki page is https://github.com/owenliu97/chairvise/wiki/ChairVisE-V4.0

## Environment Setup

### Setup Frontend

Install the latest node.js from https://nodejs.org/en/Install 

frontend components: 

go to frontend project root directory, and run `npm install`

Run the frontend project: `npm run serve`

### Setup Backend

Install Java Development Kit 1.8 from https://www.oracle.com/java/technologies/javase-downloads.html

Install Maven from http://maven.apache.org/download.cgi

Install MySQL 5.8 and create a database `chairvise4`.

Run the database initialization script `init.sql` in database folder.

Run `mvn clean install` in the backend project root directory

Run the `Chairvise4Application.java` class

