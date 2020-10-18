There are two deployments that you can choose to run.
 Tag: M3.0.2 - Local H2 deployment
 Tag: M3.0.1 - Docker-compose deployment(MySQL)

M3.0.2
If you choose to deploy M3.0.2 you need to navigate to the frontEdn folder via command line and type 'npm install' after the installation proccess is complete type 'npm start' this will start the front end and should open localhost:3000 in your web browser when it has completed. To start the back end navigate to assignment1 folder under BackEnd then via command line type './mvnw spring-boot:run' once this is complete you can then use the webpage at localhost:3000

M3.0.1
If you choose to deploy M3.0.1 you need to navigate to the root directory which contains the docker-compose file, once here enter 'docker-compose build' to build our containers, NOTE this may take 15-20 mins, once the containers are built you then type 'docker-compose up -d' to start the four containers, after roughly 3 minutes all containers should have successfully loaded, if you have docker dashboard you can check the logs to see if they have finished startup. Once finished you can navigate to localhost:3000 to start using the build.


COMMON
Admin user
username: Palmer
password: 1234

Customer
username: Jacky
password: 1234

Employee
username: Dessler
password: 1234
