FROM openjdk:8
WORKDIR /usr/app
COPY . .
EXPOSE 8080
RUN apt-get update && apt-get -y install maven
RUN mvn clean package
ARG DB_URL
ARG BUILD_ENV=production
ENV DATABASE_URL=$DB_URL
ENV BUILD_PROFILE=$BUILD_ENV
RUN echo $BUILD_PROFILE/$DATABASE_URL
CMD java -Xmx512M -XX:+CMSClassUnloadingEnabled -Dspring.profiles.active=$BUILD_PROFILE -debug -jar ./target/ApplyJob-1.0-SNAPSHOT.jar
