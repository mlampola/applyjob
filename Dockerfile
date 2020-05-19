FROM openjdk:8
WORKDIR /usr/app
COPY . .
EXPOSE 8080
RUN apt-get update && apt-get -y install maven
RUN mvn clean package
ARG DB_URL
ENV DATABASE_URL=$DB_URL
CMD java -jar ./target/ApplyJob-1.0-SNAPSHOT.jar

