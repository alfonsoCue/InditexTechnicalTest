FROM openjdk:21-jdk

EXPOSE 8080

ADD target/InditexTechnicalTest-0.0.1-SNAPSHOT.jar inditexTechnicalTest.jar

ENTRYPOINT ["java", "-jar", "/inditexTechnicalTest.jar"]