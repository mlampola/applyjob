FROM openjdk:8
WORKDIR /usr/app
COPY . .
EXPOSE 8080
RUN apt-get update && apt-get -y install maven
RUN mvn clean package
CMD java -Dspring.profiles.active=default -jar ./target/ApplyJob-1.0-SNAPSHOT.jar

