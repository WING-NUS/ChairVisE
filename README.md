# Chairvise

## Overview
This project is a tool to analyze paper submission information for conferences. It is basically a visualization tool combined with a backend query service.

Production Website at: https://chairvise-4-283411.appspot.com/

Demo video YouTube Link:

Report: Project_Report.pdf

##### Installation Guide
Pre-requisites: Python 3.8.3, Node 12.18.0, NPM 6.14.4, java 8, MYSQL 5.7

1. Clone this repo.

###### Frontend
2. Run `cd src/web/app`.
3. Run `npm install`.
4. Run `npm run serve`.
5. Access the application through http://localhost:4040

###### Backend
2. In mysql server, create database with the database name as `viz`.
3. Install your Google Cloud SDK. You can follow the instructions in https://cloud.google.com/sdk/install.

Note: For Unix (Ubuntu) users, you can follow the gcloud installation instructions at https://cloud.google.com/sdk/docs/quickstart-linux#before-you-begin instead of using apt-get, as using the latter to install will result in step 5 not working. 

4. Run `gcloud init` on the project root directory in your terminal.
5. Prepare for app engine deployment under project root directory in terminal: `gcloud -q components install app-engine-java`.
6. Navigate to src/main/resources/application-local.properties for SQL server connection setup and put the correct information to connect to the local database: 
spring.datasource.username=[YOURDATABASENAME]
spring.datasource.password=[YOURPASSWORD]
7. Run `./gradlew appengineRun`.

##### Technology stack

- Frontend: Vue.js + Vue Router + Vuex
- Backend: Google App Engine as PaaS + Spring REST + Google Cloud SQL (MySQL 5.6)
- [**Setting-up Guide**](docs/setting-up.md)
- [**Application Software Design**](docs/design.md)

##### Browser Support

| Browser | Google Chrome | Mozilla Firefox | Microsoft Edge | IE |
|---------|---------------|-----------------|----------------|----|
| Version | 83.0.4        | 77.0.1          | 44.18362.449.0 | 11 |
- This project work best in Firefox. 
- Note: This project does not work on Mac Google Chrome and Safari.

##### Acknoledgements

- New Libraries added: <a href="https://github.com/gitbrent/PptxGenJS"> PPTXGenJS </a>, <a href="https://github.com/emiliorizzo/vue-d3-network"> Vue D3 Network </a>, <a href="https://github.com/BinarCode/vue2-transitions"> Vue2-Transitions </a>, <a href="https://github.com/charliekassel/vuejs-datepicker"> VueJS-Datepicker </a>, <a href="https://github.com/fullcalendar/fullcalendar"> FullCalendar </a>
- This project was built off from <a href="https://github.com/WING-NUS/ChairVisE"> ChairVise 2.0 </a> created by <a href="https://wing.comp.nus.edu.sg/"> Wing-NUS </a>.
- Collaboration Icon was made by <a href="https://www.flaticon.com/authors/mynamepong" title="mynamepong">mynamepong</a> from <a href="https://www.flaticon.com/"             title="Flaticon">www.flaticon.com</a>
- Presentation Icon was made by <a href="https://www.flaticon.com/authors/smashicons" title="Smashicons">Smashicons</a> from <a href="https://www.flaticon.com/"             title="Flaticon">www.flaticon.com</a>
- Schedule, Stadistics, Upload Icon was made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a>
- Businessman Icon was made by <a href="https://www.flaticon.com/authors/vectors-market" title="Vectors Market">Vectors Market</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a>

