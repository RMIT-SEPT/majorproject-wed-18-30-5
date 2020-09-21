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
 - In the root directory there is a docker-compose.yml this will call both of the individual dockerfiles in order to run the docker service type 'docker-compose up -d' to build(if neccessary) and start the services(locally) in VM containers.
