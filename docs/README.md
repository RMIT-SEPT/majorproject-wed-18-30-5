# SEPT Startup code and  project Structure documentation 

## Back-End 
###### Folder Structure
  * Requests - contains the templates and validation checks for any endpoint request
  * Responses - contains base response template, potential for future expansion
    * Response.java(Base response for all responses)
      - Boolean success, if action was successfull response is true.
      - String message, optional: use only on success and pass any optional information.
      - Object errors, if action wasn't successfull errors will either be a single string or list of field errors.
      - Object body, whatever object we may want to return.
  * Model - contains all entity models that represent database data.
    * User.java - base user class for all users
    * UserType.java - used to represent our three user types, Admin/Employee/Customer
    * Booking.java - 
    * Service.java -
    * Work.java -
  * Repositories - CrudRepositories for our models, allows read/write to database for specific model
  * Services - Handles calls between web adn repositories.
  * Web - Endpoints for specific GET/POST calls from front end.
      


# Quick Start

## Back-End
###### Running SpringBoot with startup testing data.
  - Uncomment the function 'public CommandLineRunner demo()' from DemoApplication.java
    - will create the three user types and some generic users.
