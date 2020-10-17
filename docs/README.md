# SEPT Startup code and  project Structure documentation 

## Back-End 
###### Folder Structure
  * Config - contains all configuration beans and other helper functions.
  * Requests - contains the templates and validation checks for any endpoint request
  * Responses - contains base response template, potential for future expansion
    * Response.java(Base response for all responses)
      - Boolean success, if action was successfull response is true.
      - String message, optional: use only on success and pass any optional information.
      - Object errors, if action wasn't successfull errors will either be a single string or list of field errors.
      - Object body, whatever object we may want to return.
  * Model - contains all entity models that represent database data.
    * Entity_User.java - base user class for all users
    * Entity_UserType.java - used to represent our three user types, Admin/Employee/Customer
    * Entity_Booking.java - 
    * Entity_Service.java - used to represent a service an Employee/Admin can provide, will also have a time length variable.
    * Entity_Schedule.java - used to represent a work schedule for an Employee/Admin
  * Repositories - CrudRepositories for our models, allows read/write to database for specific model
  * Services - Handles calls between web and repositories, also and specific functions for Modifing Model data.
  * Web - Endpoints for specific GET/POST calls from the front end.
      


# Quick Start
## Project Start -Local using H2 database
###### Front End
 * navigate to FrontEnd folder in command line
 * type: npm install
 * type: npm start
###### Back End
 * navigate to BackEnd/assignment1 in command line
 * type: ./mvnw spring-boot:run
###### Web Browser
 * navigate to localhost:3000
 
## Project Start -Local using docker-compose MySQL
 * navigate to project root in command line
 * type: docker-compose build (if you need to)
 * type: docker-compose up -d
 * navigate to localhost:3000 on a web browser after about 2 minutes

## Back-End
###### Running SpringBoot with startup testing data.
  - Building and running the BackEnd is done in VisualStudioCode and IntelliJ, main development people are using VSCode
  - Uncomment the function 'public CommandLineRunner demo()' from DemoApplication.java
    - will create testing data in terms of users, usertypes, schedules etc...

###### Back-End JUnit tests
 - BackEnd/assignment1/src/test
   - All tests are in here, A seperate file for each controller is used to test all endpoints. 
   
###### Application-properties
 - We set the JDBC datasource details here
 - We also set up H2 console path for accessing the database
 - Lastly all spring session and security data, such as session timeout, cookie age etc
 
###### Configuration files
 - All config files are in assignment1/src/main/java/com/wed18305/assignment1/config/
   - AuthenticationSuccess.java, custom response handler for successfull login
   - AuthenticationFailure.java, custom response handler for unsuccessfull login
   - DateTimeStatic.java, static file for formatting strings into OffsettDateTime
   - DefaultEncoder.java, temporary encoder for passwords(doesn't encode)
   - SecurityConfig.java, the most important file here, tells JDBC auth where to look for user login also handles login endpoint and authority required for other endpoints
   
## Docker
 - Both the front and back ends have an individual dockerfile which handles building and running the container for that service
 - In the root directory there is a docker-compose.yml this will call both of the individual dockerfiles in order to run the docker service type 'docker-compose up -d' to start the services(locally) in VM containers.
 - Image names are
  - 386724395914.dkr.ecr.us-east-1.amazonaws.com/backend-app:latest
  - 386724395914.dkr.ecr.us-east-1.amazonaws.com/frontend-app:latest
 - This allows pushing to the AWS ECR via the following command(provided you have access SEE AWS BELOW)
  - docker push 386724395914.dkr.ecr.us-east-1.amazonaws.com/backend-app:latest
  - docker push 386724395914.dkr.ecr.us-east-1.amazonaws.com/frontend-app:latest
 
## CircleCI
 - CircleCI config.yaml contains three jobs, BackBuild, BackTest and FrontTest, as the names suggest BackBuild performs a maven build on the back end(without test) the BackTest requires a successful build then performs the testing for the back end, FrontTest performs all tests for the front end.
 
## AWS
 - AWS account is currently setup for s3561388@student.rmit.edu.au(NeilK) and contains an ECR(elastic container registry) with both of the registry created using docker contained here named backend-app and frontend-app
 - Since we have no ability to setup Users or groups in AWS student I'm unsure how others can help out at this time.
 - CODE for getting aws credentials for pushing  
   - aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 386724395914.dkr.ecr.us-east-1.amazonaws.com
