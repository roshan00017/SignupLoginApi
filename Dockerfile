FROM openjdk:17-oracle
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} auth-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/auth-api.jar"]