FROM openjdk:11-jdk

# FROM amazoncorretto:11 ==> amazon corretto 11 사용할 경우

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
