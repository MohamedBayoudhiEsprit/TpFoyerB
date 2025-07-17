FROM openjdk:8-jdk-alpine
EXPOSE 8082
ADD target/tp-foyer-5.0.0.jar /tp-foyerB-5.0.0.jar
ENTRYPOINT ["java","-jar","/tp-foyerB-5.0.0.jar"]