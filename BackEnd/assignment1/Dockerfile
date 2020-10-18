FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn -Dmaven.test.skip=true package
FROM openjdk:8-jre-alpine
WORKDIR /app
# Update the version in the name if you change the pom.xml version.
COPY --from=MAVEN_BUILD /build/target/assignment1-0.3.0.jar /app/
ENTRYPOINT ["java", "-jar", "assignment1-0.3.0.jar"]